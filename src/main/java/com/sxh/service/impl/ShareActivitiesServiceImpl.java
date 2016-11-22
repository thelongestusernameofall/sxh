package com.sxh.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.ShareActivitiesMapper;
import com.sxh.model.ShareActivities;
import com.sxh.service.ShareActivitiesService;
import com.sxh.vo.AppShareActivityVo;

@Transactional
@Service("shareActivitiesService")
public class ShareActivitiesServiceImpl implements ShareActivitiesService {

	@Autowired
	private ShareActivitiesMapper shareActivitiesMapper;

	public int insert(ShareActivities shareActivities) {
		return shareActivitiesMapper.insert(shareActivities);
	}

	public int UpdateAttachsById(ShareActivities shareActivities) {

		return shareActivitiesMapper.updateByPrimaryKeySelective(shareActivities);
	}

	public List<ShareActivities> getShareActivitiesList(AppShareActivityVo appShareActivityVo) {
		return shareActivitiesMapper.getShareActivitiesList(appShareActivityVo);
	}

	public int insertShareActivites(ShareActivities shareActivities) {

		return shareActivitiesMapper.insertSelective(shareActivities);
	}

}
