package com.hxt.oj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hxt.oj.common.ErrorCode;
import com.hxt.oj.exception.BusinessException;
import com.hxt.oj.judge.codesandbox.CodeSandBox;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: ExampleCodeSand
 * Package: com.hxt.oj.judge.codesandbox.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/14 20:26
 * @Version 1.0
 */
public class RemoteCodeSandBox implements CodeSandBox {

    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("调用远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(responseStr)){
            throw  new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remoteSandBox error message = "+responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
