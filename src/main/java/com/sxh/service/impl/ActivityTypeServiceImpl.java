package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.ActivityTypeMapper;
import com.sxh.model.ActivityType;
import com.sxh.service.ActivityTypeService;

@Transactional
@Service("ActivityTypeService")
public class ActivityTypeServiceImpl implements ActivityTypeService {

	@Autowired
	private ActivityTypeMapper ActivityTypeMapper;

	public List<ActivityType> getActivityType(ActivityType activitytype) {
		return ActivityTypeMapper.selectAll(activitytype);
	}

	public int addActivityType(ActivityType activitytype) {

		return ActivityTypeMapper.insert(activitytype);
	}

	public int UpdateActivityType(ActivityType activitytype) {
		return ActivityTypeMapper.updateByPrimaryKey(activitytype);
	}

	public ActivityType selectActivityTypeById(int id) {

		return ActivityTypeMapper.selectByPrimaryKey(id);
	}

}
