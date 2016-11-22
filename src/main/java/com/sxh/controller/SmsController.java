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
import com.alibaba.fastjson.JSONObject;
import com.sxh.common.MessageToAndroid;
import com.sxh.common.MessageToIOS;
import com.sxh.common.SMSToAndroid;
import com.sxh.common.SMSToIOS;
import com.sxh.model.Message;
import com.sxh.model.SmsTemplate;
import com.sxh.model.TemplateVariables;
import com.sxh.model.UserInfo;
import com.sxh.service.MessageService;
import com.sxh.service.SmsTemplateService;
import com.sxh.service.TemplateVariablesService;
import com.sxh.service.UserService;

@Controller
@RequestMapping("/message")
public class SmsController {
	// 获取当前时间
	Calendar c = Calendar.getInstance();
	SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = f1.format(c.getTime());
	@Autowired
	private TemplateVariablesService templateVariablesService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	// 显示所有短信模板
	@RequestMapping("/showsmstemples")
	public ModelAndView showsmstemples(HttpServletRequest request) {
		String tempname = "";
		String tempstatus = "2";
		if (!"".equals(request.getParameter("tempname")) && request.getParameter("tempname") != null) {
			tempname = request.getParameter("tempname").toString();
		}
		if (!"".equals(request.getParameter("tempstatus")) && request.getParameter("tempstatus") != null) {
			tempstatus = request.getParameter("tempstatus").toString();
		}
		// 封装模板变量数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setTemptype(0);// 短信模板
		smsTemplate.setTempname(tempname);
		smsTemplate.setTempstatus(Integer.parseInt(tempstatus));
		ModelAndView mv = new ModelAndView();
		List<SmsTemplate> smsTemplateList = smsTemplateService.getSmsTemplateList(smsTemplate);
		System.out.println("smsTemplateList.size()" + smsTemplateList.size());
		if (smsTemplateList.size() > 0) {
			for (int i = 0; i < smsTemplateList.size(); i++) {
				if (smsTemplateList.get(i).getTempcontent().length() > 10) {
					smsTemplateList.get(i).setTempcontent(smsTemplateList.get(i).getTempcontent().substring(0, 10) + "...");
				}
			}
		}
		mv.addObject("smsTemplateList", smsTemplateList);
		mv.setViewName("/message/smsTpl");
		return mv;
	}

	// 显示所有站内信模板
	@RequestMapping("/showmessagetemples")
	public ModelAndView showmessagetemples(HttpServletRequest request) {
		// 获取输入的查询条件
		String tempname = "";
		String tempstatus = "2";
		if (!"".equals(request.getParameter("tempname")) && request.getParameter("tempname") != null) {
			tempname = request.getParameter("tempname").toString();
		}
		if (!"".equals(request.getParameter("tempstatus")) && request.getParameter("tempstatus") != null) {
			tempstatus = request.getParameter("tempstatus").toString();
		}
		// 封装模板变量数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setTemptype(1);// 站内信模板
		smsTemplate.setTempname(tempname);
		smsTemplate.setTempstatus(Integer.parseInt(tempstatus));
		ModelAndView mv = new ModelAndView();
		List<SmsTemplate> smsTemplateList = smsTemplateService.getSmsTemplateList(smsTemplate);
		System.out.println("smsTemplateList.size()" + smsTemplateList.size());
		if (smsTemplateList.size() > 0) {
			for (int i = 0; i < smsTemplateList.size(); i++) {
				if (smsTemplateList.get(i).getTempcontent().length() > 10) {
					smsTemplateList.get(i).setTempcontent(smsTemplateList.get(i).getTempcontent().substring(0, 10) + "...");
				}
			}
		}
		mv.addObject("smsTemplateList", smsTemplateList);
		mv.setViewName("/message/messageTpl");
		return mv;
	}

	@RequestMapping("/addsmstemple")
	public ModelAndView addsmstemple(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setTmpstatus(2);
		List<TemplateVariables> templateVariableList = templateVariablesService.getTemplateList(templateVariables);
		System.out.println("=============templateVariableList.size()=" + templateVariableList.size());
		mv.addObject("templateVariableList", templateVariableList);
		mv.setViewName("/message/smsTplEdit");
		return mv;
	}

	@RequestMapping("/addmessagetemple")
	public ModelAndView addmessagetemple(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setTmpstatus(2);
		List<TemplateVariables> templateVariableList = templateVariablesService.getTemplateList(templateVariables);
		System.out.println("=============templateVariableList.size()=" + templateVariableList.size());
		mv.addObject("templateVariableList", templateVariableList);
		mv.setViewName("/message/messageTplEdit");
		return mv;
	}

	@RequestMapping("/savesmstemple")
	public ModelAndView savesmstemple(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取页面传入的数据
		String tempname = request.getParameter("tempname");
		String tempcontent = request.getParameter("tempcontent");
		String tempremark = request.getParameter("tempremark");
		// 封装对象数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setTempcontent(tempcontent);
		smsTemplate.setTempname(tempname);
		smsTemplate.setTempremark(tempremark);
		smsTemplate.setTempdelstatus(0);
		smsTemplate.setTempstatus(1);
		smsTemplate.setTemptype(0);
		int i = smsTemplateService.addSmsTemplate(smsTemplate);
		HashMap<String, String> map = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		if (i > 0) {
			map.put("success", "添加成功!");
			mv.setViewName("redirect:/message/showsmstemples.do");
			return mv;
		} else {
			map.put("fail", "添加失败!");
			mv.setViewName("redirect:/message/showsmstemples.do");
			return mv;
		}
	}

	@RequestMapping("/savemessagetemple")
	public ModelAndView savemessagetemple(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取页面传入的数据
		String tempname = request.getParameter("tempname");
		String tempcontent = request.getParameter("tempcontent");
		String tempremark = request.getParameter("tempremark");
		// 封装对象数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setTempcontent(tempcontent);
		smsTemplate.setTempname(tempname);
		smsTemplate.setTempremark(tempremark);
		smsTemplate.setTempdelstatus(0);
		smsTemplate.setTempstatus(1);
		smsTemplate.setTemptype(1);
		int i = smsTemplateService.addSmsTemplate(smsTemplate);
		HashMap<String, String> map = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		if (i > 0) {
			map.put("success", "添加成功!");
			mv.setViewName("redirect:/message/showmessagetemples.do");
			return mv;
		} else {
			map.put("fail", "添加失败!");
			mv.setViewName("redirect:/message/showmessagetemples.do");
			return mv;
		}
	}

	@RequestMapping("/deltemplate")
	public void deltemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// 获取传入数据
		String tempid = request.getParameter("tempid");
		System.out.println("==================tempid=" + tempid);
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setId(Integer.parseInt(tempid));
		smsTemplate.setTempdelstatus(1);
		int i = smsTemplateService.delSmsTemplate(smsTemplate);
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

	@RequestMapping("/updatesmstemplates")
	public ModelAndView updatesmstemplates(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取页面传入的数据
		String tempid = request.getParameter("tempid");
		// 封装对象数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate = smsTemplateService.selectSmsTemplateById(Integer.parseInt(tempid));
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setTmpstatus(2);
		List<TemplateVariables> templateVariableList = templateVariablesService.getTemplateList(templateVariables);
		System.out.println("=============templateVariableList.size()=" + templateVariableList.size());
		ModelAndView mv = new ModelAndView();
		mv.addObject("templateVariableList", templateVariableList);
		mv.addObject("smsTemplate", smsTemplate);
		mv.setViewName("/message/smsTplEdit1");
		return mv;
	}

	@RequestMapping("/updatemessagetemplates")
	public ModelAndView updatemessagetemplates(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取页面传入的数据
		String tempid = request.getParameter("tempid");
		// 封装对象数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate = smsTemplateService.selectSmsTemplateById(Integer.parseInt(tempid));
		TemplateVariables templateVariables = new TemplateVariables();
		templateVariables.setTmpstatus(2);
		List<TemplateVariables> templateVariableList = templateVariablesService.getTemplateList(templateVariables);
		System.out.println("=============templateVariableList.size()=" + templateVariableList.size());
		ModelAndView mv = new ModelAndView();
		mv.addObject("templateVariableList", templateVariableList);
		mv.addObject("smsTemplate", smsTemplate);
		mv.setViewName("/message/messageTplEdit1");
		return mv;
	}

	@RequestMapping("/saveupdatesmstemple")
	public ModelAndView saveupdatesmstemple(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取页面传入的数据
		String tempid = request.getParameter("tempid");
		String tempname = request.getParameter("tempname");
		String tempcontent = request.getParameter("tempcontent");
		String tempremark = request.getParameter("tempremark");
		// 封装对象数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setId(Integer.parseInt(tempid));
		smsTemplate.setTempcontent(tempcontent);
		smsTemplate.setTempname(tempname);
		smsTemplate.setTempremark(tempremark);
		smsTemplate.setTempdelstatus(0);
		smsTemplate.setTempstatus(1);
		smsTemplate.setTemptype(1);
		int i = smsTemplateService.updateSmsTemplate(smsTemplate);
		HashMap<String, String> map = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		if (i > 0) {
			map.put("success", "添加成功!");
			mv.setViewName("redirect:/message/showsmstemples.do");
			return mv;
		} else {
			map.put("fail", "添加失败!");
			mv.setViewName("redirect:/message/showsmstemples.do");
			return mv;
		}
	}

	@RequestMapping("/saveupdatemessahetemple")
	public ModelAndView saveupdatemessahetemple(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取页面传入的数据
		String tempid = request.getParameter("tempid");
		String tempname = request.getParameter("tempname");
		String tempcontent = request.getParameter("tempcontent");
		String tempremark = request.getParameter("tempremark");
		// 封装对象数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setId(Integer.parseInt(tempid));
		smsTemplate.setTempcontent(tempcontent);
		smsTemplate.setTempname(tempname);
		smsTemplate.setTempremark(tempremark);
		smsTemplate.setTempdelstatus(0);
		smsTemplate.setTempstatus(1);
		smsTemplate.setTemptype(1);
		int i = smsTemplateService.updateSmsTemplate(smsTemplate);
		HashMap<String, String> map = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		if (i > 0) {
			map.put("success", "添加成功!");
			mv.setViewName("redirect:/message/showmessagetemples.do");
			return mv;
		} else {
			map.put("fail", "添加失败!");
			mv.setViewName("redirect:/message/showmessagetemples.do");
			return mv;
		}
	}

	@RequestMapping("/updatesmstemplestatus")
	public void updatesmstemplestatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// 获取页面传入的数据
		String tempid = request.getParameter("tempid");
		String tempstatus = request.getParameter("tempstatus");
		// 封装对象数据
		SmsTemplate smsTemplate = new SmsTemplate();
		smsTemplate.setId(Integer.parseInt(tempid));
		smsTemplate.setTempstatus(Integer.parseInt(tempstatus));
		int i = smsTemplateService.updateSmsTemplate(smsTemplate);
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

	@RequestMapping("/msgpush")
	public ModelAndView msgpush(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		// TemplateVariables templateVariables = new TemplateVariables();
		// templateVariables.setTmpstatus(2);
		// List<TemplateVariables> templateVariableList =
		// templateVariablesService.getTemplateList(templateVariables);
		// System.out.println("=============templateVariableList.size()=" +
		// templateVariableList.size());
		// mv.addObject("templateVariableList", templateVariableList);
		mv.setViewName("/message/msgPush");
		return mv;
	}

	@RequestMapping("/smslist")
	public ModelAndView smslist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		String reception = "";
		String content = "";
		String starttime = "";
		String endtime = "";
		if (!"".equals(request.getParameter("reception")) && request.getParameter("reception") != null) {
			reception = request.getParameter("reception").toString();
		}
		if (!"".equals(request.getParameter("content")) && request.getParameter("content") != null) {
			content = request.getParameter("content").toString();
		}
		if (!"".equals(request.getParameter("starttime")) && request.getParameter("starttime") != null) {
			starttime = request.getParameter("starttime").toString() + " 00:00:00";
		}
		if (!"".equals(request.getParameter("endtime")) && request.getParameter("endtime") != null) {
			endtime = request.getParameter("endtime").toString() + " 00:00:00";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("reception", reception);
		map.put("content", "%" + content + "%");
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		List<Message> smsList = messageService.getSmsList(map);
		System.out.println("=============smsList.size()=" + smsList.size());
		if (smsList.size() > 0) {
			for (int i = 0; i < smsList.size(); i++) {
				if (smsList.get(i).getContent().length() > 10) {
					smsList.get(i).setContent(smsList.get(i).getContent().substring(0, 10) + "...");
				}
			}
		}
		mv.addObject("smsList", smsList);
		mv.setViewName("/message/smsList");
		return mv;
	}

	@RequestMapping("/messagelist")
	public ModelAndView messagelist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		String reception = "";
		String content = "";
		String starttime = "";
		String endtime = "";
		if (!"".equals(request.getParameter("reception")) && request.getParameter("reception") != null) {
			reception = request.getParameter("reception").toString();
		}
		if (!"".equals(request.getParameter("content")) && request.getParameter("content") != null) {
			content = request.getParameter("content").toString();
		}
		if (!"".equals(request.getParameter("starttime")) && request.getParameter("starttime") != null) {
			starttime = request.getParameter("starttime").toString() + " 00:00:00";
		}
		if (!"".equals(request.getParameter("endtime")) && request.getParameter("endtime") != null) {
			endtime = request.getParameter("endtime").toString() + " 00:00:00";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("reception", reception);
		map.put("content", "%" + content + "%");
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		List<Message> messageList = messageService.getMessageList(map);
		if (messageList.size() > 0) {
			for (int i = 0; i < messageList.size(); i++) {
				if (messageList.get(i).getContent().length() > 10) {
					messageList.get(i).setContent(messageList.get(i).getContent().substring(0, 10) + "...");
				}
			}
		}
		System.out.println("=============messageList.size()=" + messageList.size());
		mv.addObject("messageList", messageList);
		mv.setViewName("/message/messageList");
		return mv;
	}

	@RequestMapping("/delmessage")
	public void delmessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setContentType("application/json");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		// 获取页面传入的数据
		String id = request.getParameter("id");
		// 封装对象数据
		int i = messageService.delMessaeById(Integer.parseInt(id));
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

	// 推送站内消息
	@RequestMapping("/messagepush")
	public ModelAndView messagepush(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String phonenumbers = "";
		String type = "2";
		String messagetype = "0";
		if (!"".equals(request.getParameter("phonenumbers")) && request.getParameter("phonenumbers") != null) {
			phonenumbers = request.getParameter("phonenumbers").toString();
		}
		if (!"".equals(request.getParameter("type")) && request.getParameter("type") != null) {
			type = request.getParameter("type").toString();
		}
		if (!"".equals(request.getParameter("messagetype")) && request.getParameter("messagetype") != null) {
			messagetype = request.getParameter("messagetype").toString();
		}
		if ("2".equals(type)) {

		} else if ("1".equals(type)) {
			String phone[] = phonenumbers.split(",");
			if (phone.length > 0) {
				for (int i = 0; i < phone.length; i++) {
					String phonenumber = phone[i].toString();
					UserInfo info = new UserInfo();
					info.setPhone(phonenumber);
					List<UserInfo> userInfoList = userService.selectByPhone(info);
					String cid = userInfoList.get(0).getCid();
					int usertype = userInfoList.get(0).getUsertype();
					String msg = null;
					String result = null;
					if ("4".equals(messagetype)) {
						msg = "欢迎使用顺心会" + userInfoList.get(0).getNickname();
					}
					if (usertype == 1) {
						HashMap<String, String> m = new HashMap<String, String>();
						HashMap<String, String> m1 = new HashMap<String, String>();
						HashMap<String, Object> map = new HashMap<String, Object>();
						m.put("deviceToken", cid);
						m1.put("alert", msg);
						Object jsonA = JSONObject.toJSON(m);
						Object jsonB = JSONObject.toJSON(m1);
						map.put("where", jsonA);
						map.put("data", jsonB);
						String json = JSON.toJSON(map).toString();
						result = MessageToIOS.MessageIOS(json);
					}
					if (usertype == 0) {
						HashMap<String, String> m = new HashMap<String, String>();
						HashMap<String, String> m1 = new HashMap<String, String>();
						HashMap<String, Object> map = new HashMap<String, Object>();
						m.put("installationId", cid);
						m1.put("alert", msg);
						Object jsonA = JSONObject.toJSON(m);
						Object jsonB = JSONObject.toJSON(m1);
						map.put("where", jsonA);
						map.put("data", jsonB);
						String json = JSON.toJSON(map).toString();
						result = MessageToAndroid.MessageAndroid(json);
					}
					// result = EntityUtils.toString(res.getEntity());//
					// 返回json格式：
					System.out.println("result=" + result);
					if ("{}".equals(result)) {
						// 如果发送成功就像message表中插入数据
						System.out.println("发送成功！");
						SmsTemplate smsTemplate = smsTemplateService
								.selectSmsTemplateById(Integer.parseInt(messagetype));
						String tempname = smsTemplate.getTempname();
						String tempcontent = smsTemplate.getTempcontent();
						Message message = new Message();
						message.setReception(phonenumber);
						message.setTitle(tempname);
						message.setContent(tempcontent);
						message.setSendtime(now);
						message.setMsgsorce(1);
						message.setIsread(0);
						message.setMsgtype(Integer.parseInt(messagetype));
						messageService.addMessage(message);
					}
				}
			}
		} else {
			// 发短信操作
			String phone[] = phonenumbers.split(",");
			if (phone.length > 0) {
				for (int i = 0; i < phone.length; i++) {
					String phonenumber = phone[i].toString();
					UserInfo info = new UserInfo();
					info.setPhone(phonenumber);
					List<UserInfo> userInfoList = userService.selectByPhone(info);
					int usertype = userInfoList.get(0).getUsertype();
					String msg = null;
					String result = null;
					HashMap<String, String> m = new HashMap<String, String>();
					if ("4".equals(messagetype)) {
						m.put("mobilePhoneNumber", phonenumber);
						m.put("template", "android_activity");
						m.put("date", "android_activity");
					}
					if (usertype == 1) {
						String json = JSON.toJSON(m).toString();
						result = SMSToIOS.SMSIOS(json);
					}
					if (usertype == 0) {
						String json = JSON.toJSON(m).toString();
						result = SMSToAndroid.SMSAndroid(json);
					}
					System.out.println("result=" + result);
					if ("{}".equals(result)) {
						// 如果发送成功就像message表中插入数据
						System.out.println("发送成功！");
						SmsTemplate smsTemplate = smsTemplateService
								.selectSmsTemplateById(Integer.parseInt(messagetype));
						String tempname = smsTemplate.getTempname();
						String tempcontent = smsTemplate.getTempcontent();
						Message message = new Message();
						message.setReception(phonenumber);
						message.setTitle(tempname);
						message.setContent(tempcontent);
						message.setSendtime(now);
						message.setMsgsorce(0);
						message.setIsread(0);
						message.setMsgtype(Integer.parseInt(messagetype));
						messageService.addMessage(message);
					}
				}
			}
		}

		ModelAndView mv = new ModelAndView();
		// TemplateVariables templateVariables = new TemplateVariables();
		// templateVariables.setTmpstatus(2);
		// List<TemplateVariables> templateVariableList =
		// templateVariablesService.getTemplateList(templateVariables);
		// System.out.println("=============templateVariableList.size()=" +
		// templateVariableList.size());
		// mv.addObject("templateVariableList", templateVariableList);
		mv.setViewName("/message/msgPush");
		return mv;
	}
}
