package com.hxt.oj.judge;

import cn.hutool.json.JSONUtil;
import com.hxt.oj.common.ErrorCode;
import com.hxt.oj.exception.BusinessException;
import com.hxt.oj.judge.codesandbox.CodeSandBox;
import com.hxt.oj.judge.codesandbox.CodeSandBoxFactory;
import com.hxt.oj.judge.codesandbox.CodeSandBoxProxy;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hxt.oj.judge.strategy.JudeContext;
import com.hxt.oj.judge.strategy.JudeStrategy;
import com.hxt.oj.model.dto.question.JudeCase;
import com.hxt.oj.judge.codesandbox.model.JudeInfo;
import com.hxt.oj.model.entity.Question;
import com.hxt.oj.model.entity.QuestionSubmit;
import com.hxt.oj.model.enums.QueationSubmitEnum;
import com.hxt.oj.service.QuestionService;
import com.hxt.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: JudeServiceImpl
 * Package: com.hxt.oj.judge
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/15 16:32
 * @Version 1.0
 */
@Service
public class JudeServiceImpl implements JudeService {

    @Value("${codesandbox.type:example}")
    private String type;
    @Resource
    private QuestionSubmitService questionSubmitService;
    
    @Resource
    private QuestionService questionService;
    
    @Override
    public QuestionSubmit doJudge(long QuestionSubmitId) {
        //传入题目id，获取相对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(QuestionSubmitId);
        if(questionSubmit==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        //如果题目提交状态不为等待中，就不用重复执行
        if(!QueationSubmitEnum.WAITING.getValue().equals(questionSubmit.getStatus())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目已提交正在判题中");
        }
        //更改判题（题目提交）的状态为判题中，防止重复执行，也能让用户及时看到判题的状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(QuestionSubmitId);
        questionSubmitUpdate.setStatus(QueationSubmitEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交状态更新失败");
        }
        //调用沙箱执行代码，获取判题结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        //获取判题输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudeCase> judeCaseList = JSONUtil.toList(judgeCaseStr, JudeCase.class);
        List<String> inputList = judeCaseList.stream().map(JudeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        //根据沙箱的返回结果，更新判题（题目提交）的状态，以及执行信息
        List<String> outputList = executeCodeResponse.getOutputList();
        JudeContext judeContext = new JudeContext();
        judeContext.setJudeInfo(executeCodeResponse.getJudeInfo());
        judeContext.setInputList(inputList);
        judeContext.setOutputList(outputList);
        judeContext.setJudeCaseList(judeCaseList);
        judeContext.setQuestion(question);

        JudeStrategy judeStrategy = JudeManager.getJudeStrategy(questionSubmit.getLanguage());
        JudeInfo judeInfo = judeStrategy.doJude(judeContext);

        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(QuestionSubmitId);
        questionSubmitUpdate.setStatus(QueationSubmitEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交状态更新失败");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(QuestionSubmitId);
        return questionSubmitResult;
    }
}
