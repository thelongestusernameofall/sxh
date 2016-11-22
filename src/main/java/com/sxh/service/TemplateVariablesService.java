package com.sxh.service;

import java.util.List;

import com.sxh.model.TemplateVariables;

/**
 * 
 * 模板变量Service
 *
 */
public interface TemplateVariablesService {
	/**
	 * 根据条件获取末班变量列表
	 * 
	 * @return
	 */
	public List<TemplateVariables> getTemplateList(TemplateVariables templateVariables);

	/**
	 * 根据ID查询变量信息
	 */
	public TemplateVariables selectTemplateVariablesById(int id);

	/**
	 * 新增模板变量
	 * 
	 * @param templateVariables
	 * @return
	 */
	public int addTemplateVariables(TemplateVariables templateVariables);

	/**
	 * 修改模板变量状态
	 */
	public int updateStatusTemplateVariables(TemplateVariables templateVariables);

	/**
	 * 根据id删除模板变量
	 * 
	 * @param id
	 * @return
	 */
	public int delTemplateVariables(TemplateVariables templateVariables);

}
