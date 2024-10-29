package com.hxt.oj.model.dto.question;

import lombok.Data;

/**
 * ClassName: QuestionJudeCase
 * Package: com.hxt.oj.model.dto.question
 * Description:
 *          题目配置
 * @Author hxt
 * @Create 2024/10/11 20:30
 * @Version 1.0
 */
@Data
public class JudeConfig {

    /**
     * 时间限制(ms)
     */
    private Long timeLimit;
    /**
     * 内存限制(kb)
     */
    private Long memoryLimit;

    /**
     * 堆栈限制(kb)
     */
    private Long stackLimit;

}
