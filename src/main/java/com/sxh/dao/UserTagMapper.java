package com.sxh.dao;

import java.util.List;

import com.sxh.model.UserTag;

public interface UserTagMapper {
    int deleteByPrimaryKey(Integer tagid);

    int insert(UserTag record);

    int insertSelective(UserTag record);

    UserTag selectByPrimaryKey(Integer tagid);

    int updateByPrimaryKeySelective(UserTag record);

    int updateByPrimaryKey(UserTag record);
    
    List<UserTag> selectAll(UserTag record);
    
    List<UserTag> selectAllUserTag();
    
    
}