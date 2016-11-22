package com.sxh.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sxh.common.UrlUtil;
import com.sxh.model.Activities;
import com.sxh.model.ActivityType;
import com.sxh.model.Attach;
import com.sxh.model.UserInfo;
import com.sxh.service.ActivityService;
import com.sxh.service.ActivityTypeService;
import com.sxh.service.AttachService;
import com.sxh.service.ShareActivitiesService;
import com.sxh.service.SuggestionService;
import com.sxh.service.UserCollectService;
import com.sxh.service.UserCommentService;
import com.sxh.service.UserEnterService;
import com.sxh.service.UserLikeService;
import com.sxh.service.UserService;
import com.sxh.service.UserTagService;
import com.sxh.vo.ActivityVo;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	// 获取当前时间
	Date date = new Date();
	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	String time = f.format(date);
	SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = f1.format(date);
	SimpleDateFormat bh = new SimpleDateFormat("yyyyMMdd");
	String bianhao = bh.format(date);
	SimpleDateFormat f2 = new SimpleDateFormat("yyyyMMddHHmmSS");
	String time1 = f2.format(date);
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
	private ActivityTypeService activityTypeService;
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

	@RequestMapping("/showactivities")
	public ModelAndView showactivities(HttpServletRequest request) {
		// 获取输入的查询条件
		String activityname = "";
		int activitytypeid = 0;
		String activitycost = "2";
		int activitystatus = 4;
		String starttime = "";
		String endtime = "";

		if (!"".equals(request.getParameter("activityname")) && request.getParameter("activityname") != null) {
			activityname = request.getParameter("activityname").toString();
		}
		if (!"".equals(request.getParameter("activitycost")) && request.getParameter("activitycost") != null) {
			activitycost = request.getParameter("activitycost").toString();
		}
		if (!"".equals(request.getParameter("starttime")) && request.getParameter("starttime") != null) {
			starttime = request.getParameter("starttime").toString() + " 00:00:00";

		}
		if (!"".equals(request.getParameter("endtime")) && request.getParameter("endtime") != null) {
			endtime = request.getParameter("endtime").toString() + " 00:00:00";
		}
		if (!"".equals(request.getParameter("activitytypeid")) && request.getParameter("activitytypeid") != null) {
			activitytypeid = Integer.parseInt(request.getParameter("activitytypeid").toString());
		}
		if (!"".equals(request.getParameter("activitystatus")) && request.getParameter("activitystatus") != null) {
			activitystatus = Integer.parseInt(request.getParameter("activitystatus").toString());
		}
		ActivityVo activityVo = new ActivityVo();
		activityVo.setActivityname("%" + activityname + "%");
		activityVo.setActivitycost(activitycost);
		System.out.println("===========activitycost=" + activitycost);
		activityVo.setActivitystatus(activitystatus);
		activityVo.setActivitytypeid(activitytypeid);
		activityVo.setStarttime(starttime);
		activityVo.setEndtime(endtime);
		ModelAndView mv = new ModelAndView();
		List<Activities> activities = activityService.getActivitiesListByActivityVo(activityVo);
		System.out.println("=================activities=" + activities.size());
		if (activities.size() > 0) {
			for (int i = 0; i < activities.size(); i++) {
				int activitytypeids = activities.get(i).getActivitytypeid();
				ActivityType activityType = activityTypeService.selectActivityTypeById(activitytypeids);
				String activityTypeName = activityType.getActivityname();
				activities.get(i).setActivityadd(activityTypeName);
			}
		}
		ActivityType activityType = new ActivityType();
		activityType.setStatus(1);
		List<ActivityType> activityTypeList = activityTypeService.getActivityType(activityType);
		System.out.println("==============activityTypeList=" + activityTypeList.size());
		mv.addObject("activityTypeList", activityTypeList);
		mv.addObject("activitiesList", activities);
		mv.setViewName("/activity/activityList");
		return mv;
	}

	/**
	 * 跳转新增活动页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addactivities")
	public ModelAndView addactivities(HttpServletRequest request) {
		ActivityType activityType = new ActivityType();
		activityType.setStatus(1);
		List<ActivityType> activityTypeList = activityTypeService.getActivityType(activityType);
		System.out.println("==============activityTypeList=" + activityTypeList.size());
		ModelAndView mv = new ModelAndView();
		mv.addObject("activityTypeList", activityTypeList);
		mv.setViewName("/activity/activityEdit");
		return mv;
	}

	/**
	 * 保存活动页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveactivities")
	public ModelAndView saveactivities(HttpServletRequest request) {
		// 获取传入的数据
		String activityname = request.getParameter("activityname").toString();// 活动名称
		System.out.println("=====================activityname=" + activityname);
		String activitydetail = request.getParameter("activitydetail").toString();// 活动详情
		System.out.println("=====================activitydetail=" + activitydetail);
		String activityflow = request.getParameter("activityprocess").toString();// 活动流程
		System.out.println("=====================activityflow=" + activityflow);
		String attachid = request.getParameter("attachid").toString();// 图片附件id
		System.out.println("=====================attachid=" + attachid);
		String starttime = request.getParameter("starttime").toString();// 开始时间
		System.out.println("=====================starttime=" + starttime);
		String endtime = request.getParameter("endtime").toString();// 结束时间
		System.out.println("=====================endtime=" + endtime);
		String activityadd = request.getParameter("activityadd").toString();// 活动地址
		System.out.println("=====================activityadd=" + activityadd);
		String activitycount = request.getParameter("activitycount").toString();// 限制人数
		System.out.println("=====================activitycount=" + activitycount);
		String activitycost = request.getParameter("activitycost").toString();// 费用
		System.out.println("=====================activitycost=" + activitycost);
		String activitytypeid = request.getParameter("activitytypeid").toString();// 类型id
		System.out.println("=====================activitytypeid=" + activitytypeid);
		String userid = request.getParameter("userid").toString();// 获取创建人id
		System.out.println("=====================userid=" + userid);
		// 封装数据模型
		Activities activities = new Activities();
		activities.setActivityname(activityname);
		activities.setActivitydetail(activitydetail);
		activities.setActivityflow(activityflow);
		activities.setActivityadd(activityadd);
		activities.setActivitypic(Integer.parseInt(attachid));
		activities.setActivitystart(starttime);
		activities.setActivityend(endtime);
		activities.setActivitycost(activitycost);
		activities.setPeoplecount(Integer.parseInt(activitycount));
		activities.setActivitytypeid(Integer.parseInt(activitytypeid));
		activities.setActivitystatus(0);
		activities.setUserid(Integer.parseInt(userid));
		activities.setEntercount(0);
		activities.setIfmy(0);
		String number = "";
		Date date2 = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String time = f.format(date2);
		String date = new StringBuilder("%").append(time).append("%").toString();
		activities.setCreatetime(date);
		List<Activities> activitiesList = activityService.getCountActivityToday(activities);
		System.out.println("==============" + activitiesList.size());
		number = String.valueOf(activitiesList.size());
		number = (Integer.parseInt(number) + 1) + "";
		if (Integer.parseInt(number) < 10) {
			number = "000" + number;
		} else if (Integer.parseInt(number) >= 10 && Integer.parseInt(number) < 100) {
			number = "00" + number;
		} else if (Integer.parseInt(number) >= 100 && Integer.parseInt(number) < 1000) {
			number = "0" + number;
		}
		// 普通用户注册
		activities.setActivitynum(bianhao + number);
		Date date1 = new Date();
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = f1.format(date1);
		activities.setCreatetime(now);
		// 插入数据库
		int i = activityService.saveActivity(activities);
		System.out.println("i=" + i);
		ModelAndView mv = null;
		if (i > 0) {
			mv = new ModelAndView("redirect:/activity/showactivities.do");
		}
		return mv;
	}

	/**
	 * WEB端获取活动详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getActivityDetail")
	public ModelAndView getActivityDetail(HttpServletRequest request) {
		// 获取输入的活动id
		String activityid = "";
		if (!"".equals(request.getParameter("activityid")) && request.getParameter("activityid") != null) {
			activityid = request.getParameter("activityid").toString();
		}
		System.out.println("=================activityid=" + activityid);

		ModelAndView mv = new ModelAndView();

		// 根据id查询活动详情
		Activities activities = activityService.getActivityById(Integer.parseInt(activityid));
		System.out.println(activities.toString());

		// 获取活动图片
		if (!"".equals(activities.getActivitypic()) && activities.getActivitypic() != null) {
			int photo = activities.getActivitypic();
			Attach attach = attachService.selectAttachById(photo);
			String savepath = attach.getSavepath();
			String savefile = attach.getSavefile();
			String sourcefile = attach.getSourcefile();
			String filesize = attach.getFilesize();
			String filetype = attach.getFiletype();
			String a[] = savepath.split("tupian/");
			mv.addObject("purl", ht + a[1] + "/" + savefile);

		}
		// 获取活动发布人名称
		UserInfo userInfo = null;
		if (!"".equals(activities.getUserid()) && activities.getUserid() != null) {
			userInfo = userService.getUserById(activities.getUserid());
		}
		// 获取报名此活动的人员名称列表以及手机号
		List<UserInfo> userInfoList = userEnterService.getUserEnterByActivity(Integer.parseInt(activityid));
		mv.addObject("userInfo", userInfo);
		mv.addObject("activities", activities);
		mv.addObject("userInfoList", userInfoList);
		mv.setViewName("/activity/activityDetail");
		return mv;
	}

	/**
	 * WEB端上传活动图片
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadActivityPic", method = RequestMethod.POST)
	public void uploadActivityPic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String activityname = URLDecoder.decode(request.getParameter("activityname"), "UTF-8");
		System.out.println("-------------------------------------------------activityname=" + activityname);
		String userid = URLDecoder.decode(request.getParameter("userid"), "UTF-8");
		System.out.println("-------------------------------------------------userid=" + userid);
		String sf2 = "yyyyMMddHHmmss";
		String attachId = "";
		// DatabaseBo dbo = new DatabaseBo();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String path = request.getSession().getServletContext().getRealPath("/") + "tupian\\";
		System.out.println("path---" + path);
		String savePath = path + bianhao;
		String saveUrl = "/tupian/" + bianhao + "/";
		System.out.println("saveUrl:" + saveUrl);
		String[] fileTypes = { "gif", "jpg", "jpeg", "png", "bmp" };
		PrintWriter out = response.getWriter();
		long maxSize = 5242880L;
		System.out.println("savePath--" + savePath);
		File root = new File(savePath);
		if (!root.isDirectory()) {
			System.out.println("创建新文件夹成功");
			root.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory();
		System.out.println("FileItemFactory----------");
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = null;
		try {
			items = ((ServletFileUpload) upload).parseRequest(request);
			System.out.println("items----------");
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		System.out.println("itr----------");
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			String value = item.getFieldName();
			System.out.println("fileName----------" + fileName + ";value" + value);
			long fileSize = item.getSize();
			System.out.println("fileSize----------" + fileSize);
			if (!item.isFormField()) {
				long itemSize = item.getSize();
				if (itemSize > maxSize) {
					out.write(getError("上传文件大小超过限制。"));
					return;
				}
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.asList(fileTypes).contains(fileExt)) {
					out.write(getError("上传文件扩展名是不允许的扩展名。"));
					return;
				}
				System.out.println("fileExt----------");
				SimpleDateFormat df = new SimpleDateFormat(sf2);
				String newFileName = df.format(new Date()) + new Random().nextInt(1000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
					System.out.println("----------:uploadedFile:" + uploadedFile);
					savePath = "/" + savePath;
					System.out.println("sp------" + savePath);
					System.out.println("baocunpath--------" + savePath.replace("\\", "/"));
					// WaterMarkUtils waterMarkUtils = new WaterMarkUtils();
					// String srcImage = (savePath.replace("\\", "/") + "/" +
					// newFileName).substring(1,
					// (savePath.replace("\\", "/") + "/" +
					// newFileName).length());
					// String outImage = (savePath.replace("\\", "/") + "/water"
					// + newFileName).substring(1,
					// (savePath.replace("\\", "/") + "/water" +
					// newFileName).length());
					// System.out.println("=====================srcimage=" +
					// srcImage);
					// System.out.println("=====================outimage=" +
					// outImage);
					// waterMarkUtils.mark(srcImage, outImage, Color.white,
					// activityname);

					Attach attach = new Attach();
					attach.setUserid(userid);
					attach.setUptime(now);
					attach.setSourcefile(newFileName);
					attach.setFilesize(String.valueOf(itemSize));
					attach.setFiletype(fileExt);
					attach.setSavepath(savePath.replace("\\", "/"));
					attach.setSavefile(newFileName);
					int ID = attachService.insert(attach);
					System.out.println("ID=" + ID);
					attachId = String.valueOf(attach.getId());
					// String a[] = savePath.replace("\\",
					// "/").split("tupian/");
					// ScaleImageUtils.resize(400, 300,
					// ImageQuality.high.getQuality(), null,
					// ImageIO.read(new URL(ht + a[1] + "/" + newFileName)));
					System.out.println("pid = " + attach.getId());
					System.out.println("===================attid=" + attachId);
					System.out.println("文件在上传中------------------------");
				} catch (Exception e) {
					e.printStackTrace();
					out.write(getError("上传文件失败。"));
					return;
				}
				JSONObject obj = new JSONObject();
				obj.put("error", Integer.valueOf(0));
				obj.put("url", saveUrl + newFileName);
				obj.put("attId", attachId);
				System.out.println("上传图片结果----------------------------");
				System.out.println(obj.toString());
				System.out.println("上传图片结果----------------------------");
				out.write(obj.toString());
			}
		}
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", Integer.valueOf(1));
		obj.put("message", message);
		return obj.toString();
	}

	private static boolean isEmpty(String value) {
		return (value == null) || (value.trim().equals(""));
	}

	// 删除活动
	@RequestMapping("/delactivity")
	public void delactivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		String activityid = request.getParameter("activityid");
		Activities activities = new Activities();
		activities.setActivityid(Integer.parseInt(activityid));
		activities.setActivitystatus(3);
		int i = activityService.updateActivity(activities);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("success", "删除成功!");
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

	/**
	 * WEB端获取活动详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateActivityDetail")
	public ModelAndView updateActivityDetail(HttpServletRequest request) {
		// 获取输入的活动id
		String activityid = "";
		if (!"".equals(request.getParameter("activityid")) && request.getParameter("activityid") != null) {
			activityid = request.getParameter("activityid").toString();
		}
		System.out.println("=================activityid=" + activityid);
		ActivityType activityType = new ActivityType();
		activityType.setStatus(1);
		List<ActivityType> activityTypeList = activityTypeService.getActivityType(activityType);
		System.out.println("==============activityTypeList=" + activityTypeList.size());

		ModelAndView mv = new ModelAndView();

		// 根据id查询活动详情
		Activities activities = activityService.getActivityById(Integer.parseInt(activityid));
		System.out.println(activities.toString());

		// 获取活动图片
		if (!"".equals(activities.getActivitypic()) && activities.getActivitypic() != null) {
			int photo = activities.getActivitypic();
			Attach attach = attachService.selectAttachById(photo);
			String savepath = attach.getSavepath();
			String savefile = attach.getSavefile();
			String sourcefile = attach.getSourcefile();
			String filesize = attach.getFilesize();
			String filetype = attach.getFiletype();
			String a[] = savepath.split("tupian/");
			mv.addObject("purl", ht + a[1] + "/" + savefile);

		}
		// 获取活动发布人名称
		UserInfo userInfo = null;
		if (!"".equals(activities.getUserid()) && activities.getUserid() != null) {
			userInfo = userService.getUserById(activities.getUserid());
		}
		// 获取报名此活动的人员名称列表以及手机号
		List<UserInfo> userInfoList = userEnterService.getUserEnterByActivity(Integer.parseInt(activityid));
		mv.addObject("userInfo", userInfo);
		mv.addObject("activities", activities);
		mv.addObject("userInfoList", userInfoList);
		mv.addObject("activityTypeList", activityTypeList);
		mv.setViewName("/activity/updateActivityEdit");
		return mv;
	}

	/**
	 * 保存活动页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveupdateactivities")
	public ModelAndView saveupdateactivities(HttpServletRequest request) {
		// 获取传入的数据
		String activityid = request.getParameter("activityid").toString();// 活动id
		System.out.println("=====================activityid=" + activityid);
		String activitynum = request.getParameter("activitynum").toString();// 活动编号
		System.out.println("=====================activitynum=" + activitynum);
		String activityname = request.getParameter("activityname").toString();// 活动名称
		System.out.println("=====================activityname=" + activityname);
		String activitydetail = request.getParameter("activitydetail").toString();// 活动详情
		System.out.println("=====================activitydetail=" + activitydetail);
		String activityflow = request.getParameter("activityprocess").toString();// 活动流程
		System.out.println("=====================activityflow=" + activityflow);
		String attachid = request.getParameter("attachid").toString();// 图片附件id
		System.out.println("=====================attachid=" + attachid);
		String starttime = request.getParameter("starttime").toString();// 开始时间
		System.out.println("=====================starttime=" + starttime);
		String endtime = request.getParameter("endtime").toString();// 结束时间
		System.out.println("=====================endtime=" + endtime);
		String activityadd = request.getParameter("activityadd").toString();// 活动地址
		System.out.println("=====================activityadd=" + activityadd);
		String activitycount = request.getParameter("activitycount").toString();// 限制人数
		System.out.println("=====================activitycount=" + activitycount);
		String activitycost = request.getParameter("activitycost").toString();// 费用
		System.out.println("=====================activitycost=" + activitycost);
		String activitytypeid = request.getParameter("activitytypeid").toString();// 类型id
		System.out.println("=====================activitytypeid=" + activitytypeid);
		String userid = request.getParameter("userid").toString();// 获取创建人id
		System.out.println("=====================userid=" + userid);
		// 封装数据模型
		Activities activities = new Activities();
		activities.setActivityid(Integer.parseInt(activityid));
		activities.setActivityname(activityname);
		activities.setActivitydetail(activitydetail);
		activities.setActivityflow(activityflow);
		activities.setActivityadd(activityadd);
		activities.setActivitypic(Integer.parseInt(attachid));
		activities.setActivitystart(starttime);
		activities.setActivityend(endtime);
		activities.setActivitycost(activitycost);
		activities.setPeoplecount(Integer.parseInt(activitycount));
		activities.setActivitytypeid(Integer.parseInt(activitytypeid));
		activities.setActivitystatus(0);
		// activities.setUserid(Integer.parseInt(userid));
		// activities.setEntercount(0);

		// 普通用户注册
		activities.setActivitynum(activitynum);
		// activities.setCreatetime(now);
		int i = activityService.updateActivity(activities);
		System.out.println("i=" + i);
		ModelAndView mv = null;
		if (i > 0) {
			mv = new ModelAndView("redirect:/activity/showactivities.do");
		}
		return mv;
	}

}
