package com.sxh.service;

import java.util.List;

import com.sxh.model.ActivityType;

public interface ActivityTypeService {
	/**
	 * 获取活动类型列表
	 * 
	 * @return
	 */
	public List<ActivityType> getActivityType(ActivityType activitytype);

	/**
	 * 新增活动类型
	 * 
	 * @param
	 * @return
	 */
	public int addActivityType(ActivityType activitytype);

	/**
	 * 修改活动类型状态
	 */
	public int UpdateActivityType(ActivityType activitytype);

	/**
	 * 根据id查询类型
	 */
	public ActivityType selectActivityTypeById(int id);

}
