package com.sxh.service;

import java.util.List;

import com.sxh.model.UserCollect;

/**
 * 
 * 用户收藏活动Service
 *
 */
public interface UserCollectService {
	/**
	 * 根据用户id和活动id查询收藏数据列表
	 * 
	 * @param userCollect
	 * @return
	 */
	public List<UserCollect> selectByUseridAndActivityid(UserCollect userCollect);

	/**
	 * 添加收藏
	 * 
	 * @param userCollect
	 * @return
	 */
	public int addCollect(UserCollect userCollect);

	/**
	 * 根据活动id和用户id删除收藏
	 * 
	 * @param userCollect
	 * @return
	 */
	public int deleteByActivityidAndUserid(UserCollect userCollect);

	/**
	 * 根据活动id查询收藏列表
	 * 
	 * @param activityid
	 * @return
	 */
	public List<UserCollect> selectByActivityid(Integer activityid);

}
