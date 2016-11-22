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
<link rel="stylesheet" href="../css/dropdown.css" />
<link rel="stylesheet" href="../css/page_index.css" />
<link rel="stylesheet" href="../css/pagination.css" />
<link rel="stylesheet" href="../css/page_user.css" />
<link rel="stylesheet" href="../css/mass_send.css" />
<link rel="stylesheet" href="../css/processor_bar.css" />
<link rel="stylesheet" href="../css/setting_common.css" />
<link rel="stylesheet" href="../css/app.css" />
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="../js/app.js"></script>
<script type="text/javascript">
	function queding() {
		var tempid = $("#tempid").val();
		var tempname = $("#tempname").val();
		if (tempname == "") {
			alert("请填写模板标题!");
			return false;
		} else if (tempname.length > 20) {
			alert("模板标题过长!");
			return false;
		}
		var tempcontent = $("#tempcontent").val();
		if (tempcontent == "") {
			alert("请填写模板内容!");
			return false;
		} else if (tempcontent.length > 200) {
			alert("模板内容长度过长!");
			return false;
		}
		var tempremark = $("#tempremark").val();
		if (tempremark == "") {
			alert("请填写模板备注!");
			return false;
		} else if (tempremark.length > 200) {
			alert("模板备注长度过长!");
			return false;
		}
		var temp = document.createElement("form");
		temp.action = "../message/saveupdatemessahetemple.do";
		temp.method = "post";
		temp.style.display = "none";
		var opt = document.createElement("input");
		opt.name = "tempname";
		opt.value = tempname;
		temp.appendChild(opt);
		var opt1 = document.createElement("input");
		opt1.name = "tempcontent";
		opt1.value = tempcontent;
		temp.appendChild(opt1);
		var opt2 = document.createElement("input");
		opt2.name = "tempremark";
		opt2.value = tempremark;
		temp.appendChild(opt2);
		var opt3 = document.createElement("input");
		opt3.name = "tempid";
		opt3.value = tempid;
		temp.appendChild(opt3);
		document.body.appendChild(temp);
		temp.submit();
		return temp;

	}
</script>
</head>
<body>
	<jsp:include page="../top.jsp" />
	<div id="body" class="body page_user">
		<div id="js_container_box" class="container_box cell_layout side_l">
			<!--菜单栏  -->
			<jsp:include page="../menu.jsp" />
			<!--菜单栏  end-->
			<!--主界面   -->
			<div class="col_main">
				<!--导航区  -->
				<div class="main_hd">
					<h2>顺心会>会员管理>站内信模板管理>新增模板</h2>
					<div class="extra_info mini_tips icon_after">
						<a href="history.go(-1)">返回</a><i
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
									<div style="display: none">
										<input type="hidden" id="tempid" name="tempid"
											value="${smsTemplate.id}" />
									</div>
									<tbody class="tbody" id="userGroups">
										<tr>
											<td class="table_cell">模板标题</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search"> <input
														type="text" value="${smsTemplate.tempname}" id="tempname"
														name="tempname" class="frm_input jsSearchInput"
														placeholder="请输入模板标题">
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">模板内容</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search textarea"> <textarea
															id="tempcontent" name="tempcontent"
															class="frm_input jsSearchInput"
															placeholder="请输入您要设置的短信模板,例如：欢迎注册顺心会，验证码为｛验证码｝">${smsTemplate.tempcontent}</textarea>
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">变量选择</td>
											<td class="table_cell center">
												<div class="space"></div>
												<ul class="var_list">
													<c:forEach items="${templateVariableList}"
														var="templateVariableList" varStatus="status">
														<li><a href="javascript:void();">｛${templateVariableList.tmpname}｝</a></li>
													</c:forEach>
												</ul>
												<div class="space"></div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">模板备注</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search textarea"> <textarea
															id="tempremark" name="tempremark"
															class="frm_input jsSearchInput" placeholder=" 请输入备注内容">${smsTemplate.tempremark}</textarea>
													</span>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								<div class="tool_bar tc">
									<a href="javascript:history.go(-1);" class="btn btn_warn js_complete_bnt">取消</a>
									<a href="javascript:queding();"
										class="btn btn_primary js_complete_bnt">提交</a>
								</div>
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