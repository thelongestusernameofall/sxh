package com.sxh.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.ActivitiesMapper;
import com.sxh.model.Activities;
import com.sxh.service.ActivityService;
import com.sxh.vo.ActivityVo;

@Transactional
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivitiesMapper activitiesMapper;

	/**
	 * 获取所有活动信息列表
	 */
	public List<Activities> getActivitiesList(HashMap<String, Integer> map) {
		return activitiesMapper.selectAll(map);
	}

	/**
	 * 获取用户收藏的活动列表
	 */
	public List<Activities> getActivitiesListByUserid(HashMap<String, Integer> map) {
		return activitiesMapper.getActivitiesListByUserid(map);
	}

	/**
	 * 获取用户参加的活动列表
	 */
	public List<Activities> getEnterActivitiesListByUserid(HashMap<String, Integer> map) {
		return activitiesMapper.getEnterActivitiesListByUserid(map);
	}

	/**
	 * WEB端获取活动列表
	 */
	public List<Activities> getActivitiesListByActivityVo(ActivityVo activityVo) {
		return activitiesMapper.getActivitiesListByActivityVo(activityVo);
	}

	/**
	 * 保存活动
	 */
	public int saveActivity(Activities activities) {
		return activitiesMapper.insert(activities);
	}

	/**
	 * 查询当天的活动数量
	 */
	public List<Activities> getCountActivityToday(Activities activities) {
		return activitiesMapper.getCountActivityToday(activities);
	}

	/**
	 * 根据id获取活动详情
	 */
	public Activities getActivityById(Integer activityid) {
		return activitiesMapper.selectByPrimaryKey(activityid);
	}

	/**
	 * 修改活动
	 */
	public int updateActivity(Activities activities) {
		return activitiesMapper.updateByPrimaryKeySelective(activities);
	}

	/**
	 * 获取某个用户的历史活动
	 */
	public List<Activities> getHistoryActivitiesListByUserId(ActivityVo activityVo) {
		return activitiesMapper.getHistoryActivitiesListByUserId(activityVo);
	}
}
