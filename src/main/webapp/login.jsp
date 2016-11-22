<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<meta name="baidu-site-verification" content="DLH0DxvBfi">
<title>顺心会管理后台</title>
<link href="favicon.ico" rel="Shortcut Icon">
<link rel="stylesheet" href="css/page_login.css">
<script type="application/javascript" src="js/jquery-1.11.0.js"></script>
<script type="text/javascript">
	function login() {
		var username = $("#account").val();
		var password = $("#pwd").val();
		if (username == "") {
			alert("请填写账号!");
			return false;
		}
		if (password == "密码") {
			alert("请填写密码!");
			return false;
		}
		$.ajax({
			url : "../sxh/login/login.do",
			type : "post",
			dataType : "json",
			data : {
				username : username,
				password : password
			},
			success : function(data) {
				var map = eval(data);
				if (map != null) {
					if (map.success == "登录成功!") {
						location.href = "../sxh/login/index.do";
					} else {
						alert(map.success);
					}
				}
			},
			error : function() {
				alert("请求失败！");
			}
		});
	}
	function keyLogin(){
		  if (event.keyCode==13)   //回车键的键值为13
			  login();  //调用登录按钮的登录事件
		}
</script>
</head>
<body class="zh_CN" onkeydown="keyLogin();">
	<div class="head" id="header">
		<div class="head_box">
			<div class="inner wrp">
				<h1 class="logo">
					<a href="" title="微信公众平台">顺心会管理后台</a>
				</h1>
			</div>
		</div>
		<div class="banner">
			<div class="inner wrp">
				<div class="login_frame">
					<h3>登录</h3>
					<div class="login_err_panel" style="display: none;" id="err">
					</div>
					<form class="login_form" id="loginForm">
						<div class="login_input_panel" id="js_mainContent">
							<div class="login_input">
								<i class="icon_login un"> </i> <input type="text"
									placeholder="手机号/用户名" id="account" name="account">
							</div>
							<div class="login_input">
								<i class="icon_login pwd"> </i> <input type="password"
									placeholder="密码" id="pwd" name="password">
							</div>
						</div>
						<div class="verifycode" style="display: none;" id="verifyDiv">
							<span class="frm_input_box"> <input class="frm_input"
								type="text" id="verify" name="verify">
							</span> <img id="verifyImg" src=""> <a href="javascript:;"
								id="verifyChange">换一张</a>
						</div>
						<div class="login_help_panel">
							<label class="frm_checkbox_label selected" for="rememberCheck">
								<!--<i class="icon_checkbox"></i>--> <input type="checkbox"
								class="frm_checkbox" id="rememberCheck"> 记住帐号
							</label>
						</div>
						<div class="login_btn_panel">
							<a class="btn_login" title="点击登录" href="javascript:login();"
								id="loginBt">登录</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="body" class="body page_login"></div>
	<div class="foot" id="footer">
		<ul class="links ft">
			<li class="links_item no_extra"><a href="javascript:;"
				target="_blank">关于顺心会</a></li>
			<li class="links_item"><a href="javascript:;" target="_blank">服务协议</a></li>
			<li class="links_item"><a href="javascript:;" target="_blank">运营规范</a></li>
			<li class="links_item"><a href="javascript:;" target="_blank">客服中心</a></li>
			<li class="links_item"><a href="javascript:;" target="_blank">联系邮箱</a></li>
			<li class="links_item"><p class="copyright">Copyright © 2016
					顺心会. All Rights Reserved.</p></li>
		</ul>
	</div>
</body>

</html>
