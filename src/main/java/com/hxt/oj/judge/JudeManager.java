package com.hxt.oj.judge;

import com.hxt.oj.judge.strategy.DefaultJudeStrategy;
import com.hxt.oj.judge.strategy.JavaLanguageJudeStrategy;
import com.hxt.oj.judge.strategy.JudeStrategy;

/**
 * ClassName: JudeManager
 * Package: com.hxt.oj.judge.strategy
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/15 19:41
 * @Version 1.0
 */
public class JudeManager {

    /**
     * 根据语言获取判题策略
     * @param language
     * @return
     */
    public static JudeStrategy getJudeStrategy(String language) {
        switch (language) {
            case "java":
                return new JavaLanguageJudeStrategy();
            default:
                return new DefaultJudeStrategy();
        }

    }

}
