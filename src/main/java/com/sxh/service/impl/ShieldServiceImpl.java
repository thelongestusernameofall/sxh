package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.ShieldMapper;
import com.sxh.model.Shield;
import com.sxh.service.ShieldService;

@Transactional
@Service("shieldService")
public class ShieldServiceImpl implements ShieldService {
	@Autowired
	private ShieldMapper shieidMapper;

	public List<Shield> selectShieldListById(Shield shield) {
		return shieidMapper.selectShieldListById(shield);
	}

	public int addShield(Shield shield) {
		return shieidMapper.insert(shield);
	}

	public int deleteShieldById(int id) {
		return shieidMapper.deleteByPrimaryKey(id);
	}

	public Shield selectShieldByUseridAndShieldId(Shield shield) {
		return shieidMapper.selectShieldByUseridAndShieldId(shield);
	}

}
