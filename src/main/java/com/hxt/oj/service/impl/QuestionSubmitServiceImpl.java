package com.hxt.oj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxt.oj.common.ErrorCode;
import com.hxt.oj.constant.CommonConstant;
import com.hxt.oj.exception.BusinessException;
import com.hxt.oj.judge.JudeService;
import com.hxt.oj.mapper.QuestionSubmitMapper;
import com.hxt.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hxt.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.hxt.oj.model.entity.Question;
import com.hxt.oj.model.entity.QuestionSubmit;
import com.hxt.oj.model.entity.User;
import com.hxt.oj.model.enums.QueationSubmitEnum;
import com.hxt.oj.model.enums.QuestionSubmitLanguageEnum;
import com.hxt.oj.model.vo.QuestionSubmitVO;
import com.hxt.oj.model.vo.UserVO;
import com.hxt.oj.service.QuestionService;
import com.hxt.oj.service.QuestionSubmitService;
import com.hxt.oj.service.UserService;
import com.hxt.oj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author 86185
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-10-11 20:03:21
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;
    
    @Resource
    @Lazy
    private JudeService judeService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已经提交题目
        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        //设置初始状态
        questionSubmit.setStatus(QueationSubmitEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据插入失败");
        }
        // 执行判题服务
        Long questionSubmitId = questionSubmit.getId();
        CompletableFuture.runAsync(() ->{
           judeService.doJudge(questionSubmitId);
        });
        return questionSubmitId;
    }

    /**
     * 获取查询包装类（用户根据某些字段查询）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        String sortField = questionSubmitQueryRequest.getSortField();
        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QueationSubmitEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        User loginUser = userService.getLoginUser(request);
        Long loginUserId = loginUser.getId();
        Long userId = questionSubmit.getUserId();
        if(loginUserId != userId && !userService.isAdmin(loginUser)){
            questionSubmitVO.setCode(null);
        }
        // 1. 关联查询用户信息
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionSubmitVO.setUserVO(userVO);
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
            Long userId = questionSubmit.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionSubmitVO.setUserVO(userService.getUserVO(user));
            return questionSubmitVO;
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

    
}




