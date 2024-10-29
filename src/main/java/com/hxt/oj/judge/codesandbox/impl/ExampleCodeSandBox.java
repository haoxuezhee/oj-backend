package com.hxt.oj.judge.codesandbox.impl;

import com.hxt.oj.judge.codesandbox.CodeSandBox;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hxt.oj.judge.codesandbox.model.JudeInfo;
import com.hxt.oj.model.enums.JudeInfoMessageEnum;
import com.hxt.oj.model.enums.QueationSubmitEnum;

import java.util.List;

/**
 * ClassName: ExampleCodeSand
 * Package: com.hxt.oj.judge.codesandbox.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/14 20:26
 * @Version 1.0
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QueationSubmitEnum.SUCCEED.getValue());
        JudeInfo judeInfo = new JudeInfo();
        judeInfo.setMessage(JudeInfoMessageEnum.ACCEPTED.getValue());
        judeInfo.setMemory(100L);
        judeInfo.setTime(100L);
        executeCodeResponse.setJudeInfo(judeInfo);
        

        return executeCodeResponse;
    }
}
