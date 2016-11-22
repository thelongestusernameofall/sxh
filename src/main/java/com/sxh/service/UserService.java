package com.sxh.service;

import java.util.List;

import com.sxh.model.UserInfo;

public interface UserService {

	public UserInfo getUserById(int id);

	public List<UserInfo> getUsers();

	// 新增用户
	public int insert(UserInfo userInfo);

	// 根据用户信息的账号密码判断是否正确
	public List<UserInfo> SelectByNameAndPwd(UserInfo userInfo);

	// 根据日期查询今日注册人数
	public List<UserInfo> getCountUserToday(UserInfo userInfo);

	// 修改用户信息
	public int UpdateUserInfo(UserInfo suerinfo);

	// 根据输入的手机号查询用户ID
	public List<UserInfo> getUserIdByPhone(UserInfo userInfo);

	// 通过用户ID修改用户头像
	public int UpdateUserPhoto(UserInfo userInfo);

	// 通过用户tagid查询数据
	public List<UserInfo> getUserListByTag(UserInfo userInfo);

	// 根据用户信息的昵称密码判断是否正确
	public List<UserInfo> selectByUserCodeAndPwd(UserInfo userInfo);

	// 根据用户查询条件查询用户列表
	public List<UserInfo> selectUsers(UserInfo userInfo);
	
	// 修改用户状态
	public int UpdateStatusUser(UserInfo userInfo);
	
	public List<UserInfo> selectByPhone(UserInfo userInfo);
	//根据手机修改密码
	public int updatePwdByPhone(UserInfo userInfo);

}
