package com.hxt.oj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息消息枚举
 */
public enum JudeInfoMessageEnum {

    ACCEPTED("成功", "accepted"),
    WRONG_ANSWER("答案错误", "wrongAnswer"),
    COMPILE_ERROR("编译错误", "compileError"),
    MEMORY_LIMIT_EXCEEDED("内存溢出", "memoryLimit"),
    TIME_LIMIT_EXCEEDED("超时", "timeLimit"),
    WAITING("等待中", "waiting"),
    PRESENTATION_ERROR("展示错误", "presentationError"),
    OUTPUT_LIMIT_EXCEEDED("输出溢出", "outputLimitExceeded"),
    DANGEROUS_OPERATION("危险操作", "dangerousOperation"),
    RUNTIME_ERROR("运行错误", "runtimeError"),
    SYSTEM_ERROR("系统错误", "systemError");

    private final String text;

    private final String value;

    JudeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudeInfoMessageEnum anEnum : JudeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
