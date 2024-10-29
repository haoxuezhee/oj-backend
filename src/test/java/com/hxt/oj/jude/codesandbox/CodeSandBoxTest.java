package com.hxt.oj.jude.codesandbox;

import com.hxt.oj.judge.codesandbox.CodeSandBox;
import com.hxt.oj.judge.codesandbox.CodeSandBoxFactory;
import com.hxt.oj.judge.codesandbox.CodeSandBoxProxy;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hxt.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hxt.oj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * ClassName: CodeSandBoxTest
 * Package: com.hxt.oj.jude.codesandbox
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/14 20:34
 * @Version 1.0
 */
@SpringBootTest
public class CodeSandBoxTest {

    @Value("${codesandbox.type}")
    private String type;
    @Test
    void executeCodeTest(){
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String code = "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果：\" + (a + b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
      //  Assertions.assertNotNull(executeCodeResponse);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String type = scanner.nextLine();
            CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
            String code = "int main(){}";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        }

    }
}
