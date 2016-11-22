package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.UserLikeMapper;
import com.sxh.model.UserLike;
import com.sxh.service.UserLikeService;

@Transactional
@Service("userLikeService")
public class UserLikeServiceImpl implements UserLikeService {

	@Autowired
	private UserLikeMapper userLikeMapper;

	public int insert(UserLike userLike) {
		return userLikeMapper.insert(userLike);
	}

	public UserLike selectUserLikeById(int id) {
		return userLikeMapper.selectByPrimaryKey(id);
	}

	public List<UserLike> getUserLikeListByShareId(int id) {
		return userLikeMapper.getUserLikeListByShareId(id);
	}

	public int deleteByShareidAndUserid(UserLike userLike) {

		return userLikeMapper.deleteByShareidAndUserid(userLike);
	}

	public List<UserLike> getUserLikeListByUserLike(UserLike userLike) {
		
		return userLikeMapper.getUserLikeListByUserLike(userLike);
	}

}
