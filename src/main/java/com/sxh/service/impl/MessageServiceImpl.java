package com.sxh.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.MessageMapper;
import com.sxh.model.Message;
import com.sxh.service.MessageService;

@Transactional
@Service("messageService")
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageMapper messageMapper;

	public List<Message> getMessageList(HashMap<String, String> map) {
		return messageMapper.getMessageList(map);
	}

	public List<Message> getSmsList(HashMap<String, String> map) {
		return messageMapper.getSmsList(map);
	}

	public int addMessage(Message message) {
		return messageMapper.insert(message);
	}

	public int delMessaeById(int id) {
		return messageMapper.deleteByPrimaryKey(id);
	}

	public List<Message> getMessageListByUserid(HashMap<String, Integer> map) {
		return messageMapper.getMessageListByUserid(map);
	}

	public Message getMessageByid(int id) {
		// TODO Auto-generated method stub
		return messageMapper.selectByPrimaryKey(id);
	}

}
