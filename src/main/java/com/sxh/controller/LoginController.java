package com.sxh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sxh.common.Md5Util;
import com.sxh.model.UserInfo;
import com.sxh.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");

		// 获取输入的查询条件
		String username = "";
		String password = "";
		if (!"".equals(request.getParameter("username")) && request.getParameter("username") != null) {
			username = request.getParameter("username").toString();
		}
		if (!"".equals(request.getParameter("password")) && request.getParameter("password") != null) {
			password = request.getParameter("password").toString();
		}
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		UserInfo userInfo = new UserInfo();
		userInfo.setPwd(Md5Util.md5zh(password));
		if (isphonenum(username) == true) {
			userInfo.setPhone(username);
			userInfoList = userService.SelectByNameAndPwd(userInfo);
		} else {
			userInfo.setUsercode(username);
			userInfoList = userService.selectByUserCodeAndPwd(userInfo);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		if (userInfoList.size() > 0) {
			UserInfo userInfo2 = userInfoList.get(0);

			if (userInfo2.getPtatus() == 0) {
				map.put("success", "该用户被禁用!");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("userrole", userInfo2.getRoleid());
				session.setAttribute("nickname", userInfo2.getNickname());
				session.setAttribute("userid", userInfo2.getUserid());
				map.put("success", "登录成功!");
			}
		} else {
			map.put("success", "用户名密码错误!");
		}
		String jsonResult = JSON.toJSONString(map);
		System.out.println("=============jsonResult=" + jsonResult);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
		out.close();
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index");
		return mv;

	}

	/**
	 * 判断用户传入的是否为手机号
	 * 
	 * @param phonenum
	 * @return
	 */
	public static boolean isphonenum(String phonenum) {
		String value = phonenum;
		String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();// boolean
	}
}
