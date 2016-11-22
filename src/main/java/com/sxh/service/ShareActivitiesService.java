package com.sxh.service;

import java.util.List;

import com.sxh.model.ShareActivities;
import com.sxh.vo.AppShareActivityVo;

public interface ShareActivitiesService {

	public int insert(ShareActivities shareActivities);

	public int insertShareActivites(ShareActivities shareActivities);

	public int UpdateAttachsById(ShareActivities shareActivities);

	public List<ShareActivities> getShareActivitiesList(AppShareActivityVo appShareActivityVo);

}
