package com.sxh.service;

import java.util.List;

import com.sxh.model.Informagainst;

public interface InformagainstService {

	/**
	 * 获取所有举报信息
	 * 
	 * @return
	 */
	public List<Informagainst> SelectAll();

	/**
	 * 添加举报信息
	 * 
	 * @param informagainst
	 * @return
	 */
	public int addInformagainst(Informagainst informagainst);

	/**
	 * 删除举报信息
	 */
	public int deleteShieldById(int id);

}
