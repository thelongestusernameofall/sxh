package com.sxh.service;

import java.util.List;

import com.sxh.model.Suggestions;
import com.sxh.vo.SuggestionsVo;

public interface SuggestionService {
	/**
	 * 获取活动类型列表
	 * 
	 * @return
	 */
	public List<Suggestions> getSuggestionsList(SuggestionsVo suggestionsVo);

	/**
	 * 新增反馈意见
	 * 
	 * @param
	 * @return
	 */
	public int addSuggestions(Suggestions suggestions);

	/**
	 * 获取反馈意见详情
	 * 
	 * @param id
	 * @return
	 */
	public Suggestions getSuggestions(int id);

	/**
	 * 修改反馈意见
	 */
	public int UpdateSuggestions(Suggestions suggestions);

	/**
	 * 删除反馈意见
	 */
	public int DelSuggestions(int id);

}
