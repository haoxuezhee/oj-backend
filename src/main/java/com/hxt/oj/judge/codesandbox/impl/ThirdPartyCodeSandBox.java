package com.hxt.oj.judge.codesandbox.impl;

import com.hxt.oj.judge.codesandbox.CodeSandBox;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * ClassName: ExampleCodeSand
 * Package: com.hxt.oj.judge.codesandbox.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/14 20:26
 * @Version 1.0
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("调用第三方代码沙箱");
        return null;
    }
}
