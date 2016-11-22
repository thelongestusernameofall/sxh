package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.UserEnterMapper;
import com.sxh.model.UserEnter;
import com.sxh.model.UserInfo;
import com.sxh.service.UserEnterService;

@Transactional
@Service("UserEnterService")
public class UserEnterServiceImpl implements UserEnterService {

	@Autowired
	private UserEnterMapper userEnterMapper;

	/**
	 * 根据用户id和活动id查询数据
	 */
	public List<UserEnter> selectByUseridAndActivityid(UserEnter userEnter) {

		return userEnterMapper.selectByUseridAndActivityid(userEnter);
	}

	/**
	 * 添加报名
	 */
	public int addUserEnter(UserEnter userEnter) {
		return userEnterMapper.insert(userEnter);
	}

	/**
	 * 取消报名
	 */
	public int deleteByActivityidAndUserid(UserEnter userEnter) {
		return userEnterMapper.deleteByActivityidAndUserid(userEnter);
	}

	public List<UserInfo> getUserEnterByActivity(int activityid) {
		return userEnterMapper.getUserEnterByActivity(activityid);
	}

}
