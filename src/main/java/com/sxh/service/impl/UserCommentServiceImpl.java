package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.UserCommentMapper;
import com.sxh.model.UserComment;
import com.sxh.service.UserCommentService;

@Transactional
@Service("userCommentService")
public class UserCommentServiceImpl implements UserCommentService {

	@Autowired
	private UserCommentMapper commentMapper;

	public int insert(UserComment userComment) {
		return commentMapper.insert(userComment);
	}

	public UserComment selectAttachById(int id) {
		return commentMapper.selectByPrimaryKey(id);
	}

	public List<UserComment> getUserCommentListByShareId(int id) {
		return commentMapper.getUserCommentListByShareId(id);
	}

}
