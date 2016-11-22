package com.sxh.dao;

import java.util.List;

import com.sxh.model.Suggestions;
import com.sxh.vo.SuggestionsVo;

public interface SuggestionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Suggestions record);

    int insertSelective(Suggestions record);

    Suggestions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Suggestions record);

    int updateByPrimaryKey(Suggestions record);
    
    List<Suggestions> selectAll(SuggestionsVo suggestionsVo);
}