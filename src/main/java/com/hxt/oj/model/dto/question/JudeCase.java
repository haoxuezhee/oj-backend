package com.hxt.oj.model.dto.question;

import lombok.Data;

/**
 * ClassName: QuestionJudeCase
 * Package: com.hxt.oj.model.dto.question
 * Description:
 *          题目用例
 * @Author hxt
 * @Create 2024/10/11 20:30
 * @Version 1.0
 */
@Data
public class JudeCase {

    /**
     * 输入用例
     */
    private String input;
    /**
     * 输出用例
     */
    private String output;

}
