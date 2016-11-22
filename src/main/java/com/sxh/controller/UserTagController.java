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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sxh.model.UserTag;
import com.sxh.service.UserTagService;

@Controller
@RequestMapping("/usertag")
public class UserTagController {
	@Autowired
	private UserTagService userTagService;

	@RequestMapping("/showtags")
	public ModelAndView showUserTags(HttpServletRequest request) {
		// 获取输入的查询条件
		String tagname = "";
		String status = "2";
		if (!"".equals(request.getParameter("tagname")) && request.getParameter("tagname") != null) {
			tagname = request.getParameter("tagname").toString();
		}
		if (!"".equals(request.getParameter("tagstatus")) && request.getParameter("tagstatus") != null) {
			status = request.getParameter("tagstatus").toString();
		}
		UserTag usertag = new UserTag();
		usertag.setTagname(tagname);
		usertag.setStatus(Integer.parseInt(status));
		ModelAndView mv = new ModelAndView();
		List<UserTag> userTagList = userTagService.getUserTags(usertag);
		System.out.println(userTagList.size());
		mv.addObject("userTagList", userTagList);
		// mv.addObject("page", page);
		mv.setViewName("/userTag");
		return mv;
	}

	@RequestMapping("/addtags")
	public void addUserTags(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String tagName = request.getParameter("tagname");
		String tagStatus = request.getParameter("tagstatus");
		UserTag userTag = new UserTag();
		userTag.setTagname(tagName);
		userTag.setStatus(Integer.parseInt(tagStatus));
		int i = userTagService.addUserTag(userTag);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("success", "添加成功!");
		} else {
			map.put("fail", "添加失败!");
		}
		String jsonResult = JSON.toJSONString(map);
		System.out.println("=============jsonResult=" + jsonResult);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
		out.close();
	}

	@RequestMapping("/deltags")
	public void delUserTags(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String tagid = request.getParameter("tagid");
		String tagName = request.getParameter("tagname");
		String tagStatus = request.getParameter("tagstatus");
		UserTag usertag = new UserTag();
		usertag.setTagid(Integer.parseInt(tagid));
		usertag.setTagname(tagName);
		usertag.setStatus(Integer.parseInt(tagStatus));
		int i = userTagService.UpdateStatusUserTag(usertag);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			if ("1".equals(tagStatus)) {
				map.put("success", "启用成功!");
			}
			if ("0".equals(tagStatus)) {
				map.put("success", "禁用成功!");
			}
		} else {
			map.put("fail", "失败!");
		}
		String jsonResult = JSON.toJSONString(map);
		System.out.println("=============jsonResult=" + jsonResult);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
		out.close();
	}

	@RequestMapping("/updatetags")
	public void updateUserTags(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// 获取用户标签的id,名称,状态
		String tagId = request.getParameter("tagid");
		String tagName = request.getParameter("tagname");
		String tagStatus = request.getParameter("tagstatus");
		UserTag userTag = new UserTag();
		userTag.setTagid(Integer.parseInt(tagId));
		userTag.setTagname(tagName);
		userTag.setStatus(Integer.parseInt(tagStatus));
		int i = userTagService.UpdateStatusUserTag(userTag);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("success", "修改成功!");
		} else {
			map.put("fail", "修改失败!");
		}
		String jsonResult = JSON.toJSONString(map);
		System.out.println("=============jsonResult=" + jsonResult);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
		out.close();
	}

}
