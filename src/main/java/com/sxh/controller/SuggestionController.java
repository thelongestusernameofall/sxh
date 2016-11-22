package com.sxh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sxh.model.ActivityType;
import com.sxh.model.Appversion;
import com.sxh.model.Suggestions;
import com.sxh.service.ActivityTypeService;
import com.sxh.service.AppVersionService;
import com.sxh.service.SuggestionService;
import com.sxh.vo.SuggestionsVo;

@Controller
@RequestMapping("/suggestion")
public class SuggestionController {
	@Autowired
	private SuggestionService suggestionService;
	@Autowired
	private AppVersionService appVersionService;

	@RequestMapping("/showsug")
	public ModelAndView showSug(HttpServletRequest request) {

		// 获取输入的查询条件
		String sugcontent = "";// 反馈内容的模糊查询
		String contract = "";// 联系方式
		String version = "0";// 版本号
		String replystatus = "4";// 回复状态
		String startdate = "";// 开始时间
		String enddate = "";// 结束时间
		if (!"".equals(request.getParameter("sugcontent")) && request.getParameter("sugcontent") != null) {
			sugcontent = request.getParameter("sugcontent").toString();
		}
		if (!"".equals(request.getParameter("contact")) && request.getParameter("contact") != null) {
			contract = request.getParameter("contact").toString();
		}
		if (!"".equals(request.getParameter("version")) && request.getParameter("version") != null) {
			version = request.getParameter("version").toString();
		}
		if (!"".equals(request.getParameter("replystatus")) && request.getParameter("replystatus") != null) {
			replystatus = request.getParameter("replystatus").toString();
		}
		if (!"".equals(request.getParameter("startdate")) && request.getParameter("startdate") != null) {
			startdate = request.getParameter("startdate").toString()+" 00:00:00";
		}
		if (!"".equals(request.getParameter("enddate")) && request.getParameter("enddate") != null) {
			enddate = request.getParameter("enddate").toString()+" 00:00:00";
		}
		SuggestionsVo suggestionsVo = new SuggestionsVo();
		suggestionsVo.setSugcontent(sugcontent);
		suggestionsVo.setContract(contract);
		suggestionsVo.setVersion(version);
		suggestionsVo.setReplystatus(Integer.parseInt(replystatus));
		suggestionsVo.setStartdate(startdate);
		suggestionsVo.setEnddate(enddate);

		ModelAndView mv = new ModelAndView();
		List<Suggestions> suggestionList = suggestionService.getSuggestionsList(suggestionsVo);
		System.out.println(suggestionList.size());
		// 截取字符串过长的内容
		if (suggestionList.size() > 0) {
			for (int i = 0; i < suggestionList.size(); i++) {
				String sucont = suggestionList.get(i).getSugcontent();
				if (sucont.length() > 10) {
					sucont = sucont.substring(0, 10) + "...";
				}
				suggestionList.get(i).setSugcontent(sucont);
				if (!"".equals(suggestionList.get(i).getReplycontent())
						&& suggestionList.get(i).getReplycontent() != null) {
					String replycont = suggestionList.get(i).getReplycontent();
					if (replycont.length() > 10) {
						replycont = replycont.substring(0, 10) + "...";
					}
					suggestionList.get(i).setReplycontent(replycont);
				}
			}
		}
		//查询所有版本号
		List<Appversion> appversionList = appVersionService.appversionList();
		mv.addObject("suggestionList", suggestionList);
		mv.addObject("appversionList", appversionList);
		mv.setViewName("/resp/respList");

		return mv;
	}

	@RequestMapping("/delSug")
	public void delSug(@RequestParam(value = "id") int id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");

		int i = suggestionService.DelSuggestions(id);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("success", "删除成功!");
		} else {
			map.put("success", "删除失败!");
		}
		String jsonResult = JSON.toJSONString(map);
		System.out.println("=============jsonResult=" + jsonResult);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
		out.close();
	}

	@RequestMapping("/updateSug")
	public void updateSug(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		//获取传入后台的参数
		String id = request.getParameter("id").toString();
		String replycontent = request.getParameter("replycontent").toString();
		String replystatus = request.getParameter("replystatus").toString();
		Suggestions suggestions = new Suggestions();
		suggestions.setId(Integer.parseInt(id));
		suggestions.setReplycontent(replycontent);
		suggestions.setReplystatus(Integer.parseInt(replystatus));
		int i = suggestionService.UpdateSuggestions(suggestions);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("success", "回复成功!");
		} else {
			map.put("fail", "回复失败!");
		}
		String jsonResult = JSON.toJSONString(map);
		System.out.println("=============jsonResult=" + jsonResult);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
		out.close();
	}

	@RequestMapping("/getSuggestionById")
	public ModelAndView getSuggestionById(HttpServletRequest request) {
		// 获取传入的ID
		int id = Integer.parseInt(request.getParameter("id").toString());
		ModelAndView mv = new ModelAndView();
		Suggestions suggestion = suggestionService.getSuggestions(id);
		mv.addObject("suggestion", suggestion);
		// mv.addObject("page", page);
		mv.setViewName("/resp/respDetail");
		return mv;
	}

}
