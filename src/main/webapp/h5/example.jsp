<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sxh.service.*"%>
<%@page import="org.springframework.web.context.support.*"%>
<%@page import="org.springframework.context.*"%>
<%@page import="com.sxh.model.*"%>
<%
	String id = (String) request.getParameter("id");
	String userid = (String) request.getParameter("userid");
	System.out.print("==========================id=" + id + "userid=" + userid);
	//业务处理  
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	ActivityService activityService = (ActivityService) ctx.getBean("activityService");
	Activities activities = activityService.getActivityById(Integer.parseInt(id));
	System.out.println(activities.toString());
	UserService userService = (UserService) ctx.getBean("userService");
	UserInfo userinfo = userService.getUserById(activities.getUserid());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="style.css"></link>
<meta name="viewport"
	content="width=375,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script type="text/javascript" src="js/zepto_1.1.3.js"></script>

</head>
<body>
	<div class="title">
		<span><%=activities.getActivityname()%></span>
	</div>
	<div id="activity_detail" readonly="readonly"><%=activities.getActivitydetail()%></div>

	<div id="recommend">
		顺心会倾情推荐 (发布人:<%=userinfo.getNickname()%>)
	</div>
	<div id="activity_content">
		<div id="activity_detail">
			<div class="title">
				<span>活动细节</span>
			</div>
			<ul>
				<li><%=activities.getActivitystart()%><span>-</span><%=activities.getActivityend()%></li>
				<li><%=activities.getActivityadd()%></li>
				<li>活动人数限制<%=activities.getPeoplecount()%>人
				</li>
				<li><%=activities.getActivitycost()%>元/人</li>
			</ul>
		</div>

		<div id="activity_process">
			<div class="title">
				<span>活动流程</span>
			</div>
			<div>
				<%=activities.getActivityflow()%>
			</div>
		</div>
	</div>
	<div id="tip">
		<span id="beizhu">成功报名后，你将被邀请进入此活动群聊</span>
	</div>
	<div id="baoming"></div>
	<script>
		window.onload = function() {
			var imgScrs = "";
			var imgs = document.getElementsByTagName("img");
			for (var i = 0; i < imgs.length; i++) {
				//imgScrs+=imgs [i].src;
				//if(i+1<imgs.length)imgScrs+="|";
				var img = imgs[i];
				/** 
				 * 图片按比例自适应缩放 
				 * @param img {Element} 用户上传的图片 
				 * @param maxWidth {Number} 预览区域的最大宽度 
				 * @param maxHeight {Number} 预览区域的最大高度 
				 */
				resizeImg = function(img, maxWidth, maxHeight) {
					var w = img.width, h = img.height;
					// 当图片比预览区域小时不做任何改变 
					if (w < maxWidth && h < maxHeight)
						return;
					// 当实际图片比例大于预览区域宽高比例时 
					// 缩放图片宽度，反之缩放图片宽度 
					w / h > maxWidth / maxHeight ? img.width = maxWidth
							: img.height = maxHeight;
				};
				resizeImg(img, 300, 300);
			}

		}
	</script>
</body>
</html>
