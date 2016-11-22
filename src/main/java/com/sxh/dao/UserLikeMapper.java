package com.sxh.dao;

import java.util.List;

import com.sxh.model.UserLike;

public interface UserLikeMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserLike record);

	int insertSelective(UserLike record);

	UserLike selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserLike record);

	int updateByPrimaryKey(UserLike record);

	List<UserLike> getUserLikeListByShareId(int id);
	
	int deleteByShareidAndUserid(UserLike userLike);
	
	List<UserLike> getUserLikeListByUserLike(UserLike userLike);
}