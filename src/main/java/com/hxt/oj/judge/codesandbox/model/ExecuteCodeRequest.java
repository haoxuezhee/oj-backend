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
public class ExecuteCodeRequest {

    private List<String> inputList;

    private String code;

    private String language;
}
