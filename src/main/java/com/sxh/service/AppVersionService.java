package com.sxh.service;

import java.util.HashMap;
import java.util.List;

import com.sxh.model.Activities;
import com.sxh.model.Appversion;
import com.sxh.vo.ActivityVo;

public interface AppVersionService {
	/**
	 * 查询所有版本列表
	 * 
	 * @param map
	 * @return
	 */

	public List<Appversion> appversionList();

	/**
	 * 查询最大版本号
	 * 
	 * @param map
	 * @return
	 */
	public Appversion getMaxAppVersion();

	/**
	 * 根据id查询最新版本信息
	 * 
	 * @param appid
	 * @return
	 */
	public Appversion getAppVersionById(int appid);

}
