package com.sxh.dao;

import java.util.HashMap;
import java.util.List;

import com.sxh.model.Message;

public interface MessageMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Message record);

	int insertSelective(Message record);

	Message selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Message record);

	int updateByPrimaryKey(Message record);

	// 获取站内信列表
	List<Message> getMessageList(HashMap<String, String> map);

	// 获取短息列表
	List<Message> getSmsList(HashMap<String, String> map);

	// 根据用户id获取某个用户的站内信列表
	List<Message> getMessageListByUserid(HashMap<String, Integer> map);
}