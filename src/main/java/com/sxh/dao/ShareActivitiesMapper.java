package com.sxh.dao;

import java.util.List;

import com.sxh.model.ShareActivities;
import com.sxh.vo.AppShareActivityVo;

public interface ShareActivitiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShareActivities record);

    int insertSelective(ShareActivities record);

    ShareActivities selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareActivities record);

    int updateByPrimaryKey(ShareActivities record);
    
    List<ShareActivities> getShareActivitiesList(AppShareActivityVo appShareActivityVo);
}