package com.sxh.service;

import java.util.HashMap;
import java.util.List;

import com.sxh.model.Activities;
import com.sxh.vo.ActivityVo;

public interface ActivityService {
	/**
	 * 查询所有活动列表
	 * 
	 * @param map
	 * @return
	 */

	public List<Activities> getActivitiesList(HashMap<String, Integer> map);

	/**
	 * 通过userid查询用户喜欢的活动
	 * 
	 * @param map
	 * @return
	 */
	public List<Activities> getActivitiesListByUserid(HashMap<String, Integer> map);

	/**
	 * 通过userid查询用户参加的活动
	 * 
	 * @param map
	 * @return
	 */
	public List<Activities> getEnterActivitiesListByUserid(HashMap<String, Integer> map);

	/**
	 * WEB查询所有活动列表
	 * 
	 * @param activityVo
	 * @return
	 */

	public List<Activities> getActivitiesListByActivityVo(ActivityVo activityVo);

	/**
	 * WEB端保存活动
	 */
	public int saveActivity(Activities activities);

	/**
	 * WEB端查询今日发布多少活动
	 * 
	 * @param activities
	 * @return
	 */
	public List<Activities> getCountActivityToday(Activities activities);

	/**
	 * WEB端根据id获取详情
	 * 
	 * @param activities
	 * @return
	 */
	public Activities getActivityById(Integer activityid);

	/**
	 * 修改活动
	 * 
	 * @param activities
	 * @return
	 */
	public int updateActivity(Activities activities);

	/**
	 * 查询某个用户的历史活动列表
	 * 
	 * @param activityVo
	 * @return
	 */

	public List<Activities> getHistoryActivitiesListByUserId(ActivityVo activityVo);
	
	

}
