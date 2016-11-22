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
import com.sxh.model.TemplateVariables;
import com.sxh.service.TemplateVariablesService;

@Controller
@RequestMapping("/template")
public class TemplateController {
	@Autowired
	private TemplateVariablesService templateVariablesService;

	@RequestMapping("/showtemplates")
	public ModelAndView showtemplates(HttpServletRequest request) {
		// 获取输入的查询条件
		String templatename = "";
		String templatestatus = "2";
		if (!"".equals(request.getParameter("templatename")) && request.getParameter("templatename") != null) {
			templatename = request.getParameter("templatename").toString();
		}
		if (!"".equals(request.getParameter("templatestatus")) && request.getParameter("templatestatus") != null) {
			templatestatus = request.getParameter("templatestatus").toString();
		}
		// 封装模板变量数据
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setTmpkey(templatename);
		templateVariables.setTmpstatus(Integer.parseInt(templatestatus));
		ModelAndView mv = new ModelAndView();
		List<TemplateVariables> templateVariablesList = templateVariablesService.getTemplateList(templateVariables);
		System.out.println("templateVariablesList.size()" + templateVariablesList.size());
		mv.addObject("templateVariablesList", templateVariablesList);
		mv.setViewName("/message/tplVar");
		return mv;
	}

	@RequestMapping("/addtemplate")
	public void addtemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// 获取页面传入的数据
		String tmpkey = request.getParameter("tmpkey");
		String tmpname = request.getParameter("tmpname");
		String tmpstatus = request.getParameter("tmpstatus");
		// 封装对象数据
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setTmpkey(tmpkey);
		templateVariables.setTmpname(tmpname);
		templateVariables.setTmpstatus(Integer.parseInt(tmpstatus));
		templateVariables.setDelstatus(0);
		int i = templateVariablesService.addTemplateVariables(templateVariables);
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

	@RequestMapping("/deltemplate")
	public void deltemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		//获取传入数据
		String tmpid = request.getParameter("tmpid");
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setId(Integer.parseInt(tmpid));
		templateVariables.setDelstatus(1);
		int i = templateVariablesService.delTemplateVariables(templateVariables);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("success", "删除成功!");
		} else {
			map.put("fail", "删除失败!");
		}
		String jsonResult = JSON.toJSONString(map);
		System.out.println("=============jsonResult=" + jsonResult);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
		out.close();
	}

	@RequestMapping("/updatetemplate")
	public void updatetemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// 获取页面传入数据
		String tmpid = request.getParameter("tmpid");
		String tmpkey = request.getParameter("tmpkey");
		String tmpname = request.getParameter("tmpname");
		String tmpstatus = request.getParameter("tmpstatus");
		// 封装数据对象
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setId(Integer.parseInt(tmpid));
		templateVariables.setTmpkey(tmpkey);
		templateVariables.setTmpname(tmpname);
		templateVariables.setTmpstatus(Integer.parseInt(tmpstatus));
		int i = templateVariablesService.updateStatusTemplateVariables(templateVariables);
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
