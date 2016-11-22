package com.sxh.dao;

import java.util.List;

import com.sxh.model.SmsTemplate;

public interface SmsTemplateMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SmsTemplate record);

	int insertSelective(SmsTemplate record);

	SmsTemplate selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SmsTemplate record);

	int updateByPrimaryKey(SmsTemplate record);

	List<SmsTemplate> getSmsTemplateList(SmsTemplate smsTemplate);
}