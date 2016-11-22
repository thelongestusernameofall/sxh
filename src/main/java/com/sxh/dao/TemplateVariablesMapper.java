package com.sxh.dao;

import java.util.List;

import com.sxh.model.TemplateVariables;

public interface TemplateVariablesMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TemplateVariables record);

	int insertSelective(TemplateVariables record);

	TemplateVariables selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TemplateVariables record);

	int updateByPrimaryKey(TemplateVariables record);

	// 根据条件查询变量模板列表
	List<TemplateVariables> getTemplateList(TemplateVariables templateVariables);
}