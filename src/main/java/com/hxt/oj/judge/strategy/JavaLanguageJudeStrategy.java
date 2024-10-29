package com.hxt.oj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.hxt.oj.model.dto.question.JudeCase;
import com.hxt.oj.model.dto.question.JudeConfig;
import com.hxt.oj.judge.codesandbox.model.JudeInfo;
import com.hxt.oj.model.entity.Question;
import com.hxt.oj.model.enums.JudeInfoMessageEnum;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: DefaultJudeStrategy
 * Package: com.hxt.oj.judge.strategy
 * Description:
 *          默认判题策略
 * @Author hxt
 * @Create 2024/10/15 18:05
 * @Version 1.0
 */
public class JavaLanguageJudeStrategy implements JudeStrategy{

    /**
     * 执行判题
     * @param judeContext
     * @return
     */
    @Override
    public JudeInfo doJude(JudeContext judeContext) {
        JudeInfo judeInfo = judeContext.getJudeInfo();
        List<String> inputList = judeContext.getInputList();
        List<String> outputList = judeContext.getOutputList();
        List<JudeCase> judeCaseList = judeContext.getJudeCaseList();
        Question question = judeContext.getQuestion();
        //获取沙箱执行的限制
        Long memory = Optional.ofNullable(judeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judeInfo.getTime()).orElse(0L);
        //先判断沙箱执行的结果输出数量是否和预期输出数量相等
        JudeInfoMessageEnum judeInfoMessageEnum = JudeInfoMessageEnum.ACCEPTED;
        JudeInfo judeInfoResponse = new JudeInfo();
        judeInfoResponse.setMemory(memory);
        judeInfoResponse.setTime(time);

        if(outputList.size()!=inputList.size()){
            judeInfoMessageEnum = judeInfoMessageEnum.WRONG_ANSWER;
            judeInfoResponse.setMessage(judeInfoMessageEnum.getValue());
            return judeInfoResponse;
        }
        //依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < judeCaseList.size(); i++) {
            JudeCase judeCase = judeCaseList.get(i);
            String outputImagine = judeCase.getOutput();
            String outputFact = outputList.get(i);
            if(!outputImagine.equals(outputFact)){
                judeInfoMessageEnum = judeInfoMessageEnum.WRONG_ANSWER;
                judeInfoResponse.setMessage(judeInfoMessageEnum.getValue());
                return judeInfoResponse;
            }
        }
        //判断题目的限制是否符合要求
        //获取题目提交的限制
        String judgeConfigStr = question.getJudgeConfig();
        JudeConfig judeConfig = JSONUtil.toBean(judgeConfigStr, JudeConfig.class);
        Long memoryLimit = judeConfig.getMemoryLimit();
        Long timeLimit = judeConfig.getTimeLimit();
        //比较限制
        if(memory > memoryLimit){
            judeInfoMessageEnum = JudeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judeInfoResponse.setMessage(judeInfoMessageEnum.getValue());
            return judeInfoResponse;
        }
        long JAVA_TIME_COST = 3000L;
        if( (time - JAVA_TIME_COST)  > timeLimit){
            judeInfoMessageEnum = JudeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judeInfoResponse.setMessage(judeInfoMessageEnum.getValue());
            return judeInfoResponse;
        }
        judeInfoResponse.setMessage(judeInfoMessageEnum.getValue());
        return judeInfoResponse;
    }
}
