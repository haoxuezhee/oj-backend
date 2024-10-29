package com.hxt.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxt.oj.model.dto.question.QuestionAddRequest;
import com.hxt.oj.model.dto.question.QuestionQueryRequest;
import com.hxt.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hxt.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.hxt.oj.model.entity.Question;
import com.hxt.oj.model.entity.QuestionSubmit;
import com.hxt.oj.model.entity.User;
import com.hxt.oj.model.vo.QuestionSubmitVO;
import com.hxt.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;


/**
* @author 86185
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-10-11 20:03:21
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {


    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param request
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);
}
