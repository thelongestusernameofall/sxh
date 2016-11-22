package com.sxh.dao;

import java.util.List;

import com.sxh.model.UserEnter;
import com.sxh.model.UserInfo;

public interface UserEnterMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserEnter record);

	int insertSelective(UserEnter record);

	UserEnter selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserEnter record);

	int updateByPrimaryKey(UserEnter record);

	List<UserEnter> selectByUseridAndActivityid(UserEnter userEnter);

	// 取消报名
	int deleteByActivityidAndUserid(UserEnter userEnter);

	// 根据活动id查询报名人员列表
	List<UserInfo> getUserEnterByActivity(int activityid);
}