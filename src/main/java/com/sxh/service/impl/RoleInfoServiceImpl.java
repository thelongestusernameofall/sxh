package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.RoleInfoMapper;
import com.sxh.model.RoleInfo;
import com.sxh.service.RoleInfoService;
import com.sxh.vo.UserAuthVo;

@Transactional
@Service("roleInfoService")
public class RoleInfoServiceImpl implements RoleInfoService {

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	public int insert(RoleInfo roleInfo) {
		return roleInfoMapper.insert(roleInfo);
	}

	public RoleInfo selectRoleInfoById(int id) {
		return roleInfoMapper.selectByPrimaryKey(id);
	}

	public List<RoleInfo> getRoleInfoList(RoleInfo roleInfo) {
		return roleInfoMapper.getRoleInfoList(roleInfo);
	}

	public int updateRoleInfo(RoleInfo roleInfo) {
		return roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
	}

	public List<UserAuthVo> getUserAuthVoList(UserAuthVo userAuthVo) {
		return roleInfoMapper.getUserAuthVoList(userAuthVo);
	}

}
