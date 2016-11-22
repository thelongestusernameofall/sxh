package com.sxh.dao;

import java.util.List;

import com.sxh.model.ActivityType;

public interface ActivityTypeMapper {
	int deleteByPrimaryKey(Integer activitytypeid);

	int insert(ActivityType record);

	int insertSelective(ActivityType record);

	ActivityType selectByPrimaryKey(Integer activitytypeid);

	int updateByPrimaryKeySelective(ActivityType record);

	int updateByPrimaryKey(ActivityType record);

	List<ActivityType> selectAll(ActivityType record);
}