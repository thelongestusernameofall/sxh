package com.sxh.dao;

import java.util.HashMap;
import java.util.List;

import com.sxh.model.Activities;
import com.sxh.vo.ActivityVo;

public interface ActivitiesMapper {
	int deleteByPrimaryKey(Integer activityid);

	int insert(Activities record);

	int insertSelective(Activities record);

	Activities selectByPrimaryKey(Integer activityid);

	int updateByPrimaryKeySelective(Activities record);

	int updateByPrimaryKey(Activities record);

	List<Activities> selectAll(HashMap<String, Integer> map);

	List<Activities> getActivitiesListByUserid(HashMap<String, Integer> map);

	List<Activities> getEnterActivitiesListByUserid(HashMap<String, Integer> map);

	List<Activities> getActivitiesListByActivityVo(ActivityVo activityVo);

	List<Activities> getCountActivityToday(Activities activities);
	
	List<Activities> getHistoryActivitiesListByUserId(ActivityVo activityVo);

}