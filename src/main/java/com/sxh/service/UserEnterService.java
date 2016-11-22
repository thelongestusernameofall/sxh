package com.sxh.service;

import java.util.List;

import com.sxh.model.UserEnter;
import com.sxh.model.UserInfo;

/**
 * 用户报名Service
 *
 */
public interface UserEnterService {
	/**
	 * 根据用户id和活动id查询数据
	 * 
	 * @param userEnter
	 * @return
	 */
	public List<UserEnter> selectByUseridAndActivityid(UserEnter userEnter);

	/**
	 * 添加报名
	 * 
	 * @param userEnter
	 * @return
	 */
	public int addUserEnter(UserEnter userEnter);

	/**
	 * 取消报名
	 * 
	 * @param userEnter
	 * @return
	 */
	public int deleteByActivityidAndUserid(UserEnter userEnter);

	/**
	 * 根据活动id查询报名此活动的人员
	 * 
	 * @param activityid
	 * @return
	 */
	public List<UserInfo> getUserEnterByActivity(int activityid);
}
