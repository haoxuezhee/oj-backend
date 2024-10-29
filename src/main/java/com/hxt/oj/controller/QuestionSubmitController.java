package com.hxt.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxt.oj.annotation.AuthCheck;
import com.hxt.oj.common.BaseResponse;
import com.hxt.oj.common.ErrorCode;
import com.hxt.oj.common.ResultUtils;
import com.hxt.oj.constant.UserConstant;
import com.hxt.oj.exception.BusinessException;
import com.hxt.oj.model.dto.question.QuestionQueryRequest;
import com.hxt.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hxt.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.hxt.oj.model.entity.Question;
import com.hxt.oj.model.entity.QuestionSubmit;
import com.hxt.oj.model.entity.User;
import com.hxt.oj.model.vo.QuestionSubmitVO;
import com.hxt.oj.service.QuestionSubmitService;
import com.hxt.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子点赞接口
 *
 * @author <a href="https://github.com/lihxt">程序员鱼皮</a>
 * @from <a href="https://hxt.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doSubmitQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }


    /**
     * 分页获取题目提交列表（仅管理员，普通用户只能看到非答案，提交代码等公开信息）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(
            @RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
            HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //从数据库中查询原始数据
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        //信息脱敏
        Page<QuestionSubmitVO> questionSubmitVOPage = questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, request);
        return ResultUtils.success(questionSubmitVOPage);
    }


}
