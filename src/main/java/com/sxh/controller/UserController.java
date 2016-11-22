package com.sxh.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxh.common.DateUtil;
import com.sxh.common.Md5Util;
import com.sxh.common.SMSToAndroid;
import com.sxh.common.SMSToIOS;
import com.sxh.common.ThumbnailAWTService2;
import com.sxh.common.UrlUtil;
import com.sxh.common.Week;
import com.sxh.model.Activities;
import com.sxh.model.Appversion;
import com.sxh.model.Attach;
import com.sxh.model.Informagainst;
import com.sxh.model.Message;
import com.sxh.model.ShareActivities;
import com.sxh.model.Shield;
import com.sxh.model.Suggestions;
import com.sxh.model.UserCollect;
import com.sxh.model.UserComment;
import com.sxh.model.UserEnter;
import com.sxh.model.UserInfo;
import com.sxh.model.UserLike;
import com.sxh.model.UserTag;
import com.sxh.service.ActivityService;
import com.sxh.service.AppVersionService;
import com.sxh.service.AttachService;
import com.sxh.service.InformagainstService;
import com.sxh.service.MessageService;
import com.sxh.service.ShareActivitiesService;
import com.sxh.service.ShieldService;
import com.sxh.service.SuggestionService;
import com.sxh.service.UserCollectService;
import com.sxh.service.UserCommentService;
import com.sxh.service.UserEnterService;
import com.sxh.service.UserLikeService;
import com.sxh.service.UserService;
import com.sxh.service.UserTagService;
import com.sxh.vo.ActivityVo;
import com.sxh.vo.AppActivityVo;
import com.sxh.vo.AppHobbyDetailVo;
import com.sxh.vo.AppHobbyListVo;
import com.sxh.vo.AppHobbyVo;
import com.sxh.vo.AppShareActivityVo;
import com.sxh.vo.AppUserTagsSign;
import com.sxh.vo.CircleVo;
import com.sxh.vo.CommentVo;
import com.sxh.vo.LikeVo;
import com.sxh.vo.PictureVo;

@Controller
@RequestMapping("/user")
public class UserController {
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
	@Autowired
	private AppVersionService appVersionService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ShieldService shieldService;
	@Autowired
	private InformagainstService informagainstService;

	@RequestMapping("/showInfos")
	public @ResponseBody Object showUserInfos() {
		List<UserInfo> userInfos = userService.getUsers();
		// LOGGER.info(JSON.toJSON(userInfos));
		return JSON.toJSON(userInfos);
	}

	// app登录接口
	@RequestMapping(value = "/loginJSONString")
	public void getJSONString(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 封装UserInfo对象
			UserInfo userInfo = new UserInfo();
			userInfo.setPhone(object.get("username").toString());
			userInfo.setPwd(Md5Util.md5zh(object.get("password").toString()));

			// 查询账号和密码是否正确
			List<UserInfo> userList = userService.SelectByNameAndPwd(userInfo);
			System.out.println("userList.size()=" + userList.size());
			// 如果List长度大于0，说明存在该用户
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (userList.size() > 0) {
				m.put("Scd", "1");
				m.put("Msg", "登录成功!");
				m.put("UserId", userList.get(0).getUserid().toString());
				if (!"".equals(userList.get(0).getNickname()) && userList.get(0).getNickname() != null) {
					m.put("Na", userList.get(0).getNickname());
				}
				UserInfo userInfo2 = new UserInfo();
				userInfo2.setUserid(userList.get(0).getUserid());
				userInfo2.setUsertype(Integer.parseInt(object.get("type").toString()));
				userInfo2.setCid(object.get("cid").toString());
				userService.UpdateUserInfo(userInfo2);

				// 查询用户头像
				if (!"".equals(userList.get(0).getPhoto()) && userList.get(0).getPhoto() != null) {
					int photo = userList.get(0).getPhoto();
					Attach attach = attachService.selectAttachById(photo);
					String savepath = attach.getSavepath();
					String savefile = attach.getSavefile();
					String sourcefile = attach.getSourcefile();
					String filesize = attach.getFilesize();
					String filetype = attach.getFiletype();
					String a[] = savepath.split("tupian/");
					m.put("Ph", ht + a[1] + "/" + savefile);
				} else {
					m.put("Ph", "");
				}
				// 用户标签
				if (!"".equals(userList.get(0).getTags()) && userList.get(0).getTags() != null) {
					m.put("Tag", userList.get(0).getTags());
				} else {
					m.put("Tag", "");
				}
				// 用户性别
				if (!"".equals(userList.get(0).getSex()) && userList.get(0).getSex() != null) {
					m.put("Sex", userList.get(0).getSex().toString());
				} else {
					m.put("Sex", "");
				}
				System.out.println("登录成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "用户不存在或密码错误!");
				System.out.println("用户不存在或密码错误!");
			}
			Object json = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", json);
			Object jsonb = JSONObject.toJSON(map);
			result = jsonb.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	// app注册接口
	@RequestMapping(value = "/appRegister")
	public void appRegister(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String number = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 封装UserInfo对象
			UserInfo userInfo = new UserInfo();
			userInfo.setPhone(object.get("Mb").toString());
			userInfo.setPwd(Md5Util.md5zh(object.get("Pwd").toString()));
			userInfo.setNickname(object.get("Na").toString());
			userInfo.setRoleid(3);
			userInfo.setUsercode(object.get("Mb").toString());

			// 设置UID
			String date = new StringBuilder("%").append(time).append("%").toString();
			userInfo.setRegtime(date);
			List<UserInfo> userList = userService.getCountUserToday(userInfo);
			System.out.println("==============" + userList.size());
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
			// 插入用户信息表
			int id = userService.insert(userInfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (id > 0) {
				m.put("Scd", "1");
				m.put("Msg", "注册成功!");
				System.out.println("注册成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "注册失败!");
				System.out.println("注册失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端获取账号管理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetUser")
	public void appGetUser(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			// 获取用户的信息
			UserInfo userinfos = userService.getUserById(userid);
			// 封装查询好的数据
			HashMap<String, String> m = new HashMap<String, String>();
			// 查询用户头像
			if (!"".equals(userinfos.getPhoto()) && userinfos.getPhoto() != null) {
				int photo = userinfos.getPhoto();
				Attach attach = attachService.selectAttachById(photo);
				String savepath = attach.getSavepath();
				String savefile = attach.getSavefile();
				String sourcefile = attach.getSourcefile();
				String filesize = attach.getFilesize();
				String filetype = attach.getFiletype();
				String a[] = savepath.split("tupian/");
				m.put("Ph", ht + a[1] + "/" + savefile);
			} else {
				m.put("Ph", "");
			}
			m.put("Na", userinfos.getNickname().toString());
			if (!"".equals(userinfos.getSex()) && userinfos.getSex() != null) {
				if (userinfos.getSex() == 0) {
					m.put("Se", "女");
				} else if (userinfos.getSex() == 1) {
					m.put("Se", "男");
				} else {
					m.put("Se", "");
				}
			} else {
				m.put("Se", "男");
			}
			if (!"".equals(userinfos.getPhone()) && userinfos.getPhone() != null) {
				m.put("Phn", userinfos.getPhone());
			} else {
				m.put("Phn", "");
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端修改昵称
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appUpdateNickName")
	public void appUpdateNickName(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			String nickname = object.getString("nickname").toString();
			// 封装要修改的对象
			UserInfo userinfo = new UserInfo();
			userinfo.setUserid(userid);
			userinfo.setNickname(nickname);
			int i = userService.UpdateUserInfo(userinfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "修改成功!");
				System.out.println("修改成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "修改失败!");
				System.out.println("修改失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端修改性别
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appUpdateSex")
	public void appUpdateSex(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			String sex = object.getString("sex").toString();
			// 封装要修改的对象
			UserInfo userinfo = new UserInfo();
			userinfo.setUserid(userid);
			userinfo.setSex(Integer.parseInt(sex));
			int i = userService.UpdateUserInfo(userinfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "修改成功!");
				System.out.println("修改成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "修改失败!");
				System.out.println("修改失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端修改新密码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appUpdateNpwd")
	public void appUpdateNpwd(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			String phone = object.get("phone").toString();
			String npwd = object.getString("npwd").toString();
			// 封装要修改的对象
			UserInfo userinfo = new UserInfo();
			// userinfo.setUserid(userid);
			userinfo.setPhone(phone);
			userinfo.setPwd(Md5Util.md5zh(npwd));
			int i = userService.updatePwdByPhone(userinfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "修改成功!");
				System.out.println("修改成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "修改失败!");
				System.out.println("修改失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app密码找回的验证
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appYzUpdatePwd")
	public void appYzUpdatePwd(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			String phn = object.get("phn").toString();
			String yzm = object.getString("yzm").toString();
			// 封装要修改的对象
			UserInfo userinfo = new UserInfo();
			userinfo.setPhone(phn);
			List<UserInfo> userlist = userService.getUserIdByPhone(userinfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (userlist.size() > 0) {
				m.put("Scd", "1");
				m.put("Msg", "验证成功!");
				m.put("Userid", userlist.get(0).getUserid().toString());
				System.out.println("验证成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "验证失败!");
				System.out.println("验证失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app修改手机号的验证
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appYzUpdatePhone")
	public void appYzUpdatePhone(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			String phn = object.get("phn").toString();
			String yzm = object.getString("yzm").toString();
			// 封装要修改的对象
			// UserInfo userinfo = new UserInfo();
			// userinfo.setPhone(phn);
			// List<UserInfo> userlist = userService.getUserIdByPhone(userinfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// if (userlist.size() > 0) {
			m.put("Scd", "1");
			m.put("Msg", "验证成功!");
			System.out.println("验证成功!");
			// } else {
			// m.put("Scd", "0");
			// m.put("Msg", "验证失败!");
			// System.out.println("验证失败!");
			// }
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端修改手机号
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appUpdatePhone")
	public void appUpdatePhone(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			String phn = object.getString("phn").toString();
			// 封装要修改的对象
			UserInfo userinfo = new UserInfo();
			userinfo.setUserid(userid);
			userinfo.setPhone(phn);
			int i = userService.UpdateUserInfo(userinfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "修改成功!");
				System.out.println("修改成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "修改失败!");
				System.out.println("修改失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	// app添加反馈意见接口
	@RequestMapping(value = "/appSuggestion")
	public void appSuggestion(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			JSONObject object = JSONObject.parseObject(str);
			// 封装意见反馈对象
			Suggestions suggestions = new Suggestions();
			suggestions.setSugcontent(object.get("sc").toString());
			suggestions.setPhonemodel(object.get("pm").toString());
			suggestions.setVersion(object.get("ver").toString());
			suggestions.setSugtime(object.get("st").toString());
			suggestions.setContract(object.get("phn").toString());
			suggestions.setReplystatus(0);
			// 插入反馈信息表
			int id = suggestionService.addSuggestions(suggestions);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (id > 0) {
				m.put("Scd", "1");
				m.put("Msg", "吐槽成功!");
				System.out.println("吐槽成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "吐槽失败!");
				System.out.println("吐槽失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取活动列表信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetActivityList")
	public void appGetActivityList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			int userid = 0;
			if (!"".equals(object.get("userid")) && object.get("userid") != null) {
				userid = Integer.parseInt(object.get("userid").toString());
			}

			String p = object.get("p").toString();
			int pagenow = 0;
			System.out.println("获取传入的页码==" + p);
			if (!("").equals(p)) {
				pagenow = Integer.valueOf(p);
			}
			int startItem = 0;
			int endItem = 0;
			if (!"".equals(pagenow) && pagenow != 0) {
				startItem = (pagenow - 1) * PAGE_COUNT + startItem;
				endItem = pagenow * PAGE_COUNT;
			} else {
				endItem = PAGE_COUNT;
			}
			System.out.println("-----------------" + startItem + "====================" + endItem);
			HashMap<String, Integer> maps = new HashMap<String, Integer>();
			maps.put("startItem", startItem);
			maps.put("endItem", endItem);
			if (!"".equals(object.get("activityCost")) && object.get("activityCost") != null) {
				// 根据条件做判断
				// 0.就是查询全部；不添加条件
				// 1.代表是非周末;
				// 2.代表周六;
				// 3.代表周末;
				// 4.代表免费;
				// 5.代表收费;
				// 6.代表未报满
				System.out.println("===========================activitycost=" + object.get("activityCost").toString());
				maps.put("activitycost", Integer.parseInt(object.get("activityCost").toString()));
			} else {
				maps.put("activitycost", 0);
			}
			List<AppActivityVo> appActivityVos = new ArrayList<AppActivityVo>();
			// 查询所有活动列表
			List<Activities> activityList = activityService.getActivitiesList(maps);
			if (activityList.size() > 0) {
				for (int i = 0; i < activityList.size(); i++) {
					Activities activities = activityList.get(i);
					AppActivityVo appActivityVo = new AppActivityVo();
					int activityid = activityList.get(i).getActivityid();
					appActivityVo.setId(activityid);
					appActivityVo.setActivityname(activities.getActivityname());
					// 查询收藏该活动的人数
					List<UserCollect> userCollects = userCollectService.selectByActivityid(activityid);
					appActivityVo.setCount(String.valueOf(userCollects.size()));// 收藏该活动的人数
					int ifmy = activities.getIfmy();
					if (ifmy == 0) {
						appActivityVo.setIfmy("1");// 是否满员0,满员;1,未满员
					} else {
						appActivityVo.setIfmy("0");// 是否满员0,满员;1,未满员
					}
					UserCollect userCollect = new UserCollect();
					userCollect.setUserid(userid);
					userCollect.setActivityid(activityid);
					List<UserCollect> usercollectList = userCollectService.selectByUseridAndActivityid(userCollect);
					if (usercollectList.size() > 0) {
						appActivityVo.setIfsc("0");// 已收藏
					} else {
						appActivityVo.setIfsc("1");// 未收藏
					}
					if (activities.getActivitycost().equals("0")) {
						appActivityVo.setIffree("0");// 是免费
					} else {
						appActivityVo.setIffree("1");// 不是免费
					}
					String date = activities.getActivitystart();
					Week week = DateUtil.getWeek(date);
					if (week.getChineseName().equals("星期六")) {
						appActivityVo.setIfweek("0");// 是周末
					} else if (week.getChineseName().equals("星期日")) {
						appActivityVo.setIfweek("0");// 是周末
					} else {
						appActivityVo.setIfweek("1");// 不是周末
					}
					if (!"".equals(activities.getActivitypic()) && activities.getActivitypic() != null) {
						int photo = activities.getActivitypic();
						Attach attach = attachService.selectAttachById(photo);
						String savepath = attach.getSavepath();
						String savefile = attach.getSavefile();
						String sourcefile = attach.getSourcefile();
						String filesize = attach.getFilesize();
						String filetype = attach.getFiletype();
						String a[] = savepath.split("tupian/");
						appActivityVo.setPurl(ht + a[1] + "/" + savefile);
					} else {
						appActivityVo.setPurl("");
					}
					appActivityVo.setActivityurl(ul + "/h5/example.jsp?id=" + activityid + "&userid=");
					appActivityVos.add(appActivityVo);
				}
			}
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonArray = JSONArray.toJSON(appActivityVos);
			m.put("Lst", jsonArray);
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端活动列表添加收藏
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appAddCollect")
	public void appAddCollect(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int activityid = Integer.parseInt(object.get("id").toString());
			// 封装要修改的对象
			UserCollect userCollect = new UserCollect();
			userCollect.setUserid(userid);
			userCollect.setActivityid(activityid);
			int i = userCollectService.addCollect(userCollect);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "收藏成功!");
				System.out.println("收藏成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "收藏失败!");
				System.out.println("收藏失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端活动列表取消收藏
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appDelCollect")
	public void appDelCollect(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int activityid = Integer.parseInt(object.get("id").toString());
			// 封装要修改的对象
			UserCollect userCollect = new UserCollect();
			userCollect.setUserid(userid);
			userCollect.setActivityid(activityid);
			int i = userCollectService.deleteByActivityidAndUserid(userCollect);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "取消收藏成功!");
				System.out.println("取消收藏成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "取消收藏失败!");
				System.out.println("取消收藏失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取活动详情
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetActivityDetail")
	public void appGetActivityDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int activityid = Integer.parseInt(object.get("id").toString());
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			UserEnter userEnter = new UserEnter();
			userEnter.setUserid(userid);
			userEnter.setActivityid(activityid);
			// 根据活动id查询该活动是否已经满员
			Activities activities = activityService.getActivityById(activityid);
			String s1 = now;// 当前时间
			String s2 = activities.getActivityend();// 活动结束时间
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
			int result1 = c1.compareTo(c2);
			if (result1 > 0) {
				m.put("Scd", "2");
				m.put("Msg", "活动已经过期，无法再报名或者取消报名！");
				System.out.println("活动已经过期，无法再报名或者取消报名！");
			} else {
				if (activities.getIfmy() == 1) {
					// 说明已经人员已满
					m.put("Scd", "1");
					m.put("Msg", "已经报名,或者人员已满！");
					System.out.println("已经报名,或者人员已满！");

				} else {
					List<UserEnter> userEnters = new ArrayList<UserEnter>();
					userEnters = userEnterService.selectByUseridAndActivityid(userEnter);
					// 如果List长度大于0，说明已经报名了。否则没有报名
					if (userEnters.size() > 0) {
						m.put("Scd", "1");
						m.put("Msg", "已经报名,或者人员已满！");
						System.out.println("已经报名,或者人员已满！");
					} else {
						m.put("Scd", "0");
						m.put("Msg", "没有报名!");
						System.out.println("没有报名!");
					}
				}
			}

			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端活动活动详情添加报名
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appAddEnter")
	public void appAddEnter(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int activityid = Integer.parseInt(object.get("id").toString());
			UserEnter userEnter = new UserEnter();
			userEnter.setUserid(userid);
			userEnter.setActivityid(activityid);
			// 根据活动id查询活动报名数，如果没有满员就继续报名，并且报名人数加1
			Activities activities = activityService.getActivityById(activityid);
			System.out.println("activities=" + activities.toString());
			int ifmy = activities.getIfmy();// 是否满员
			int entercount = activities.getEntercount();// 报名人数
			int peoplecount = activities.getPeoplecount();// 限制人数
			int i = userEnterService.addUserEnter(userEnter);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				// 报名人数加1
				entercount = entercount + 1;
				System.out.println("报名人数=" + entercount);
				// 报名人数和限制人数一样，就说明已经满员
				if (entercount == peoplecount) {
					ifmy = 1;
				}
				activities.setEntercount(entercount);
				activities.setIfmy(ifmy);
				int j = activityService.updateActivity(activities);
				System.out.println("j=" + j);
				if (j > 0) {
					m.put("Scd", "1");
					m.put("Msg", "报名成功!");
					System.out.println("报名成功!");
				}
			} else {
				m.put("Scd", "0");
				m.put("Msg", "报名失败!");
				System.out.println("报名失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端活动列表取消收藏
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appDelEnter")
	public void appDelEnter(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			int userid = Integer.parseInt(object.get("userid").toString());
			int activityid = Integer.parseInt(object.get("id").toString());
			UserEnter userEnter = new UserEnter();
			userEnter.setUserid(userid);
			userEnter.setActivityid(activityid);
			// 根据活动id查询活动
			Activities activities = activityService.getActivityById(activityid);
			int ifmy = activities.getIfmy();// 是否满员
			int entercount = activities.getEntercount();// 报名人数
			int peoplecount = activities.getPeoplecount();// 限制人数
			int i = userEnterService.deleteByActivityidAndUserid(userEnter);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				// 取消报名就要使报名人数-1
				entercount = entercount - 1;
				// 如果报名人数比限制人数少就修改满员状态
				if (entercount == peoplecount) {
					ifmy = 0;
				}
				activities.setEntercount(entercount);
				activities.setIfmy(ifmy);
				int j = activityService.updateActivity(activities);
				if (j > 0) {
					m.put("Scd", "1");
					m.put("Msg", "取消报名成功!");
					System.out.println("取消报名成功!");
				}
			} else {
				m.put("Scd", "0");
				m.put("Msg", "取消报名失败!");
				System.out.println("取消报名失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端晒活动上传图片和文字的方法
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/appShareActivity")
	public void appShareActivity(HttpServletRequest request, HttpServletResponse response) {

		PrintWriter out = null;
		String str;
		String result = null;
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 获取文件上传需要保存的路径，upload文件夹需存在。
			String path = request.getSession().getServletContext().getRealPath("/tupian/" + bianhao);
			// 设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
			System.out.println(path);
			File file = new File(path);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				System.out.println("//不存在");
				file.mkdir();
			} else {
				System.out.println("//目录存在");
			}
			factory.setRepository(file);
			// 设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
			factory.setSizeThreshold(1024 * 1024);
			// 上传处理工具类（高水平API上传处理？）
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			ArrayList<Attach> attachlist = new ArrayList<Attach>();
			// 调用 parseRequest（request）方法 获得上传文件 FileItem 的集合list 可实现多文件上传。
			List<FileItem> list;
			list = ((ServletFileUpload) upload).parseRequest(request);
			// list = (List<FileItem>) upload.parseRequest(request);
			System.out.println("list=" + list.size());
			ArrayList<String> idlist = new ArrayList<String>();
			// System.out.println("list="+list.get(0).toString());
			String userid = "";
			String t = "";
			String yzm = "";
			String type = "";
			String dian = ".";
			String sdz = bianhao + "/";
			String describe = "";
			for (FileItem item : list) {
				int i = 0;
				Attach attach = new Attach();
				// 获取表单属性名字。
				String name = item.getFieldName();
				System.out.println("name=" + name);
				// 如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
				if (item.isFormField()) {
					// 获取用户具体输入的字符串，
					str = new String(item.getString().getBytes("ISO8859_1"), "utf-8");
					System.out.println("===============" + str);
					// JSONObject jb = new JSONObject();
					// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
					JSONObject object = JSONObject.parseObject(str);
					userid = object.get("userid").toString();
					describe = URLDecoder.decode(object.get("describe").toString(), "UTF-8");
					t = object.get("t").toString();
					System.out.println("---------------userid" + userid);
					System.out.println("---------------describe" + describe);
					System.out.println("---------------t" + t);
					request.setAttribute(name, str);

				}
				// 如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
				else {
					// 获取路径名
					String value = item.getName();
					if (!"null".equals(value) && value != null) {
						System.out.println("123456" + value);
						// 取到最后一个反斜杠。
						int start = value.lastIndexOf("\\");
						// 截取上传文件的 字符串名字。+1是去掉反斜杠。
						String filename = value.substring(start + 1);
						System.out.println("name" + value);
						request.setAttribute(name, filename);
						String[] a = filename.split("\\.");
						type = a[a.length - 1];
						yzm = String.valueOf(Math.random()).substring(2, 5);
						/*
						 * 第三方提供的方法直接写到文件中。
						 */
						// 收到写到接收的文件中。
						// 上传相对路径
						String uploadPath = "/tupian/" + bianhao + "/";
						OutputStream outs = new FileOutputStream(new File(path, time1 + yzm + "." + type));
						System.out.println("原图地址=" + path.replace("\\", "/") + time1 + yzm + "." + type);
						// 创建原图
						item.write(new File(path, time1 + yzm + "." + type));
						// 创建缩略图
						ThumbnailAWTService2 thumbnailAWTService = new ThumbnailAWTService2();
						System.out.println("item.size=" + item.getSize());
						thumbnailAWTService.thumbnail(item, time1 + yzm + "_thum" + "." + type, uploadPath, path);
						// 封装ATTACH
						attach.setSourcefile(filename);
						attach.setFiletype(type);
						attach.setSavefile(time1 + yzm + "." + type);
						attach.setUptime(now);
						path = "/" + path.replaceAll("\\\\", "/");
						attach.setSavepath(path);
						attach.setUserid(userid);

						InputStream in = item.getInputStream();
						int length = 0;
						byte[] buf = new byte[1024];
						System.out.println("获取文件总量的容量:" + item.getSize());
						attach.setFilesize(String.valueOf(item.getSize()));
						int ID = attachService.insert(attach);
						System.out.println("pid = " + attach.getId());
						idlist.add(String.valueOf(attach.getId()));
						System.out.println("---------------------------------idlist.size()=" + idlist.size());
						i++;
						while ((length = in.read(buf)) != -1) {
							outs.write(buf, 0, length);
						}
						in.close();
						outs.close();
					}
				}
			}
			// 1代表类型是大头贴
			System.out.println("--------------------------------返回新头像地址Po=" + ht + sdz + time1 + yzm + dian + type);
			if (t.equals("1")) {
				String id = "";
				System.out.println("---------------------------------idlist1.size()=" + idlist.size());
				for (int i = 0; i < idlist.size(); i++) {
					id = idlist.get(i).toString();
				}
				System.out.println("---------------------------------userid.length()=" + userid.length());
				UserInfo userInfo = new UserInfo();
				userInfo.setPhoto(Integer.parseInt(id));
				userInfo.setUserid(Integer.parseInt(userid));
				int j = userService.UpdateUserPhoto(userInfo);
				System.out.println("=======j=" + j);
				if (j > 0) {
					result = "{\"Stu\":\"1\",\"Rst\":{\"Scd\":\"1\",\"Msg\":\"上传成功！\",\"Po\":\"" + ht + sdz + time1
							+ yzm + dian + type + "\"}}";
				} else {
					result = "{\"Stu\":\"1\",\"Rst\":{\"Scd\":\"0\",\"Msg\":\"上传失败！\"}}";
				}
			} // 2.代表晒活动
			if (t.equals("2")) {
				System.out.println("=================进入晒活动");
				int id1 = 0;
				/**
				 * 先插入一条分享数据
				 */
				ShareActivities shareActivity = new ShareActivities();
				shareActivity.setUserid(Integer.parseInt(userid));
				shareActivity.setDescribes(describe);
				shareActivity.setFbtime(now);
				System.out.println("=============shareActivities=" + shareActivity.toString());
				int shareid = shareActivitiesService.insertShareActivites(shareActivity);
				System.out.println("==================id=" + shareActivity.getId());
				// int shareid = 1;
				System.out.println("shareid=" + shareid);
				String ids = "";
				if (shareid > 0) {
					if (idlist.size() > 0) {
						for (int j = 0; j < idlist.size(); j++) {
							ids += idlist.get(j) + ",";
						}
					}
					ShareActivities shareActivities1 = new ShareActivities();
					shareActivities1.setId(shareActivity.getId());
					if (!"".equals(ids) && ids != null) {
						shareActivities1.setAttachs(ids.substring(0, ids.length() - 1));
					} else {
						shareActivities1.setAttachs("");
					}
					id1 = shareActivitiesService.UpdateAttachsById(shareActivities1);
					System.out.println("===============id1=" + id1);
				}
				if (id1 > 0) {
					result = "{\"Stu\":\"1\",\"Rst\":{\"Scd\":\"1\",\"Msg\":\"上传成功！\"}}";
				} else {
					result = "{\"Stu\":\"1\",\"Rst\":{\"Scd\":\"0\",\"Msg\":\"上传失败！\"}}";
				}
			}
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取圈子列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetShareActivitiesList")
	public void appGetShareActivitiesList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			// int userid = Integer.parseInt(object.get("userid").toString());
			String p = object.get("p").toString();
			String userid = object.get("userid").toString();
			int pagenow = 0;
			System.out.println("获取传入的页码==" + p);
			if (!("").equals(p)) {
				pagenow = Integer.valueOf(p);
			}
			int startItem = 0;
			int endItem = 0;
			if (!"".equals(pagenow) && pagenow != 0) {
				startItem = (pagenow - 1) * PAGE_COUNT + startItem;
				endItem = pagenow * PAGE_COUNT;
			} else {
				endItem = PAGE_COUNT;
			}
			System.out.println("-----------------" + startItem + "====================" + endItem);
			// String ids="";
			// String shieids="";

			// Shield shield = new Shield();
			// shield.setUserid(Integer.parseInt(userid));
			// List<Shield> shieldlist =
			// shieldService.selectShieldListById(shield);
			// if (shieldlist.size() > 0) {
			// for (int k = 0; k < shieldlist.size(); k++) {
			// ids += "'"+shieldlist.get(k).getShieid()+"',";
			// }
			// shieids = "("+ids.substring(0, ids.length()-1)+")";
			// }
			// System.out.println("shieids="+shieids);
			AppShareActivityVo appShareActivityVo = new AppShareActivityVo();
			appShareActivityVo.setStartitem(startItem);
			appShareActivityVo.setEnditem(endItem);
			appShareActivityVo.setUserid(Integer.parseInt(userid));

			// HashMap<String, Integer> maps = new HashMap<String, Integer>();
			// maps.put("startItem", startItem);
			// maps.put("endItem", endItem);
			List<CircleVo> circleVos = new ArrayList<CircleVo>();

			// 查询所有圈子列表
			List<ShareActivities> activityList = shareActivitiesService.getShareActivitiesList(appShareActivityVo);
			if (activityList.size() > 0) {
				for (int i = 0; i < activityList.size(); i++) {
					List<PictureVo> pictureVos = new ArrayList<PictureVo>();
					List<CommentVo> commentVos = new ArrayList<CommentVo>();
					List<LikeVo> likeVos = new ArrayList<LikeVo>();
					CircleVo circleVo = new CircleVo();
					ShareActivities shareActivities = new ShareActivities();
					shareActivities = activityList.get(i);
					// 获取圈子的id
					int shareActivitiesid = shareActivities.getId();
					// 查询当前用户是否对这个分享进行了点赞
					int islike = 0;
					UserLike userLike1 = new UserLike();
					userLike1.setShareid(shareActivitiesid);
					userLike1.setUserid(Integer.parseInt(userid));
					List<UserLike> userlikeList = userLikeService.getUserLikeListByUserLike(userLike1);
					if (userlikeList.size() > 0) {
						islike = 1;
					}
					circleVo.setIslike(islike);
					circleVo.setUserid(shareActivities.getUserid());
					circleVo.setId(shareActivitiesid);// 封装圈子ID
					circleVo.setTitle(shareActivities.getDescribes());// 封装圈子标题
					circleVo.setFabutime(shareActivities.getFbtime());// 封装圈子发布时间
					int userids = shareActivities.getUserid();
					System.out.println("============userids=" + userids);
					// 查询用户对象
					UserInfo userInfo = userService.getUserById(userids);
					if (!"".equals(userInfo.getNickname()) && userInfo.getNickname() != null) {
						circleVo.setNickname(userInfo.getNickname());// 封装昵称
					} else {
						circleVo.setNickname("");
					}
					// 查询用户的头像
					if (!"".equals(userInfo.getPhoto()) && userInfo.getPhoto() != null) {
						int photo = userInfo.getPhoto();
						// 查询头像地址
						Attach attach = attachService.selectAttachById(photo);
						String savepath = attach.getSavepath();
						String savefile = attach.getSavefile();
						String sourcefile = attach.getSourcefile();
						String filesize = attach.getFilesize();
						String filetype = attach.getFiletype();
						String a[] = savepath.split("tupian/");
						circleVo.setPurl(ht + a[1] + "/" + savefile);
					} else {
						circleVo.setPurl("");
					}

					// 查询图片列表
					String pids = shareActivities.getAttachs();
					if (!"".equals(pids) && pids != null) {
						String[] pidarry = pids.split(",");
						if (pidarry.length > 0) {
							for (int j = 0; j < pidarry.length; j++) {
								Attach attach2 = attachService.selectAttachById(Integer.parseInt(pidarry[j]));
								int attachid = attach2.getId();
								String savepath2 = attach2.getSavepath();
								String savefile2 = attach2.getSavefile();
								String sourcefile2 = attach2.getSourcefile();
								String filesize2 = attach2.getFilesize();
								String filetype2 = attach2.getFiletype();
								String a2[] = savepath2.split("tupian/");
								PictureVo pictureVo = new PictureVo();
								pictureVo.setId(attachid);
								pictureVo.setUrl(ht + a2[1] + "/" + savefile2);
								String[] pic = savefile2.split("." + filetype2);
								if (pic.length > 0) {
									String filename = pic[0] + "_thum";
									pictureVo.setThumurl(ht + a2[1] + "/" + filename + "." + filetype2);
								} else {
									pictureVo.setThumurl(ht + a2[1] + "/" + savefile2);
								}
								pictureVos.add(pictureVo);
							}
						}
					}

					/**
					 * 查询评论列表
					 */
					List<UserComment> userComments = userCommentService.getUserCommentListByShareId(shareActivitiesid);
					if (userComments.size() > 0) {
						for (int j = 0; j < userComments.size(); j++) {
							UserComment userComment = userComments.get(j);
							CommentVo commentVo = new CommentVo();
							commentVo.setId(userComment.getId());
							commentVo.setComment(userComment.getComment());
							commentVo.setUserid(String.valueOf(userComment.getUserid()));
							commentVo.setUsername(userService.getUserById(userComment.getUserid()).getNickname());
							commentVos.add(commentVo);
						}
					}
					/**
					 * 查询点赞列表
					 */
					List<UserLike> userLikes = userLikeService.getUserLikeListByShareId(shareActivitiesid);
					if (userLikes.size() > 0) {
						for (int j = 0; j < userLikes.size(); j++) {
							UserLike userLike = userLikes.get(j);
							LikeVo likeVo = new LikeVo();
							if (!"".equals(userLike.getUserid()) && userLike.getUserid() != null) {
								UserInfo userInfo2 = userService.getUserById(userLike.getUserid());
								likeVo.setId(userLike.getId());
								likeVo.setUserid(userInfo2.getUserid());
								likeVo.setUsername(userInfo2.getNickname());
							}
							likeVos.add(likeVo);
						}
					}
					// 封装评论条数
					circleVo.setComcount(commentVos.size());
					// 封装点赞条数
					circleVo.setZancount(likeVos.size());
					// 封装评论列表
					circleVo.setComList(commentVos);
					// 封装点赞列表
					circleVo.setLikeList(likeVos);
					// 封装图片列表
					circleVo.setPicList(pictureVos);

					circleVos.add(circleVo);
				}
			}
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonArray = JSONArray.toJSON(circleVos);
			m.put("Lst", jsonArray);
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端圈子列表点赞
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appAddUserLike")
	public void appAddUserLike(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int shareactivityid = Integer.parseInt(object.get("shareid").toString());
			UserLike userLike = new UserLike();
			userLike.setShareid(shareactivityid);
			userLike.setUserid(userid);
			int i = userLikeService.insert(userLike);
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "点赞成功!");
				// 查询新的点赞列表
				List<LikeVo> likeVos = new ArrayList<LikeVo>();
				List<UserLike> userLikes = userLikeService.getUserLikeListByShareId(shareactivityid);
				if (userLikes.size() > 0) {
					for (int j = 0; j < userLikes.size(); j++) {
						LikeVo likeVo = new LikeVo();
						UserInfo userInfo = userService.getUserById(userid);
						if (!"".equals(userInfo.getNickname()) && userInfo.getNickname() != null) {
							likeVo.setUsername(userInfo.getNickname());
							likeVo.setUserid(userInfo.getUserid());
						} else {
							likeVo.setUsername("");
							likeVo.setUserid(0);
						}
						likeVos.add(likeVo);
					}
				}
				m.put("likeLst", likeVos);
				System.out.println("点赞成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "点赞失败!");
				// 查询新的点赞列表
				List<LikeVo> likeVos = new ArrayList<LikeVo>();
				List<UserLike> userLikes = userLikeService.getUserLikeListByShareId(shareactivityid);
				if (userLikes.size() > 0) {
					for (int j = 0; j < userLikes.size(); j++) {
						LikeVo likeVo = new LikeVo();
						UserInfo userInfo = userService.getUserById(userid);
						if (!"".equals(userInfo.getNickname()) && userInfo.getNickname() != null) {
							likeVo.setUsername(userInfo.getNickname());
						} else {
							likeVo.setUsername("");
						}
						likeVos.add(likeVo);
					}
				}
				m.put("likeLst", likeVos);
				System.out.println("点赞失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端圈子列表取消点赞
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appDelUserLike")
	public void appDelUserLike(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int shareactivityid = Integer.parseInt(object.get("shareid").toString());
			UserLike userLike = new UserLike();
			userLike.setShareid(shareactivityid);
			userLike.setUserid(userid);
			int i = userLikeService.deleteByShareidAndUserid(userLike);
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "取消成功!");
				// 查询新的点赞列表
				List<LikeVo> likeVos = new ArrayList<LikeVo>();
				List<UserLike> userLikes = userLikeService.getUserLikeListByShareId(shareactivityid);
				if (userLikes.size() > 0) {
					for (int j = 0; j < userLikes.size(); j++) {
						LikeVo likeVo = new LikeVo();
						UserInfo userInfo = userService.getUserById(userid);
						if (!"".equals(userInfo.getNickname()) && userInfo.getNickname() != null) {
							likeVo.setUsername(userInfo.getNickname());
						} else {
							likeVo.setUsername("");
						}
						likeVos.add(likeVo);
					}
				}
				m.put("likeLst", likeVos);
				System.out.println("取消成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "取消失败!");
				// 查询新的点赞列表
				List<LikeVo> likeVos = new ArrayList<LikeVo>();
				List<UserLike> userLikes = userLikeService.getUserLikeListByShareId(shareactivityid);
				if (userLikes.size() > 0) {
					for (int j = 0; j < userLikes.size(); j++) {
						LikeVo likeVo = new LikeVo();
						UserInfo userInfo = userService.getUserById(userid);
						if (!"".equals(userInfo.getNickname()) && userInfo.getNickname() != null) {
							likeVo.setUsername(userInfo.getNickname());
						} else {
							likeVo.setUsername("");
						}
						likeVos.add(likeVo);
					}
				}
				m.put("likeLst", likeVos);
				System.out.println("取消失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端圈子列表添加评论
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appAddUserComment")
	public void appAddUserComment(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int shareactivityid = Integer.parseInt(object.get("shareid").toString());
			String comment = object.get("comment").toString();
			UserComment userComment = new UserComment();
			userComment.setUserid(userid);
			userComment.setShareid(shareactivityid);
			userComment.setComment(comment);
			int i = userCommentService.insert(userComment);
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "评论成功!");
				// 查询新的评论列表
				List<CommentVo> commentVos = new ArrayList<CommentVo>();
				List<UserComment> userComments = userCommentService.getUserCommentListByShareId(shareactivityid);
				if (userComments.size() > 0) {
					for (int j = 0; j < userComments.size(); j++) {
						CommentVo commentVo = new CommentVo();

						UserComment userComment2 = userComments.get(j);
						UserInfo userInfo = userService.getUserById(userComment2.getUserid());
						if (!"".equals(userInfo.getNickname()) && userInfo.getNickname() != null) {
							commentVo.setUsername(userInfo.getNickname());
						} else {
							commentVo.setUsername("");
						}
						commentVo.setUserid(String.valueOf(userComment2.getUserid()));
						commentVo.setComment(userComment2.getComment());
						commentVos.add(commentVo);
					}
				}
				m.put("comLst", commentVos);
				System.out.println("评论成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "评论失败!");
				// 查询新的评论列表
				List<CommentVo> commentVos = new ArrayList<CommentVo>();
				List<UserComment> userComments = userCommentService.getUserCommentListByShareId(shareactivityid);
				if (userComments.size() > 0) {
					for (int j = 0; j < userComments.size(); j++) {
						CommentVo commentVo = new CommentVo();
						UserInfo userInfo = userService.getUserById(userid);
						if (!"".equals(userInfo.getNickname()) && userInfo.getNickname() != null) {
							commentVo.setUsername(userInfo.getNickname());
						} else {
							commentVo.setUsername("");
						}
						commentVo.setUserid(String.valueOf(userid));
						commentVo.setComment(comment);
						commentVos.add(commentVo);
					}
				}
				m.put("comLst", commentVos);

			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取活动列表信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetMyActivityList")
	public void appGetMyActivityList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			String p = object.get("p").toString();
			int pagenow = 0;
			System.out.println("获取传入的页码==" + p);
			if (!("").equals(p)) {
				pagenow = Integer.valueOf(p);
			}
			int startItem = 0;
			int endItem = 0;
			if (!"".equals(pagenow) && pagenow != 0) {
				startItem = (pagenow - 1) * PAGE_COUNT + startItem;
				endItem = pagenow * PAGE_COUNT;
			} else {
				endItem = PAGE_COUNT;
			}
			System.out.println("-----------------" + startItem + "====================" + endItem);
			HashMap<String, Integer> maps = new HashMap<String, Integer>();
			maps.put("userid", userid);
			maps.put("startItem", startItem);
			maps.put("endItem", endItem);
			List<AppActivityVo> appActivityVos = new ArrayList<AppActivityVo>();
			// 查询所有活动列表
			List<Activities> activityList = activityService.getActivitiesListByUserid(maps);
			if (activityList.size() > 0) {
				for (int i = 0; i < activityList.size(); i++) {
					Activities activities = activityList.get(i);
					AppActivityVo appActivityVo = new AppActivityVo();
					int activityid = activityList.get(i).getActivityid();

					appActivityVo.setId(activityid);
					// appActivityVo.setCount("139");// 收藏该活动的人数
					// int ifmy = activities.get
					appActivityVo.setIfmy("1");// 是否满员0,满员;1,未满员
					int activityiespid = activities.getActivitypic();
					Attach attach = attachService.selectAttachById(activityiespid);
					String savepath = attach.getSavepath();
					String savefile = attach.getSavefile();
					String sourcefile = attach.getSourcefile();
					String filesize = attach.getFilesize();
					String filetype = attach.getFiletype();
					String a[] = savepath.split("tupian/");
					appActivityVo.setPurl(ht + a[1] + "/" + savefile);
					appActivityVo.setActivityurl(ul + "/h5/example.jsp?id=" + activityid + "&userid=");
					appActivityVos.add(appActivityVo);
				}
			}
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonArray = JSONArray.toJSON(appActivityVos);
			m.put("Lst", jsonArray);
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取用户标签列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetUserTagList")
	public void appGetUserTagList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			// str = URLDecoder.decode(request.getParameter("jsonString"),
			// "UTF-8");
			// System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			// JSONObject object = JSONObject.parseObject(str);
			// System.out.println("===============" + str);
			List<UserTag> userTags1 = new ArrayList<UserTag>();
			// 查询所有用户标签
			List<UserTag> userTags = userTagService.selectAllUserTag();
			if (userTags.size() > 0) {
				for (int i = 0; i < userTags.size(); i++) {
					UserTag userTag = new UserTag();
					userTag.setTagid(userTags.get(i).getTagid());
					userTag.setTagname(userTags.get(i).getTagname());
					userTags1.add(userTag);
				}
			}
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonArray = JSONArray.toJSON(userTags1);
			m.put("Lst", jsonArray);
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取某个用户的标签和签名
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetUserTagsAndSign")
	public void appGetUserTagsAndSign(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			int userid = Integer.parseInt(object.get("userid").toString());
			AppUserTagsSign userTagsSign = new AppUserTagsSign();
			// 根据uuserid获取用户的信息
			UserInfo userInfo = userService.getUserById(userid);
			if (!"".equals(userInfo.getSign()) && userInfo.getSign() != null) {
				userTagsSign.setSign(userInfo.getSign());
			} else {
				userTagsSign.setSign("");
			}
			userTagsSign.setUserid(userid);
			List<UserTag> userTags1 = new ArrayList<UserTag>();
			// 查询用户对应的标签名称和ID
			if (!"".equals(userInfo.getTags()) && userInfo.getTags() != null) {
				String[] tags = userInfo.getTags().split(",");
				if (tags.length > 0) {
					for (int i = 0; i < tags.length; i++) {
						UserTag userTag = new UserTag();
						System.out.println("====================================tags[i]=" + tags[i]);
						userTag = userTagService.selectUserTagById(Integer.parseInt(tags[i]));
						userTags1.add(userTag);
					}
				}
			}
			userTagsSign.setUserTags(userTags1);
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonA = JSONObject.toJSON(userTagsSign);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP修改某个用户的标签和签名
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appUpdateUserTagsAndSign")
	public void appUpdateUserTagsAndSign(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			int userid = Integer.parseInt(object.get("userid").toString());
			String tags = object.get("tags").toString();
			String sign = object.get("sign").toString();
			// 根据userid修改用户的信息
			UserInfo userinfo = new UserInfo();
			userinfo.setUserid(userid);
			userinfo.setTags(tags);
			userinfo.setSign(sign);
			int i = userService.UpdateUserInfo(userinfo);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "修改成功!");
				System.out.println("修改成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "修改失败!");
				System.out.println("修改失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取找同好列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetMyHobbyList")
	public void appGetMyHobbyList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			String tag = object.get("tag").toString();
			String p = object.get("p").toString();
			int pagenow = 0;
			System.out.println("获取传入的页码==" + p);
			if (!("").equals(p)) {
				pagenow = Integer.valueOf(p);
			}
			int startItem = 0;
			int endItem = 0;
			if (!"".equals(pagenow) && pagenow != 0) {
				startItem = (pagenow - 1) * PAGE_COUNT + startItem;
				endItem = pagenow * PAGE_COUNT;
			} else {
				endItem = PAGE_COUNT;
			}
			System.out.println("-----------------" + startItem + "====================" + endItem);
			HashMap<String, Integer> maps = new HashMap<String, Integer>();
			maps.put("userid", userid);
			maps.put("startItem", startItem);
			maps.put("endItem", endItem);
			AppHobbyListVo appHobbyListVo = new AppHobbyListVo();
			List<AppActivityVo> appActivityVos = new ArrayList<AppActivityVo>();
			// 查询当前用户的标签
			UserInfo userInfo = userService.getUserById(userid);
			HashSet<String> set = new HashSet<String>();
			List<AppHobbyVo> appHobbyVos = new ArrayList<AppHobbyVo>();
			if (!"".equals(tag)) {
				String[] tags = tag.split(",");
				if (tags.length > 0) {
					for (int i = 0; i < tags.length; i++) {
						System.out.println("=================tags[i]" + tags[i]);
						UserTag userTag = userTagService.selectUserTagById(Integer.parseInt(tags[i]));
						String tagid = tags[i].toString();
						UserInfo userInfo2 = new UserInfo();
						userInfo2.setTags("%" + tagid + "%");
						List<UserInfo> userInfos = userService.getUserListByTag(userInfo2);
						if (userInfos.size() > 0) {
							for (int j = 0; j < userInfos.size(); j++) {
								set.add(String.valueOf(userInfos.get(j).getUserid()));
							}
						}
					}
				}
			}
			System.out.println("set.size()=" + set.size());
			if (set.size() > 0) {
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					AppHobbyVo appHobbyVo = new AppHobbyVo();
					UserInfo uInfo = userService.getUserById(Integer.parseInt(iterator.next()));
					appHobbyVo.setUserid(uInfo.getUserid());
					appHobbyVo.setUsername(uInfo.getNickname());
					if (!"".equals(uInfo.getSex()) && uInfo.getSex() != null) {
						if (uInfo.getSex() == 0) {
							appHobbyVo.setSex("女");
						} else if (uInfo.getSex() == 1) {
							appHobbyVo.setSex("男");
						} else {
							appHobbyVo.setSex("");
						}
					} else {
						appHobbyVo.setSex("男");
					}
					appHobbyVo.setAddress("北京");
					// 用户头像
					if (!"".equals(uInfo.getPhoto()) && uInfo.getPhoto() != null) {
						int photo = uInfo.getPhoto();
						Attach attach = attachService.selectAttachById(photo);
						String savepath = attach.getSavepath();
						String savefile = attach.getSavefile();
						String sourcefile = attach.getSourcefile();
						String filesize = attach.getFilesize();
						String filetype = attach.getFiletype();
						String a[] = savepath.split("tupian/");
						appHobbyVo.setPurl(ht + a[1] + "/" + savefile);
					} else {
						appHobbyVo.setPurl("");
					}
					// 查询用户的标签
					String[] tag1 = uInfo.getTags().split(",");
					List<UserTag> userTags = new ArrayList<UserTag>();
					if (tag1.length > 0) {
						for (int k = 0; k < tag1.length; k++) {
							UserTag userTag = new UserTag();
							userTag = userTagService.selectUserTagById(Integer.parseInt(tag1[k]));
							userTags.add(userTag);
						}
					}
					appHobbyVo.setUserTags(userTags);
					// 查询参加的活动
					HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
					hashMap.put("userid", uInfo.getUserid());
					hashMap.put("startItem", startItem);
					hashMap.put("endItem", endItem);
					List<Activities> activitiesList = activityService.getEnterActivitiesListByUserid(hashMap);
					if (activitiesList.size() > 0) {
						Activities activities = activitiesList.get(0);
						appHobbyVo.setActivitytitle(activities.getActivityname());
						if (!"".equals(activities.getActivitypic()) && activities.getActivitypic() != null) {
							int photo = activities.getActivitypic();
							Attach attach = attachService.selectAttachById(photo);
							String savepath = attach.getSavepath();
							String savefile = attach.getSavefile();
							String sourcefile = attach.getSourcefile();
							String filesize = attach.getFilesize();
							String filetype = attach.getFiletype();
							String a[] = savepath.split("tupian/");
							appHobbyVo.setActivitypul(ht + a[1] + "/" + savefile);
						} else {
							appHobbyVo.setActivitypul("");
						}
					} else {
						appHobbyVo.setActivitytitle("");
						appHobbyVo.setActivitypul("");
					}
					appHobbyVos.add(appHobbyVo);
				}
			}
			appHobbyListVo.setAppHobbyVos(appHobbyVos);
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonA = JSONObject.toJSON(appHobbyListVo);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取找同好详情
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetMyHobbyDetail")
	public void appGetMyHobbyDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			int id = 0;
			// 获取传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			if (object.get("id") != null) {
				id = Integer.parseInt(object.get("id").toString());
			}

			String p = object.get("p").toString();
			int pagenow = 0;
			System.out.println("获取传入的页码==" + p);
			if (!("").equals(p)) {
				pagenow = Integer.valueOf(p);
			}
			int startItem = 0;
			int endItem = 0;
			if (!"".equals(pagenow) && pagenow != 0) {
				startItem = (pagenow - 1) * PAGE_COUNT + startItem;
				endItem = pagenow * PAGE_COUNT;
			} else {
				endItem = PAGE_COUNT;
			}
			System.out.println("-----------------" + startItem + "====================" + endItem);
			HashMap<String, Integer> maps = new HashMap<String, Integer>();
			maps.put("userid", userid);
			maps.put("startItem", startItem);
			maps.put("endItem", endItem);
			// 获取同好，昵称，和头像
			UserInfo uInfo = userService.getUserById(userid);
			AppHobbyDetailVo appHobbyDetailVo = new AppHobbyDetailVo();
			if (!"".equals(uInfo.getPhoto()) && uInfo.getPhoto() != null) {
				int photo = uInfo.getPhoto();
				Attach attach = attachService.selectAttachById(photo);
				String savepath = attach.getSavepath();
				String savefile = attach.getSavefile();
				String sourcefile = attach.getSourcefile();
				String filesize = attach.getFilesize();
				String filetype = attach.getFiletype();
				String a[] = savepath.split("tupian/");
				appHobbyDetailVo.setPurl(ht + a[1] + "/" + savefile);
			}
			appHobbyDetailVo.setUserid(uInfo.getUserid());
			appHobbyDetailVo.setUsername(uInfo.getNickname());
			if (!"".equals(uInfo.getSign()) && uInfo.getSign() != null) {
				appHobbyDetailVo.setSign(uInfo.getSign());
			} else {
				appHobbyDetailVo.setSign("");
			}

			if (!"".equals(uInfo.getSex()) && uInfo.getSex() != null) {
				if (uInfo.getSex() == 0) {
					appHobbyDetailVo.setSex("女");
				} else if (uInfo.getSex() == 1) {
					appHobbyDetailVo.setSex("男");
				} else {
					appHobbyDetailVo.setSex("");
				}
			} else {
				appHobbyDetailVo.setSex("男");
			}
			appHobbyDetailVo.setAddress("北京");
			// 查询用户的标签
			String[] tag = uInfo.getTags().split(",");
			List<UserTag> userTags = new ArrayList<UserTag>();
			if (tag.length > 0) {
				for (int k = 0; k < tag.length; k++) {
					System.out.println("=============================tag[k]=" + tag[k]);
					UserTag userTag = new UserTag();
					userTag = userTagService.selectUserTagById(Integer.parseInt(tag[k]));
					userTags.add(userTag);
				}
			}
			appHobbyDetailVo.setUserTags(userTags);
			// 判断当前登陆用户是否屏蔽该用户
			Shield shield = new Shield();
			shield.setUserid(id);
			shield.setShieid(userid);
			Shield shield1 = shieldService.selectShieldByUseridAndShieldId(shield);
			if (shield1 != null) {
				appHobbyDetailVo.setIfshield("1");
			} else {
				appHobbyDetailVo.setIfshield("0");
			}
			// 查询参加的活动
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			hashMap.put("userid", uInfo.getUserid());
			hashMap.put("startItem", startItem);
			hashMap.put("endItem", endItem);
			List<AppHobbyVo> appHobbyVos = new ArrayList<AppHobbyVo>();
			List<Activities> activitiesList = activityService.getEnterActivitiesListByUserid(hashMap);
			if (activitiesList.size() > 0) {
				for (int i = 0; i < activitiesList.size(); i++) {
					AppHobbyVo appHobbyVo = new AppHobbyVo();
					Activities activities = activitiesList.get(i);
					appHobbyVo.setActivitytitle(activities.getActivityname());
					if (!"".equals(activities.getActivitypic()) && activities.getActivitypic() != null) {
						int photo = activities.getActivitypic();
						Attach attach = attachService.selectAttachById(photo);
						String savepath = attach.getSavepath();
						String savefile = attach.getSavefile();
						String sourcefile = attach.getSourcefile();
						String filesize = attach.getFilesize();
						String filetype = attach.getFiletype();
						String a[] = savepath.split("tupian/");
						appHobbyVo.setPurl(ht + a[1] + "/" + savefile);
					} else {
						appHobbyVo.setPurl("");
					}
					appHobbyVos.add(appHobbyVo);
				}
			}
			appHobbyDetailVo.setActivities(appHobbyVos);
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonA = JSONObject.toJSON(appHobbyDetailVo);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取找同好列表中当前登录用户的头像标签的接口
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetMyDetail")
	public void appGetMyDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			HashMap<String, String> usermaps = new HashMap<String, String>();
			// 获取同好，昵称，和头像
			UserInfo uInfo = userService.getUserById(userid);
			if (!"".equals(uInfo.getPhoto()) && uInfo.getPhoto() != null) {
				int photo = uInfo.getPhoto();
				Attach attach = attachService.selectAttachById(photo);
				String savepath = attach.getSavepath();
				String savefile = attach.getSavefile();
				String sourcefile = attach.getSourcefile();
				String filesize = attach.getFilesize();
				String filetype = attach.getFiletype();
				String a[] = savepath.split("tupian/");
				usermaps.put("purl", ht + a[1] + "/" + savefile);
			} else {
				usermaps.put("purl", "");
			}

			// 查询用户的标签
			System.out.println(uInfo.getTags());
			String[] tag = (uInfo.getTags()).split(",");
			System.out.println(tag.toString());
			String tags = "";
			if (tag.length > 0) {
				for (int k = 0; k < tag.length; k++) {
					UserTag userTag = new UserTag();
					System.out.println("tag[k]=" + tag[k]);
					userTag = userTagService.selectUserTagById(Integer.parseInt(tag[k]));
					tags += userTag.getTagname() + "、";
				}
			}
			usermaps.put("tag", tags);

			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonA = JSONObject.toJSON(usermaps);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端注册发送验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appSendSMS")
	public void appSendSMS(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			String phone = object.get("phone").toString();
			int type = Integer.parseInt(object.get("type").toString());

			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 先根据phone查询是否存在了该手机号
			UserInfo user = new UserInfo();
			user.setPhone(phone);
			List<UserInfo> userList = userService.selectByPhone(user);
			if (userList.size() > 0) {
				m.put("Scd", "0");
				m.put("Msg", "该手机号码已被注册!");
				System.out.println("该手机号码已被注册!");
			} else {
				// 封装数据
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("mobilePhoneNumber", phone);
				String json = JSON.toJSON(hashMap).toString();
				if (type == 0) {
					results = SMSToAndroid.SMSAndroid(json);
				}
				if (type == 1) {
					results = SMSToIOS.SMSIOS(json);
				}
				System.out.println("===============================results=" + results);
				JSONObject jsonObject = JSONObject.parseObject(results);
				if ("{}".equals(results)) {
					m.put("Scd", "1");
					m.put("Msg", "发送成功!");
					System.out.println("发送成功!");
				} else {
					m.put("Scd", "0");
					m.put("Msg", jsonObject.get("error").toString());
					System.out.println("发送失败!");
				}
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端注册验证验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appValidateSMS")
	public void appValidateSMS(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			String phone = object.get("phone").toString();
			int type = Integer.parseInt(object.get("type").toString());
			String code = object.get("code").toString();
			// 封装数据
			if (type == 0) {
				results = SMSToAndroid.validateSMS(phone, code);
			}
			if (type == 1) {
				results = SMSToIOS.validateSMS(phone, code);
			}
			System.out.println("===============================results=" + results);
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			JSONObject jsonObject = JSONObject.parseObject(results);
			if ("{}".equals(results)) {
				m.put("Scd", "1");
				m.put("Msg", "验证成功!");
				System.out.println("验证成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", jsonObject.get("error").toString());
				System.out.println("验证失败!");
			}

			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端找回密码发送验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appSendUpdateSMS")
	public void appSendUpdateSMS(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			String phone = object.get("phone").toString();
			int type = Integer.parseInt(object.get("type").toString());

			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 封装数据
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("mobilePhoneNumber", phone);
			if (type == 0) {
				hashMap.put("template", "android_find");
				String json = JSON.toJSON(hashMap).toString();
				results = SMSToAndroid.SMSAndroid(json);
			}
			if (type == 1) {
				hashMap.put("template", "ios_find");
				String json = JSON.toJSON(hashMap).toString();
				results = SMSToIOS.SMSIOS(json);
			}
			System.out.println("===============================results=" + results);
			JSONObject jsonObject = JSONObject.parseObject(results);
			if ("{}".equals(results)) {
				m.put("Scd", "1");
				m.put("Msg", "发送成功!");
				System.out.println("发送成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", jsonObject.get("error").toString());
				System.out.println("发送失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * app端找回密码发送验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/appSendUpdatePhoneSMS")
	public void appSendUpdatePhoneSMS(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		String results = "";
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			String phone = object.get("phone").toString();
			int type = Integer.parseInt(object.get("type").toString());

			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 封装数据
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("mobilePhoneNumber", phone);
			if (type == 0) {
				hashMap.put("template", "android_updatephone");
				String json = JSON.toJSON(hashMap).toString();
				results = SMSToAndroid.SMSAndroid(json);
			}
			if (type == 1) {
				hashMap.put("template", "ios_updatephone");
				String json = JSON.toJSON(hashMap).toString();
				results = SMSToIOS.SMSIOS(json);
			}
			System.out.println("===============================results=" + results);
			JSONObject jsonObject = JSONObject.parseObject(results);
			if ("{}".equals(results)) {
				m.put("Scd", "1");
				m.put("Msg", "发送成功!");
				System.out.println("发送成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", jsonObject.get("error").toString());
				System.out.println("发送失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取我的活动中历史活动列表信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetHistoryActivityList")
	public void appGetHistoryActivityList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			int userid = 0;
			if (!"".equals(object.get("userid")) && object.get("userid") != null) {
				userid = Integer.parseInt(object.get("userid").toString());
			}

			String p = object.get("p").toString();
			int pagenow = 0;
			System.out.println("获取传入的页码==" + p);
			if (!("").equals(p)) {
				pagenow = Integer.valueOf(p);
			}
			int startItem = 0;
			int endItem = 0;
			if (!"".equals(pagenow) && pagenow != 0) {
				startItem = (pagenow - 1) * PAGE_COUNT + startItem;
				endItem = pagenow * PAGE_COUNT;
			} else {
				endItem = PAGE_COUNT;
			}
			System.out.println("-----------------" + startItem + "====================" + endItem);
			HashMap<String, Integer> maps = new HashMap<String, Integer>();
			maps.put("startItem", startItem);
			maps.put("endItem", endItem);
			ActivityVo activityvo = new ActivityVo();
			activityvo.setUserid(userid);
			activityvo.setEndtime(now);
			activityvo.setStartitem(startItem);
			activityvo.setEnditem(endItem);
			List<AppActivityVo> appActivityVos = new ArrayList<AppActivityVo>();
			// 查询所有活动列表
			List<Activities> activityList = activityService.getHistoryActivitiesListByUserId(activityvo);
			if (activityList.size() > 0) {
				for (int i = 0; i < activityList.size(); i++) {
					Activities activities = activityList.get(i);
					AppActivityVo appActivityVo = new AppActivityVo();
					int activityid = activityList.get(i).getActivityid();
					appActivityVo.setId(activityid);
					appActivityVo.setActivityname(activities.getActivityname());
					// 查询收藏该活动的人数
					List<UserCollect> userCollects = userCollectService.selectByActivityid(activityid);
					appActivityVo.setCount(String.valueOf(userCollects.size()));// 收藏该活动的人数
					int ifmy = activities.getIfmy();
					if (ifmy == 0) {
						appActivityVo.setIfmy("1");// 是否满员0,满员;1,未满员
					} else {
						appActivityVo.setIfmy("0");// 是否满员0,满员;1,未满员
					}
					UserCollect userCollect = new UserCollect();
					userCollect.setUserid(userid);
					userCollect.setActivityid(activityid);
					List<UserCollect> usercollectList = userCollectService.selectByUseridAndActivityid(userCollect);
					if (usercollectList.size() > 0) {
						appActivityVo.setIfsc("0");// 已收藏
					} else {
						appActivityVo.setIfsc("1");// 未收藏
					}
					if (activities.getActivitycost().equals("0")) {
						appActivityVo.setIffree("0");// 是免费
					} else {
						appActivityVo.setIffree("1");// 不是免费
					}
					String date = activities.getActivitystart();
					Week week = DateUtil.getWeek(date);
					if (week.getChineseName().equals("星期六")) {
						appActivityVo.setIfweek("0");// 是周末
					} else if (week.getChineseName().equals("星期日")) {
						appActivityVo.setIfweek("0");// 是周末
					} else {
						appActivityVo.setIfweek("1");// 不是周末
					}
					if (!"".equals(activities.getActivitypic()) && activities.getActivitypic() != null) {
						int photo = activities.getActivitypic();
						Attach attach = attachService.selectAttachById(photo);
						String savepath = attach.getSavepath();
						String savefile = attach.getSavefile();
						String sourcefile = attach.getSourcefile();
						String filesize = attach.getFilesize();
						String filetype = attach.getFiletype();
						String a[] = savepath.split("tupian/");
						appActivityVo.setPurl(ht + a[1] + "/" + savefile);
					} else {
						appActivityVo.setPurl("");
					}
					appActivityVo.setActivityurl(ul + "/h5/example.jsp?id=" + activityid + "&userid=" + userid);
					appActivityVos.add(appActivityVo);
				}
			}
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonArray = JSONArray.toJSON(appActivityVos);
			m.put("Lst", jsonArray);
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取活动列表信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetVersionUpdate")
	public void appGetVersionUpdate(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 获取传入的UserId
			 String version=null;
			 String maxVersion =null;
			if (!"".equals(object.get("version")) && object.get("version") != null) {
				version =  object.get("version").toString();
			}
			// 获取服务器数据库中最大版本号
			maxVersion = appVersionService.appversionList().get(0).getVersionnum().toString();
			System.out.println("===========================maxVersion=" + maxVersion);
			if ((version.compareTo(maxVersion))<0) {
				// 说明手机上的版本比服务器上的低，需要返回最新版本信息
				Appversion appversion = appVersionService.appversionList().get(0);
				m.put("Scd", "0");
				m.put("Msg", "需要更新最新版本！");
				m.put("url", appversion.getAppurl());
				m.put("updateversion", appversion.getVersionnum());
				m.put("updatesize", appversion.getAppsize());
				m.put("updateContent", appversion.getUpdatecontent());
			} else {
				m.put("Scd", "1");
				m.put("Msg", "已经是最新版本！");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP获取消息中心我的消息列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appGetMessageList")
	public void appGetMessageList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取传入的UserId
			int userid = 0;
			if (!"".equals(object.get("userid")) && object.get("userid") != null) {
				userid = Integer.parseInt(object.get("userid").toString());
			}
			String p = object.get("p").toString();
			int pagenow = 0;
			System.out.println("获取传入的页码==" + p);
			if (!("").equals(p)) {
				pagenow = Integer.valueOf(p);
			}
			int startItem = 0;
			int endItem = 0;
			if (!"".equals(pagenow) && pagenow != 0) {
				startItem = (pagenow - 1) * PAGE_COUNT + startItem;
				endItem = pagenow * PAGE_COUNT;
			} else {
				endItem = PAGE_COUNT;
			}
			System.out.println("-----------------" + startItem + "====================" + endItem);
			HashMap<String, Integer> maps = new HashMap<String, Integer>();
			maps.put("startItem", startItem);
			maps.put("endItem", endItem);
			maps.put("userid", userid);

			// 查询所有活动列表
			List<Message> messageList = messageService.getMessageListByUserid(maps);

			HashMap<String, Object> m = new HashMap<String, Object>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			Object jsonArray = JSONArray.toJSON(messageList);
			m.put("Lst", jsonArray);
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP添加屏蔽或者取消屏蔽
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appShieldShare")
	public void appShieldShare(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int shieid = Integer.parseInt(object.get("shieid").toString());
			int type = Integer.parseInt(object.get("type").toString());
			// 封装要修改的对象
			Shield shield = new Shield();
			shield.setUserid(userid);
			shield.setShieid(shieid);
			// 判断类型是增加还是减少
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (type == 0) {
				// 说明是添加屏蔽
				Shield shield2 = shieldService.selectShieldByUseridAndShieldId(shield);
				if (shield2 != null) {
					m.put("Scd", "1");
					m.put("Msg", "屏蔽成功!");
					System.out.println("屏蔽成功!");
				} else {
					int i = shieldService.addShield(shield);
					if (i > 0) {
						m.put("Scd", "1");
						m.put("Msg", "屏蔽成功!");
						System.out.println("屏蔽成功!");
					} else {
						m.put("Scd", "0");
						m.put("Msg", "屏蔽失败!");
						System.out.println("屏蔽失败!");
					}
				}

			}

			if (type == 1) {
				// 说明是取消屏蔽
				Shield shield1 = shieldService.selectShieldByUseridAndShieldId(shield);
				if (shield1 != null) {
					// 说明曾经对此用户添加过屏蔽;并且要删除该条数据
					int i = shieldService.deleteShieldById(shield1.getId());
					if (i > 0) {
						m.put("Scd", "1");
						m.put("Msg", "取消成功!");
						System.out.println("取消成功!");
					} else {
						m.put("Scd", "0");
						m.put("Msg", "取消失败!");
						System.out.println("取消失败!");
					}
				} else {
					m.put("Scd", "0");
					m.put("Msg", "没有屏蔽过该用户!");
					System.out.println("没有屏蔽过该用户!");
				}
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * APP举报某条圈子
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/appAddInformagainst")
	public void appAddInformagainst(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String str;
		String result;
		try {
			out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			str = URLDecoder.decode(request.getParameter("jsonString"), "UTF-8");
			System.out.println("===============" + str);
			// JSONObject jb = new JSONObject();
			// 将json格式的字符串转换为json对象，并取得该对象的“userName”属性值
			JSONObject object = JSONObject.parseObject(str);
			// 获取移动端传入的UserId
			int userid = Integer.parseInt(object.get("userid").toString());
			int shareid = Integer.parseInt(object.get("shareid").toString());
			// 封装要修改的对象
			Informagainst informagainst = new Informagainst();
			informagainst.setUserid(userid);
			informagainst.setShareid(shareid);
			// 判断类型是增加还是减少
			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			int i = informagainstService.addInformagainst(informagainst);
			if (i > 0) {
				m.put("Scd", "1");
				m.put("Msg", "举报成功!");
				System.out.println("举报成功!");
			} else {
				m.put("Scd", "0");
				m.put("Msg", "举报失败!");
				System.out.println("举报失败!");
			}
			Object jsonA = JSONObject.toJSON(m);
			map.put("Stu", "1");
			map.put("Rst", jsonA);
			jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Stu", "0");
			map.put("Rst", "");
			Object jsonA = JSONObject.toJSON(map);
			result = jsonA.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		out.println(result);
		out.flush();
		out.close();
	}
}
