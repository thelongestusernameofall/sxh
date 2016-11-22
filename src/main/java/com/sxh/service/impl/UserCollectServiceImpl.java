package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.UserCollectMapper;
import com.sxh.model.UserCollect;
import com.sxh.service.UserCollectService;

@Transactional
@Service("userCollectService")
public class UserCollectServiceImpl implements UserCollectService {

	@Autowired
	private UserCollectMapper userCollectMapper;

	/**
	 * 根据用户id和活动id查询数据
	 */
	public List<UserCollect> selectByUseridAndActivityid(UserCollect userCollect) {

		return userCollectMapper.selectByUseridAndActivityid(userCollect);
	}

	/**
	 * 添加收藏
	 */
	public int addCollect(UserCollect userCollect) {
		return userCollectMapper.insert(userCollect);
	}

	/**
	 * 取消收藏
	 */
	public int deleteByActivityidAndUserid(UserCollect userCollect) {

		return userCollectMapper.deleteByActivityidAndUserid(userCollect);
	}

	/**
	 * 根据活动id查询数据
	 */
	public List<UserCollect> selectByActivityid(Integer activityid) {

		return userCollectMapper.selectByActivityid(activityid);
	}

}
