package com.hxt.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.hxt.oj.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86185
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2024-10-11 19:56:34
* @Entity generator.domain.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}




