package com.sxh.dao;

import java.util.List;

import com.sxh.model.UserCollect;

public interface UserCollectMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserCollect record);

	int insertSelective(UserCollect record);

	UserCollect selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserCollect record);

	int updateByPrimaryKey(UserCollect record);

	// 根据用户id和活动id查询数据
	List<UserCollect> selectByUseridAndActivityid(UserCollect record);

	// 取消收藏
	int deleteByActivityidAndUserid(UserCollect userCollect);

	// 根据活动id查询数据
	List<UserCollect> selectByActivityid(Integer activityid);
}