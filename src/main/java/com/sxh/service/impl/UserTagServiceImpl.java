package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.UserTagMapper;
import com.sxh.model.UserTag;
import com.sxh.service.UserTagService;

@Transactional
@Service("userTagService")
public class UserTagServiceImpl implements UserTagService {
	@Autowired
	private UserTagMapper userTagMapper;

	public List<UserTag> getUserTags(UserTag usertag) {
		return userTagMapper.selectAll(usertag);
	}

	public int addUserTag(UserTag userTag) {
		return userTagMapper.insert(userTag);
	}

	public int UpdateStatusUserTag(UserTag usertag) {
		return userTagMapper.updateByPrimaryKey(usertag);
	}

	public List<UserTag> selectAllUserTag() {
		return userTagMapper.selectAllUserTag();
	}

	public UserTag selectUserTagById(int id) {
		return userTagMapper.selectByPrimaryKey(id);
	}

}
