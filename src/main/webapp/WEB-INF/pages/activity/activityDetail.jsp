<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
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
<link rel="stylesheet" href="../css/datepicker.css" />
<link rel="stylesheet" href="../css/vote_edit.css" />
<link rel="stylesheet" href="../css/webuploader.css" />
<link rel="stylesheet" href="../css/app.css" />
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>

</head>
<body>
	<jsp:include page="../top.jsp" />
	<!--<div id="body" class="body page_index">-->
	<div id="body" class="body page_user">
		<div id="js_container_box" class="container_box cell_layout side_l">
			<!--菜单栏  -->
			<jsp:include page="../menu.jsp" />
			<!--菜单栏  end-->
			<!--主界面   -->
			<div class="col_main">
				<!--导航区  -->
				<div class="main_hd">
					<h2>顺心会>活动管理>活动详情</h2>
					<div class="extra_info mini_tips icon_after">
						<a href="javascript:history.go(-1)">返回</a><i
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
											<td class="table_cell">活动编号</td>
											<td class="table_cell">${activities.activitynum}</td>
										</tr>
										<tr>
											<td class="table_cell">活动名称</td>
											<td class="table_cell">${activities.activityname}</td>
										</tr>
										<tr>
											<td class="table_cell">活动封面图</td>
											<td class="table_cell"><img src="${purl}" /></td>
										</tr>
										<tr>
											<td class="table_cell">活动详情</td>
											<td class="table_cell"><div name="editor" id="editor"
													readonly="readonly">${activities.activitydetail}</div></td>
										</tr>
										<tr>
											<td class="table_cell">活动时间</td>
											<td class="table_cell">${activities.activitystart}<span>-</span>${activities.activityend}</td>
										</tr>
										<tr>
											<td class="table_cell">活动地点</td>
											<td class="table_cell">${activities.activityadd}</td>
										</tr>
										<tr>
											<td class="table_cell">限制人数</td>
											<td class="table_cell">${activities.peoplecount}人</td>
										</tr>
										<tr>
											<td class="table_cell">已报名人数</td>
											<td class="table_cell"><div class="half_cell">${activities.entercount}人</div>
												<div class="half_cell">
													<a onclick="showMembers();" style="cursor: pointer;">查看全部报名人员</a>
													<a href="../message/msgPush.html">群发通知</a>
												</div></td>
										</tr>
										<tr>
											<td class="table_cell">活动费用</td>
											<td class="table_cell">${activities.activitycost}元／人</td>
										</tr>
										<tr>
											<td class="table_cell">活动流程</td>
											<td class="table_cell center"><div name="editor1"
													id="editor1" readonly="readonly">${activities.activityflow}
												</div></td>
										</tr>
										<tr>
											<td class="table_cell">发布人</td>
											<td class="table_cell">${userInfo.nickname}</td>
										</tr>
										<tr>
											<td class="table_cell">发布时间</td>
											<td class="table_cell center">${activities.createtime}</td>
										</tr>
										<tr>
											<td class="table_cell">活动状态</td>
											<c:if test="${activities.activitystatus==0}">
												<td class="table_cell center">已发布</td>
											</c:if>
											<c:if test="${activities.activitystatus==1}">
												<td class="table_cell center">已完成</td>
											</c:if>
											<c:if test="${activities.activitystatus==2}">
												<td class="table_cell center">已关闭</td>
											</c:if>
										</tr>
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
	<!--编辑框 -->
	<div
		class="dialog_wrp align_edge audio_dialog ui-draggable center_dialog"
		style="width: 460px; display: none;" id="edit_dialog">
		<div class="dialog">
			<div class="dialog_hd">
				<h3>
					<span id="dialog_action"></span>
				</h3>
				<a href="javascript:;" onclick="return false"
					class="icon16_opr closed pop_closed">关闭</a>
			</div>
			<div class="dialog_bd">
				<div class="audio_box">
					<div class="audio_box_bd audio_list_container" id="">
						<div class="account_setting_area" id="settingArea"
							style="padding: 30px;">
							<div class="table_wrp user_list inner_container_box">
								<table class="table" cellspacing="0">
									<tbody class="tbody">
										<tr>
											<td class="table_cell">序号</td>
											<td class="table_cell">昵称</td>
											<td class="table_cell">手机号</td>
										</tr>
										<c:forEach items="${userInfoList}" var="userInfoList"
											varStatus="status">
											<tr>
												<td class="table_cell">${status.index +1}</td>
												<td class="table_cell">${userInfoList.nickname}</td>
												<td class="table_cell">${userInfoList.phone}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="dialog_ft">

				<span class="btn btn_primary btn_input js_btn_p"><button
						type="button" class="js_btn close_dialog_btn" data-index="0">确定</button></span>
			</div>
		</div>
	</div>
	<!--编辑框 end-->
	<script type="text/javascript" src="../js/app.js"></script>
	<script type="text/javascript">
		function showMembers() {
			popup("#edit_dialog");
		}
	</script>
</body>
</html>