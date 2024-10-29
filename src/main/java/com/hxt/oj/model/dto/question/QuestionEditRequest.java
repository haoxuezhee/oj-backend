package com.hxt.oj.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编辑请求
 *
 * @author <a href="https://github.com/lihxt">程序员鱼皮</a>
 * @from <a href="https://hxt.icu">编程导航知识星球</a>
 */
@Data
public class QuestionEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;


    /**
     * 判题用例（json 数组）
     */
    private List<JudeCase> judgeCase;

    /**
     * 判题配置（json 对象）
     */
    private JudeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}