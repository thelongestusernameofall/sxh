package com.sxh.service;

import java.util.List;

import com.sxh.model.UserTag;

public interface UserTagService {
	/**
	 * 根据条件获取用户标签列表
	 * 
	 * @return
	 */
	public List<UserTag> getUserTags(UserTag usertag);
	/**
	 * 根据ID查询标签信息
	 */
	public UserTag selectUserTagById(int id);
	/**
	 * 获取用户标签列表
	 * 
	 * @return
	 */
	public List<UserTag> selectAllUserTag();

	/**
	 * 新增用户标签
	 * 
	 * @param userTag
	 * @return
	 */
	public int addUserTag(UserTag userTag);

	/**
	 * 修改用户标签状态
	 */
	public int UpdateStatusUserTag(UserTag usertag);
	
}
