package com.hxt.oj.judge.strategy;

import com.hxt.oj.judge.codesandbox.model.JudeInfo;

/**
 * ClassName: JudeStrategy
 * Package: com.hxt.oj.judge.strategy
 * Description:
 *        判题策略判题策略
 * @Author hxt
 * @Create 2024/10/15 18:01
 * @Version 1.0
 */

public interface JudeStrategy {

    /**
     * 执行判题
     * @param judeContext
     * @return
     */
    JudeInfo doJude(JudeContext judeContext);

}
