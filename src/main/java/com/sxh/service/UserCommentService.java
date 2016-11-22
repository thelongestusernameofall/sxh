package com.sxh.service;

import java.util.List;

import com.sxh.model.UserComment;

public interface UserCommentService {

	public int insert(UserComment userComment);
	
	public UserComment selectAttachById(int id);
	
	public List<UserComment> getUserCommentListByShareId(int id);

}
