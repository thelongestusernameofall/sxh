package com.sxh.service;

import java.util.List;

import com.sxh.model.SmsTemplate;

/**
 * 
 * 短信和站内信模板Service
 *
 */
public interface SmsTemplateService {
	/**
	 * 根据条件获取模板列表
	 * 
	 * @return
	 */
	public List<SmsTemplate> getSmsTemplateList(SmsTemplate smsTemplate);

	/**
	 * 根据ID查询模板详情
	 */
	public SmsTemplate selectSmsTemplateById(int id);

	/**
	 * 新增模板
	 * 
	 * @param templateVariables
	 * @return
	 */
	public int addSmsTemplate(SmsTemplate smsTemplate);

	/**
	 * 修改模板变量状态
	 */
	public int updateStatusSmsTemplate(SmsTemplate smsTemplate);

	/**
	 * 根据id删除模板
	 * 
	 * @param id
	 * @return
	 */
	public int delSmsTemplate(SmsTemplate smsTemplate);
	
	/**
	 * 修改数据
	 * 
	 * @param id
	 * @return
	 */
	public int updateSmsTemplate(SmsTemplate smsTemplate);

}
