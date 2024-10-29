package com.hxt.oj.judge.codesandbox.model;

import lombok.Data;

/**
 * ClassName: QuestionJudeCase
 * Package: com.hxt.oj.model.dto.question
 * Description:
 *          题目执行信息
 * @Author hxt
 * @Create 2024/10/11 20:30
 * @Version 1.0
 */
@Data
public class JudeInfo {

    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 消耗内存(kb)
     */
    private Long memory;

    /**
     * 消耗时间(ms)
     */
    private Long time;

}
