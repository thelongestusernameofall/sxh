package com.sxh.dao;

import java.util.List;

import com.sxh.model.RoleInfo;
import com.sxh.vo.UserAuthVo;

public interface RoleInfoMapper {
	int deleteByPrimaryKey(Integer roleid);

	int insert(RoleInfo record);

	int insertSelective(RoleInfo record);

	RoleInfo selectByPrimaryKey(Integer roleid);

	int updateByPrimaryKeySelective(RoleInfo record);

	int updateByPrimaryKey(RoleInfo record);

	List<RoleInfo> getRoleInfoList(RoleInfo roleInfo);

	/**
	 * 获取用户权限列表
	 * 
	 * @param userAuthVo
	 * @return
	 */
	List<UserAuthVo> getUserAuthVoList(UserAuthVo userAuthVo);
}