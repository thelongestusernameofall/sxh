<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../favicon.ico" rel="Shortcut Icon">
<title>顺心会管理后台</title>
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
<
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="../js/app.js"></script>
</head>

<body>
	<jsp:include page="top.jsp" />
	<!--<div id="body" class="body page_index">-->
	<div id="body" class="body page_user">
		<div id="js_container_box" class="container_box cell_layout side_l">
			<!--菜单栏  -->
			<jsp:include page="menu.jsp" />
			<!--菜单栏  end-->
			<!--主界面   -->
			<div class="col_main">
				<!--导航区  -->
				<div class="main_hd">
					<h2>顺心会>会员管理>会员详情</h2>
					<div class="extra_info mini_tips icon_after">
						<a href="../member/showMemberList.do">返回</a><i
							class="icon_mini_tips document_link"></i>
					</div>
				</div>
				<!--导航区  end-->
				<!--页面主体 -->
				<div class="main_bd">
					<div class="inner_container_box side_r cell_layout">
						<div class="bd">
							<div class="table_wrp user_list">
								<table class="table" cellspacing="0">
									<tbody class="tbody" id="userGroups">
										<tr>
											<td class="table_cell">UID</td>
											<td class="table_cell">${userInfo.uid}</td>
										</tr>
										<tr>
											<td class="table_cell">昵称</td>
											<td class="table_cell">${userInfo.nickname}</td>
										</tr>
										<tr>
											<td class="table_cell">头像</td>
											<td class="table_cell"><div style="width:50px; height:50px; border-radius:50%; overflow:hidden;margin:0 auto;"><img width="50px" height="50px" src="${purl}" /></div></td>
										</tr>
										<tr>
											<td class="table_cell">用户标签</td>
											<td class="table_cell">${userInfo.tags}</td>
										</tr>
										<tr>
											<td class="table_cell">用户签名</td>
											<td class="table_cell">${userInfo.sign}</td>
										</tr>
										<tr>
											<td class="table_cell">绑定手机</td>
											<td class="table_cell">${userInfo.phone}</td>
										</tr>
										<!-- <tr>
											<td class="table_cell">所在地区</td>
											<td class="table_cell">北京 顺义区</td>
										</tr> -->
										<tr>
											<td class="table_cell">性别</td>
											<c:if test="${userInfo.sex==0}">
												<td class="table_cell">女</td>
											</c:if>
											<c:if test="${userInfo.sex==1}">
												<td class="table_cell">男</td>
											</c:if>
										</tr>
										<tr>
											<td class="table_cell">注册时间</td>
											<td class="table_cell">${userInfo.regtime}</td>
										</tr>
										<tr>
											<td class="table_cell">状态</td>
											<c:if test="${userInfo.ptatus==0}">
												<td class="table_cell">已禁用</td>
											</c:if>
											<c:if test="${userInfo.ptatus==1}">
												<td class="table_cell">已启用</td>
											</c:if>
										</tr>
										<tr>
											<td class="table_cell" rowspan="4">参与活动</td>
											<td class="table_cell"></td>
										</tr>
										<c:forEach items="${activityList}" var="activityList"
											varStatus="status">
											<tr>
												<td class="table_cell center">${activityList.activityname}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--主界面  end -->
		</div>
	</div>
</body>
</html>