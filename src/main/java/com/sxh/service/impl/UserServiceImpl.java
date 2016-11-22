package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.UserInfoMapper;
import com.sxh.model.UserInfo;
import com.sxh.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	public UserInfo getUserById(int id) {
		return userInfoMapper.selectByPrimaryKey(id);
	}

	public List<UserInfo> getUsers() {
		return userInfoMapper.selectAll();
	}

	public int insert(UserInfo userInfo) {
		int result = userInfoMapper.insert(userInfo);
		System.out.println(result);
		return result;
	}

	public List<UserInfo> SelectByNameAndPwd(UserInfo userInfo) {
		List<UserInfo> userlist = userInfoMapper.selectByNameAndPwd(userInfo);
		return userlist;
	}

	public List<UserInfo> getCountUserToday(UserInfo userInfo) {
		List<UserInfo> userlist = userInfoMapper.getCountUserToday(userInfo);
		return userlist;
	}

	public int UpdateUserInfo(UserInfo userInfo) {
		return userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}

	public List<UserInfo> getUserIdByPhone(UserInfo userInfo) {
		List<UserInfo> userlist = userInfoMapper.getUserIdByPhone(userInfo);
		return userlist;
	}

	public int UpdateUserPhoto(UserInfo userInfo) {
		return userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}

	public List<UserInfo> getUserListByTag(UserInfo userInfo) {

		return userInfoMapper.getUserListByTag(userInfo);
	}

	public List<UserInfo> selectByUserCodeAndPwd(UserInfo userInfo) {
		return userInfoMapper.selectByUserCodeAndPwd(userInfo);
	}

	public List<UserInfo> selectUsers(UserInfo userInfo) {
		return userInfoMapper.selectUsers(userInfo);
	}

	public int UpdateStatusUser(UserInfo userInfo) {
		return userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}

	public List<UserInfo> selectByPhone(UserInfo userInfo) {
		return userInfoMapper.selectByPhone(userInfo);
	}
	
	public int updatePwdByPhone(UserInfo userInfo){
		return userInfoMapper.updatePwdByPhone(userInfo);
	}
}
