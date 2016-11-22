package com.sxh.dao;

import java.util.List;

import com.sxh.model.Shield;

public interface ShieldMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Shield record);

	int insertSelective(Shield record);

	Shield selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Shield record);

	int updateByPrimaryKey(Shield record);

	List<Shield> selectShieldListById(Shield record);

	Shield selectShieldByUseridAndShieldId(Shield record);
}