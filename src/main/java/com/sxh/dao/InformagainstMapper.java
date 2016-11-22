package com.sxh.dao;

import java.util.List;

import com.sxh.model.Informagainst;

public interface InformagainstMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Informagainst record);

	int insertSelective(Informagainst record);

	Informagainst selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Informagainst record);

	int updateByPrimaryKey(Informagainst record);

	List<Informagainst> selectAll();
}