package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.SmsTemplateMapper;
import com.sxh.model.SmsTemplate;
import com.sxh.service.SmsTemplateService;

@Transactional
@Service("smsTemplateService")
public class SmsTemplateServiceImpl implements SmsTemplateService {
	@Autowired
	private SmsTemplateMapper smsTemplateMapper;

	public List<SmsTemplate> getSmsTemplateList(SmsTemplate smsTemplate){
		return smsTemplateMapper.getSmsTemplateList(smsTemplate);
	}

	public int addSmsTemplate(SmsTemplate smsTemplate) {
		return smsTemplateMapper.insert(smsTemplate);
	}

	public int updateStatusSmsTemplate(SmsTemplate smsTemplate) {
		return smsTemplateMapper.updateByPrimaryKeySelective(smsTemplate);
	}

	public int delSmsTemplate(SmsTemplate smsTemplate) {
		return smsTemplateMapper.updateByPrimaryKeySelective(smsTemplate);
	}

	public SmsTemplate selectSmsTemplateById(int id) {
		return smsTemplateMapper.selectByPrimaryKey(id);
	}

	public int updateSmsTemplate(SmsTemplate smsTemplate) {
		return smsTemplateMapper.updateByPrimaryKeySelective(smsTemplate);
	}
}
