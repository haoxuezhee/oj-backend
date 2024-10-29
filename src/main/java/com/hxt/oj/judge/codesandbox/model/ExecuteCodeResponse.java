package com.hxt.oj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: ExecuteCodeRequest
 * Package: com.hxt.oj.judge.codesandbox.model
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/14 20:17
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeResponse {

    private List<String> outputList;

    /**
     * 执行信息
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudeInfo judeInfo;
}
