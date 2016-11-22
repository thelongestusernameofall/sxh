package com.sxh.dao;

import java.util.List;

import com.sxh.model.UserComment;

public interface UserCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserComment record);

    int insertSelective(UserComment record);

    UserComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserComment record);

    int updateByPrimaryKey(UserComment record);
    
    List<UserComment> getUserCommentListByShareId(int id);
}