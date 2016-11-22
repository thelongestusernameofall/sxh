package com.sxh.service;

import java.util.List;

import com.sxh.model.UserLike;

public interface UserLikeService {

	public int insert(UserLike userLike);

	public UserLike selectUserLikeById(int id);

	public List<UserLike> getUserLikeListByShareId(int id);
	
	public List<UserLike> getUserLikeListByUserLike(UserLike userLike);

	public int deleteByShareidAndUserid(UserLike userLike);

}
