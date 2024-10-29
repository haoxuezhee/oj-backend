package com.hxt.oj.judge.codesandbox;

import com.hxt.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: CodeSandBoxProxy
 * Package: com.hxt.oj.judge.codesandbox
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/15 16:02
 * @Version 1.0
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {

    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：{}", executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：{}", executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
