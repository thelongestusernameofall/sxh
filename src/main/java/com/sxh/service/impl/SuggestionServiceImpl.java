package com.sxh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxh.dao.SuggestionsMapper;
import com.sxh.model.Suggestions;
import com.sxh.service.SuggestionService;
import com.sxh.vo.SuggestionsVo;

@Transactional
@Service("SuggestionService")
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired
	private SuggestionsMapper SuggestionsMapper;

	public List<Suggestions> getSuggestionsList(SuggestionsVo suggestionsVo) {
		return SuggestionsMapper.selectAll(suggestionsVo);
	}

	public int addSuggestions(Suggestions suggestions) {
		return SuggestionsMapper.insert(suggestions);
	}

	public Suggestions getSuggestions(int id) {
		return SuggestionsMapper.selectByPrimaryKey(id);
	}

	public int UpdateSuggestions(Suggestions suggestions) {
		return SuggestionsMapper.updateByPrimaryKeySelective(suggestions);
	}

	public int DelSuggestions(int id) {
		return SuggestionsMapper.deleteByPrimaryKey(id);
	}

}
