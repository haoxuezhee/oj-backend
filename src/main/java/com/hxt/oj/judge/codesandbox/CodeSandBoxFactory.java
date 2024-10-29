package com.hxt.oj.judge.codesandbox;

import com.hxt.oj.judge.codesandbox.impl.ExampleCodeSandBox;
import com.hxt.oj.judge.codesandbox.impl.RemoteCodeSandBox;
import com.hxt.oj.judge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 * ClassName: CodeSandBoxFactory
 * Package: com.hxt.oj.judge.codesandbox
 * Description:
 *            代码沙箱工厂
 * @Author hxt
 * @Create 2024/10/14 20:59
 * @Version 1.0
 */
public class CodeSandBoxFactory {
    /**
     * 根据类型获取代码沙箱(简单工厂模式)
     * @param type
     * @return
     */
    public static CodeSandBox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
