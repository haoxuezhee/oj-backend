package com.hxt.oj.judge.strategy;

import com.hxt.oj.model.dto.question.JudeCase;
import com.hxt.oj.judge.codesandbox.model.JudeInfo;
import com.hxt.oj.model.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * ClassName: JudeCntext
 * Package: com.hxt.oj.judge.strategy
 * Description:
 *          上下文（用于定义策略中传递的参数）
 * @Author hxt
 * @Create 2024/10/15 18:02
 * @Version 1.0
 */
@Data
public class JudeContext {


    private JudeInfo judeInfo;

    private List<String> inputList;
    private List<String> outputList;

    private List<JudeCase> judeCaseList;
    private Question question;
}
