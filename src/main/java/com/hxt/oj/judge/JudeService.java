package com.hxt.oj.judge;

import com.hxt.oj.model.entity.QuestionSubmit;

/**
 * ClassName: JudeService
 * Package: com.hxt.oj.judge
 * Description:
 *          判题服务
 * @Author hxt
 * @Create 2024/10/15 16:24
 * @Version 1.0
 */
public interface JudeService {

    /**
     *
     * 判题
     * @param QuestionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long QuestionSubmitId);
}
