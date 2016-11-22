package com.sxh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.AttachMapper;
import com.sxh.model.Attach;
import com.sxh.service.AttachService;

@Transactional
@Service("attachService")
public class AttachServiceImpl implements AttachService {

	@Autowired
	private AttachMapper attachMapper;

	public int insert(Attach attach) {
		return attachMapper.insert(attach);
	}

	public Attach selectAttachById(int id) {
		return attachMapper.selectByPrimaryKey(id);
	}

}
