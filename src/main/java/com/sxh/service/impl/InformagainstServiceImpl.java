package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.InformagainstMapper;
import com.sxh.model.Informagainst;
import com.sxh.service.InformagainstService;

@Transactional
@Service("informagainstService")
public class InformagainstServiceImpl implements InformagainstService {
	@Autowired
	private InformagainstMapper informagainstMapper;

	public List<Informagainst> SelectAll() {
		return informagainstMapper.selectAll();
	}

	public int addInformagainst(Informagainst informagainst) {
		return informagainstMapper.insert(informagainst);
	}

	public int deleteShieldById(int id) {
		return informagainstMapper.deleteByPrimaryKey(id);
	}

}
