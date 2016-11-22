package com.sxh.dao;

import java.util.List;

import com.sxh.model.UserInfo;

public interface UserInfoMapper {
	int deleteByPrimaryKey(Integer userid);

	int insert(UserInfo record);

	int insertSelective(UserInfo record);

	UserInfo selectByPrimaryKey(Integer userid);

	int updateByPrimaryKeySelective(UserInfo record);

	int updateByPrimaryKey(UserInfo record);
	//根据手机号修改密码
	int updatePwdByPhone(UserInfo userInfo);

	// 获取全部用户
	List<UserInfo> selectAll();

	// 根据用户名和密码查询
	List<UserInfo> selectByNameAndPwd(UserInfo record);

	// 根据时间查询用户数量
	List<UserInfo> getCountUserToday(UserInfo userInfo);

	// 根据用户的手机号查询用户ID
	List<UserInfo> getUserIdByPhone(UserInfo userInfo);

	// 根据用户tagid获取用户列表
	List<UserInfo> getUserListByTag(UserInfo userInfo);

	// 根据用户名和密码查询
	List<UserInfo> selectByUserCodeAndPwd(UserInfo record);

	// 根据条件查询会员列表
	List<UserInfo> selectUsers(UserInfo record);

	// 根据手机号查询是否存在该用户
	List<UserInfo> selectByPhone(UserInfo userInfo);

}