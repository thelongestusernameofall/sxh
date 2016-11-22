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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sxh.common.UrlUtil;
import com.sxh.model.Activities;
import com.sxh.model.Attach;
import com.sxh.model.UserInfo;
import com.sxh.model.UserTag;
import com.sxh.service.ActivityService;
import com.sxh.service.AttachService;
import com.sxh.service.ShareActivitiesService;
import com.sxh.service.SuggestionService;
import com.sxh.service.UserCollectService;
import com.sxh.service.UserCommentService;
import com.sxh.service.UserEnterService;
import com.sxh.service.UserLikeService;
import com.sxh.service.UserService;
import com.sxh.service.UserTagService;

/**
 * 
 * 会员控制层
 *
 */
@Controller
@RequestMapping("/member")
public class memberController {
	// 每天条数
	public static final int PAGE_COUNT = 20;

	// 获取当前时间
	Calendar c = Calendar.getInstance();
	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	String time = f.format(c.getTime());
	SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = f1.format(c.getTime());
	SimpleDateFormat bh = new SimpleDateFormat("yyyyMMdd");
	String bianhao = bh.format(c.getTime());
	SimpleDateFormat f2 = new SimpleDateFormat("yyyyMMddHHmmSS");
	String time1 = f2.format(c.getTime());
	// 获取当前路径
	UrlUtil uu = new UrlUtil();
	String ul = uu.getUrl();
	String ht = ul + "/tupian/";
	@Autowired
	private UserService userService;
	@Autowired
	private SuggestionService suggestionService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserCollectService userCollectService;
	@Autowired
	private UserEnterService userEnterService;
	@Autowired
	private AttachService attachService;
	@Autowired
	private ShareActivitiesService shareActivitiesService;
	@Autowired
	private UserCommentService userCommentService;
	@Autowired
	private UserLikeService userLikeService;
	@Autowired
	private UserTagService userTagService;

	@RequestMapping("/showMemberList")
	public ModelAndView showMemberList(HttpServletRequest request) {
		// 获取输入的查询条件
		String nickname = "";// 用户昵称
		String phone = "";// 用户手机号
		String sex = "2";// 用户性别
		String status = "2";// 用户状态
		if (!"".equals(request.getParameter("nickname")) && request.getParameter("nickname") != null) {
			nickname = request.getParameter("nickname").toString();
		}
		if (!"".equals(request.getParameter("phone")) && request.getParameter("phone") != null) {
			phone = request.getParameter("phone").toString();
		}
		if (!"".equals(request.getParameter("sex")) && request.getParameter("sex") != null) {
			sex = request.getParameter("sex").toString();
		}
		if (!"".equals(request.getParameter("status")) && request.getParameter("status") != null) {
			status = request.getParameter("status").toString();
		}
		System.out.println("=================nickname=" + nickname);
		System.out.println("=================phone=" + phone);
		System.out.println("=================sex=" + sex);
		System.out.println("=================status=" + status);
		UserInfo userInfo = new UserInfo();
		userInfo.setNickname("%" + nickname + "%");
		userInfo.setPhone(phone);
		userInfo.setSex(Integer.parseInt(sex));
		userInfo.setPtatus(Integer.parseInt(status));
		ModelAndView mv = new ModelAndView();
		List<UserInfo> userInfos = userService.selectUsers(userInfo);
		System.out.println(userInfos.size());
		mv.addObject("memberList", userInfos);
		// mv.addObject("page", page);
		mv.setViewName("/memberList");
		return mv;
	}

	@RequestMapping("/getMemberDetail")
	public ModelAndView getMemberDetail(HttpServletRequest request) {
		// 获取输入的查询条件
		String userid = "";// 用户昵称
		if (!"".equals(request.getParameter("userid")) && request.getParameter("userid") != null) {
			userid = request.getParameter("userid").toString();
		}
		System.out.println("=================userid=" + userid);
		UserInfo userInfo = new UserInfo();

		ModelAndView mv = new ModelAndView();
		userInfo = userService.getUserById(Integer.parseInt(userid));
		// 根据userid获取参加的三个活动
		HashMap<String, Integer> maps = new HashMap<String, Integer>();
		maps.put("userid", Integer.parseInt(userid));
		maps.put("startItem", 0);
		maps.put("endItem", 3);
		// 查询所有活动列表
		List<Activities> activityList = activityService.getEnterActivitiesListByUserid(maps);
		System.out.println(userInfo.toString());
		System.out.println("activityList.size()="+activityList.size());
		//查询用户标签
		String tagsname="";
		if (!"".equals(userInfo.getTags()) && userInfo.getTags() != null) {
			String tags[] = userInfo.getTags().split(",");
			if (tags.length > 0) {
				for (int i = 0; i < tags.length; i++) {
					UserTag userTag = userTagService.selectUserTagById(Integer.parseInt(tags[i]));
					tagsname += userTag.getTagname() + " ";
				}
			}

		}
		//获取用户头像
	
		if (!"".equals(userInfo.getPhoto()) && userInfo.getPhoto() != null) {
			int photo = userInfo.getPhoto();
			Attach attach = attachService.selectAttachById(photo);
			String savepath = attach.getSavepath();
			String savefile = attach.getSavefile();
			String sourcefile = attach.getSourcefile();
			String filesize = attach.getFilesize();
			String filetype = attach.getFiletype();
			String a[] = savepath.split("tupian/");
			mv.addObject("purl", ht + a[1] + "/" + savefile);
		}
		userInfo.setTags(tagsname);
		mv.addObject("userInfo", userInfo);
		mv.addObject("activityList", activityList);
		
		// mv.addObject("page", page);
		mv.setViewName("/memberDetail");
		return mv;
	}

	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String userid = request.getParameter("userid");
		String status = request.getParameter("status");
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(Integer.parseInt(userid));
		userInfo.setPtatus(Integer.parseInt(status));
		int i = userService.UpdateStatusUser(userInfo);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			if ("1".equals(status)) {
				map.put("success", "启用成功!");
			}
			if ("0".equals(status)) {
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

}
