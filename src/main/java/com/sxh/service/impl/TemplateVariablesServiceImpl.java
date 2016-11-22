package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.TemplateVariablesMapper;
import com.sxh.model.TemplateVariables;
import com.sxh.service.TemplateVariablesService;

@Transactional
@Service("templateVariablesService")
public class TemplateVariablesServiceImpl implements TemplateVariablesService {
	@Autowired
	private TemplateVariablesMapper templateVariablesMapper;

	public List<TemplateVariables> getTemplateList(TemplateVariables templateVariables){
		return templateVariablesMapper.getTemplateList(templateVariables);
	}

	public int addTemplateVariables(TemplateVariables templateVariables) {
		return templateVariablesMapper.insert(templateVariables);
	}

	public int updateStatusTemplateVariables(TemplateVariables templateVariables) {
		return templateVariablesMapper.updateByPrimaryKeySelective(templateVariables);
	}

	public int delTemplateVariables(TemplateVariables templateVariables) {
		return templateVariablesMapper.updateByPrimaryKeySelective(templateVariables);
	}

	public TemplateVariables selectTemplateVariablesById(int id) {
		return templateVariablesMapper.selectByPrimaryKey(id);
	}

}
