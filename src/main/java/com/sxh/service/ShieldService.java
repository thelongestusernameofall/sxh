package com.sxh.service;

import java.util.List;

import com.sxh.model.Shield;

public interface ShieldService {
	/**
	 * 根据屏蔽人的userID查询被屏蔽的用户列表
	 */
	public List<Shield> selectShieldListById(Shield shield);

	/**
	 * 新增被屏蔽用户
	 * 
	 * @param Shield
	 * @return
	 */
	public int addShield(Shield shield);

	/**
	 * 删除被屏蔽的用户
	 */
	public int deleteShieldById(int id);

	/**
	 * 根据条件查询被屏蔽的对象
	 * 
	 * @param shield
	 * @return
	 */
	public Shield selectShieldByUseridAndShieldId(Shield shield);

}
