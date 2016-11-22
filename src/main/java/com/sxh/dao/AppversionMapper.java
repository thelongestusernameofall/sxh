package com.sxh.dao;

import java.util.List;

import com.sxh.model.Appversion;

public interface AppversionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appversion record);

    int insertSelective(Appversion record);

    Appversion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appversion record);

    int updateByPrimaryKey(Appversion record);
    
    List<Appversion> appversionList();
    
    Appversion getMaxAppVersion();
}