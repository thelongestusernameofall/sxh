package com.sxh.service;

import com.sxh.model.Attach;

public interface AttachService {

	public int insert(Attach attach);
	
	public Attach selectAttachById(int id);

}
