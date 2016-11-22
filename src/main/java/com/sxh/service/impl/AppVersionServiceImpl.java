package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.AppversionMapper;
import com.sxh.model.Appversion;
import com.sxh.service.AppVersionService;

@Transactional
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	private AppversionMapper appversionMapper;

	/**
	 * 获取所有版本信息列表
	 */
	public List<Appversion> appversionList() {
		return appversionMapper.appversionList();

	}

	/**
	 * 获取最大版本号
	 */
	public Appversion getMaxAppVersion() {
		return appversionMapper.getMaxAppVersion();
	}

	/**
	 * 根据id查询信息
	 */
	public Appversion getAppVersionById(int appid) {
		return appversionMapper.selectByPrimaryKey(appid);
	}
}
