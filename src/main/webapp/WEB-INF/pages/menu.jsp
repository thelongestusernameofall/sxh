<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	HttpSession sessions = request.getSession();
	String userrole = "";
	if (sessions.getAttribute("userrole") == null) {
		response.sendRedirect("../login.jsp");
	} else {
		userrole = sessions.getAttribute("userrole").toString();
		System.out.println("userrole=" + userrole);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>顺心会管理后台</title>
<link href="../favicon.ico" rel="Shortcut Icon">
<link rel="stylesheet" href="../css/base.css" />
<link rel="stylesheet" href="../css/layout_head.css" />
<link rel="stylesheet" href="../css/lib.css" />
<link rel="stylesheet" href="../css/page_index.css" />
<link rel="stylesheet" href="../css/pagination.css" />
<link rel="stylesheet" href="../css/page_user.css" />
<link rel="stylesheet" href="../css/mass_send.css" />
<link rel="stylesheet" href="../css/dropdown.css" />
<link rel="stylesheet" href="../css/processor_bar.css" />
<link rel="stylesheet" href="../css/setting_common.css" />
<link rel="stylesheet" href="../css/app.css" />
<link rel="stylesheet" href="../css/datepicker.css" />
<link rel="stylesheet" href="../css/vote_edit.css" />
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="../js/jquery.ui.core.js"></script>
<script type="text/javascript" src="../js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="../js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="../js/app.js"></script>
<body>
	<!--菜单栏  -->
	<div class="col_side">
		<div class="menu_box" id="menuBar">
			<dl class="menu no_extra">
				<dt class="menu_title clickable">
					<a href="../member/showMemberList.do"><i class="icon_menu"
						style="background: url(https://res.wx.qq.com/mpres/htmledition/images/icon/menu/icon_menu_function.png) no-repeat;"></i>会员管理</a>
				</dt>
				<dd class="menu_item ">
					<a data-id="10005" href="../usertag/showtags.do">用户标签管理</a>
				</dd>
				<!--<dd class="menu_plugins"><a class="btn_plugins_add" data-id="10025" href="https://mp.weixin.qq.com/cgi-bin/plugincenter?t=service/plugins&act=all&token=1029483892&lang=zh_CN">添加功能插件</a></dd>-->
			</dl>
			<dl class="menu">
				<dt class="menu_title clickable">
					<a href="../activity/showactivities.do"><i class="icon_menu"
						style="background: url(https://res.wx.qq.com/mpres/htmledition/images/icon/menu/icon_menu_wxpay_v2.png) no-repeat;"></i>活动管理</a>
				<dd class="menu_item ">
					<a data-id="10005" href="../activitytype/showacttype.do">活动类型管理</a>
				</dd>
				</dt>

			</dl>
			<dl class="menu ">
				<dt class="menu_title clickable">
					<a href="../message/smslist.do"><i class="icon_menu"
						style="background: url(https://res.wx.qq.com/mpres/htmledition/images/icon/menu/icon_menu_management.png) no-repeat;"></i>短信发送管理</a>
				</dt>
				<dd class="menu_item ">
					<a data-id="10012" href="../message/messagelist.do">站内信发送管理 </a>
				</dd>
				<dd class="menu_item ">
					<a data-id="10012" href="../template/showtemplates.do">模板变量管理 </a>
				</dd>
				<dd class="menu_item ">
					<a data-id="10013" href="../message/showsmstemples.do">短信模板管理 </a>
				</dd>
				<dd class="menu_item ">
					<a data-id="10014" href="../message/showmessagetemples.do">站内信模板管理
					</a>
				</dd>
				<dd class="menu_item ">
					<a data-id="10015" href="../message/msgpush.do">消息推送 </a>
				</dd>
			</dl>
			<dl class="menu ">
				<dt class="menu_title clickable">
					<a href="../suggestion/showsug.do"><i class="icon_menu"
						style="background: url(
							        https://res.wx.qq.com/mpres/htmledition/images/icon/menu/icon_menu_ad.png
							        ) no-repeat;">
					</i>意见反馈</a>
				</dt>
			</dl>
			<dl class="menu ">
				<dt class="menu_title clickable">
					<a href="../appversion/showAppversions.do"><i class="icon_menu"
						style="background: url(
							        https://res.wx.qq.com/mpres/htmledition/images/icon/menu/icon_menu_statistics.png
							        ) no-repeat;">
					</i>APP版本管理</a>
				</dt>
			</dl>
			<%if("1".equals(userrole)){ %>
			<dl class="menu ">
				<dt class="menu_title clickable">
					<a href="../roleinfo/showUserAuth.do"><i class="icon_menu"
						style="background: url(
							        https://res.wx.qq.com/mpres/htmledition/images/icon/menu/icon_menu_setup.png
							        ) no-repeat;">
					</i>权限管理</a>
				</dt>
				<dd class="menu_item ">
					<a data-id="10048" href="../roleinfo/showRoleInfo.do">角色管理</a>
				</dd>
			</dl>
			<%} %>
		</div>
	</div>
	<!--菜单栏  end-->
</body>
</html>