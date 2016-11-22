package com.sxh.service;

import java.util.List;

import com.sxh.model.RoleInfo;
import com.sxh.vo.UserAuthVo;

public interface RoleInfoService {

	public int insert(RoleInfo roleInfo);

	public RoleInfo selectRoleInfoById(int id);

	public List<RoleInfo> getRoleInfoList(RoleInfo roleInfo);

	public int updateRoleInfo(RoleInfo roleInfo);

	// 获取用户权限列表
	public List<UserAuthVo> getUserAuthVoList(UserAuthVo userAuthVo);

}
