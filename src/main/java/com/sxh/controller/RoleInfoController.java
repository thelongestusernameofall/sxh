package com.sxh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sxh.common.Md5Util;
import com.sxh.model.RoleInfo;
import com.sxh.model.UserInfo;
import com.sxh.service.RoleInfoService;
import com.sxh.service.UserService;
import com.sxh.vo.UserAuthVo;

@Controller
@RequestMapping("/roleinfo")
public class RoleInfoController {
	Calendar c = Calendar.getInstance();
	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	String time = f.format(c.getTime());
	SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = f1.format(c.getTime());
	SimpleDateFormat bh = new SimpleDateFormat("yyyyMMdd");
	String bianhao = bh.format(c.getTime());
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private UserService userService;

	@RequestMapping("/showRoleInfo")
	public ModelAndView showRoleInfo(HttpServletRequest request) {
		// 获取输入的查询条件
		String rolename = "";
		String status = "2";
		if (!"".equals(request.getParameter("rolename")) && request.getParameter("rolename") != null) {
			rolename = "%" + request.getParameter("rolename").toString() + "%";
		}
		if (!"".equals(request.getParameter("status")) && request.getParameter("status") != null) {
			status = request.getParameter("status").toString();
		}
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setRolename(rolename);
		roleInfo.setStatus(Integer.parseInt(status));

		ModelAndView mv = new ModelAndView();
		List<RoleInfo> roleInfoList = roleInfoService.getRoleInfoList(roleInfo);
		System.out.println(roleInfoList.size());
		mv.addObject("roleInfoList", roleInfoList);
		// mv.addObject("page", page);
		mv.setViewName("/auth/role");
		return mv;
	}

	@RequestMapping("/showUserAuth")
	public ModelAndView showUserAuth(HttpServletRequest request) {
		// 获取输入的查询条件
		String rolename = "";
		String nickname = "";
		String status = "2";
		if (!"".equals(request.getParameter("rolename")) && request.getParameter("rolename") != null) {
			rolename = "%" + request.getParameter("rolename").toString() + "%";
		}
		if (!"".equals(request.getParameter("nickname")) && request.getParameter("nickname") != null) {
			nickname = "%" + request.getParameter("nickname").toString() + "%";
		}
		if (!"".equals(request.getParameter("status")) && request.getParameter("status") != null) {
			status = request.getParameter("status").toString();
		}
		// 封装对象
		UserAuthVo userAuthVo = new UserAuthVo();
		userAuthVo.setRolename(rolename);
		userAuthVo.setNickname(nickname);
		userAuthVo.setStatus(Integer.parseInt(status));
		ModelAndView mv = new ModelAndView();
		// 查询数据
		List<UserAuthVo> userAuthVoList = roleInfoService.getUserAuthVoList(userAuthVo);
		System.out.println(userAuthVoList.size());
		mv.addObject("userAuthVoList", userAuthVoList);
		mv.setViewName("/auth/auth");
		return mv;
	}

	@RequestMapping("/addUserAuth")
	public void addUserAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String nickname = request.getParameter("nickname");
		String userstatus = request.getParameter("userstatus");
		String userrole = request.getParameter("userrole");
		String password = request.getParameter("password");
		System.out.println("========usercode=" + nickname);
		System.out.println("========userstatus=" + userstatus);
		System.out.println("========userrole=" + userrole);
		System.out.println("========password=" + password);
		UserInfo userInfo = new UserInfo();
		userInfo.setPwd(Md5Util.md5zh(password));
		userInfo.setNickname(nickname);
		userInfo.setRoleid(Integer.parseInt(userrole));
		userInfo.setUsercode(nickname);
		// 设置UID
		String date = new StringBuilder("%").append(time).append("%").toString();
		userInfo.setRegtime(date);
		List<UserInfo> userList = userService.getCountUserToday(userInfo);
		System.out.println("==============" + userList.size());
		String number = "";
		number = String.valueOf(userList.size());
		number = (Integer.parseInt(number) + 1) + "";
		if (Integer.parseInt(number) < 10) {
			number = "000" + number;
		} else if (Integer.parseInt(number) >= 10 && Integer.parseInt(number) < 100) {
			number = "00" + number;
		} else if (Integer.parseInt(number) >= 100 && Integer.parseInt(number) < 1000) {
			number = "0" + number;
		}
		// 普通用户注册
		userInfo.setUid(bianhao + number);
		userInfo.setRegtime(now);
		userInfo.setPtatus(Integer.parseInt(userstatus));
		// 插入用户信息表
		int i = userService.insert(userInfo);

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

	//
	// @RequestMapping("/deltags")
	// public void delUserTags(HttpServletRequest request, HttpServletResponse
	// response) throws IOException {
	// // 设置页面不缓存
	// response.setContentType("application/json");
	// response.setHeader("Pragma", "No-cache");
	// response.setHeader("Cache-Control", "no-cache");
	// response.setCharacterEncoding("UTF-8");
	// String tagid = request.getParameter("tagid");
	// String tagName = request.getParameter("tagname");
	// String tagStatus = request.getParameter("tagstatus");
	// UserTag usertag = new UserTag();
	// usertag.setTagid(Integer.parseInt(tagid));
	// usertag.setTagname(tagName);
	// usertag.setStatus(Integer.parseInt(tagStatus));
	// int i = userTagService.UpdateStatusUserTag(usertag);
	// HashMap<String, String> map = new HashMap<String, String>();
	// if (i > 0) {
	// if ("1".equals(tagStatus)) {
	// map.put("success", "启用成功!");
	// }
	// if ("0".equals(tagStatus)) {
	// map.put("success", "禁用成功!");
	// }
	// } else {
	// map.put("fail", "失败!");
	// }
	// String jsonResult = JSON.toJSONString(map);
	// System.out.println("=============jsonResult=" + jsonResult);
	// PrintWriter out = response.getWriter();
	// out.print(jsonResult);
	// out.flush();
	// out.close();
	// }
	//
	@RequestMapping("/UpdateUserAuth")
	public void UpdateUserAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// 获取用户标签的id,名称,状态
		String usercode = request.getParameter("nickname");
		String userstatus = request.getParameter("userstatus");
		String userrole = request.getParameter("userrole");
		String password = request.getParameter("password");
		String userid = request.getParameter("userid");
		System.out.println("========usercode=" + usercode);
		System.out.println("========userstatus=" + userstatus);
		System.out.println("========userrole=" + userrole);
		System.out.println("========password=" + password);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(Integer.parseInt(userid));
		userInfo.setPwd(Md5Util.md5zh(password));
		userInfo.setUsercode(usercode);
		userInfo.setRoleid(Integer.parseInt(userrole));
		userInfo.setRegtime(now);
		userInfo.setPtatus(Integer.parseInt(userstatus));
		int i = userService.UpdateUserInfo(userInfo);
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
