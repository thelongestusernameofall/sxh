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
import com.sxh.model.ActivityType;
import com.sxh.service.ActivityTypeService;

@Controller
@RequestMapping("/activitytype")
public class ActivityTypeController {
	@Autowired
	private ActivityTypeService activityTypeService;

	@RequestMapping("/showacttype")
	public ModelAndView showUserTags(HttpServletRequest request) {
		// 获取输入的查询条件
		String typename = "";
		String status = "2";
		if (!"".equals(request.getParameter("typename")) && request.getParameter("typename") != null) {
			typename = request.getParameter("typename").toString();
		}
		if (!"".equals(request.getParameter("typestatus")) && request.getParameter("typestatus") != null) {
			status = request.getParameter("typestatus").toString();
		}
		ActivityType activitytype = new ActivityType();
		activitytype.setActivityname(typename);
		activitytype.setStatus(Integer.parseInt(status));
		ModelAndView mv = new ModelAndView();
		List<ActivityType> activityTypeList = activityTypeService.getActivityType(activitytype);
		System.out.println(activityTypeList.size());
		mv.addObject("activityTypeList", activityTypeList);
		// mv.addObject("page", page);
		mv.setViewName("/activity/actType");
		return mv;
	}

	@RequestMapping("/addActType")
	public void addActType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String typeName = request.getParameter("typename");
		String typeStatus = request.getParameter("typestatus");
		ActivityType activityType = new ActivityType();
		activityType.setActivityname(typeName);
		activityType.setStatus(Integer.parseInt(typeStatus));
		int i = activityTypeService.addActivityType(activityType);
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

	@RequestMapping("/delActType")
	public void delActType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String typeid = request.getParameter("typeid");
		String typename = request.getParameter("typename");
		String typestatus = request.getParameter("typestatus");
		ActivityType activityType = new ActivityType();
		activityType.setActivitytypeid(Integer.parseInt(typeid));
		activityType.setActivityname(typename);
		activityType.setStatus(Integer.parseInt(typestatus));
		int i = activityTypeService.UpdateActivityType(activityType);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			if ("1".equals(typestatus)) {
				map.put("success", "启用成功!");
			}
			if ("0".equals(typestatus)) {
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

	@RequestMapping("/updateActType")
	public void updateActType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String typeid = request.getParameter("typeid");
		String typename = request.getParameter("typename");
		String typestatus = request.getParameter("typestatus");
		ActivityType activityType = new ActivityType();
		activityType.setActivitytypeid(Integer.parseInt(typeid));
		activityType.setActivityname(typename);
		activityType.setStatus(Integer.parseInt(typestatus));
		int i = activityTypeService.UpdateActivityType(activityType);
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
