package com.sxh.service;

import java.util.HashMap;
import java.util.List;

import com.sxh.model.Message;

/**
 * 
 * 短信和站内信模板Service
 *
 */
public interface MessageService {
	/**
	 * 根据条件获取信息站内信列表
	 * 
	 * @return
	 */
	public List<Message> getMessageList(HashMap<String, String> map);

	/**
	 * 根据条件获取短信列表
	 * 
	 * @return
	 */
	public List<Message> getSmsList(HashMap<String, String> map);

	/**
	 * 根据ID删除信息
	 */
	public int delMessaeById(int id);

	/**
	 * 新增信息
	 * 
	 * @param message
	 * @return
	 */
	public int addMessage(Message message);

	/**
	 * 根据用户id获取某个用户站内信列表
	 * 
	 * @return
	 */
	public List<Message> getMessageListByUserid(HashMap<String, Integer> map);
	/**
	 * 根据用户id获取
	 * 
	 * @return
	 */
	public Message getMessageByid(int id);
}
