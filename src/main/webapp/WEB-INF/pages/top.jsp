<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	HttpSession sessions = request.getSession();
	String nickname = "";
	if (sessions.getAttribute("nickname") == null || "".equals(sessions.getAttribute("nickname"))) {
		response.sendRedirect("../login.jsp");
	} else {
		nickname = sessions.getAttribute("nickname").toString();
		System.out.println("nickname=" + nickname);
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
<script type="text/javascript">
var nickname = "<%=nickname%>";
	if (nickname == "") {
		window.location.href = "../login.jsp";
	}
</script>
<body>
	<div class="head" id="header">
		<div class="head_box">
			<div class="inner wrp">
				<h1 class="logo">
					<a href="../login.jsp" title="微信公众平台"></a>
				</h1>
				<div class="account">
					<div class="account_meta account_meta_primary">
						欢迎<%=nickname%>
						登录顺心会后台
					</div>
					<div class="account_meta account_logout account_meta_primary">
						<a id="logout" href="../login.jsp">退出</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>