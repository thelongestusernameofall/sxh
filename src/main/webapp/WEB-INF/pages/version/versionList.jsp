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
<link rel="stylesheet" href="../css/app.css" />
<script type="text/javascript">
	var pageSize = 10;//每页显示的记录条数
	var curPage = 0;
	var lastPage;
	var direct = 0;
	var len;
	var page;
	$(document).ready(
			function() {
				len = $("#userGroups tr").length;
				page = len % pageSize == 0 ? len / pageSize : Math.floor(len
						/ pageSize) + 1;//根据记录条数，计算页数
				document.getElementById("pages"), value = page;
				curPage = 1;
				displayPage(1);//显示第一页
				$("#btn1").click(function() {
					curPage = 1;
					displayPage();
				});
				$("#btn2").click(function() {
					direct = -1;
					displayPage();
				});
				$("#btn3").click(function() {
					direct = 1;
					displayPage();
				});
				$("#btn4").click(function() {
					curPage = page;
					displayPage();
				});
			});

	function displayPage() {
		if ((curPage <= 1 && direct == -1) || (curPage >= page && direct == 1)) {
			direct = 0;
			alert("已经是第一页或者最后一页了");
			return;
		}
		lastPage = curPage;
		curPage = (curPage + direct + len) % len;
		document.getElementById("currentpage"), value = curPage;
		var begin = (curPage - 1) * pageSize;//起始记录号
		var end = begin + pageSize;
		if (end > len)
			end = len;
		$("#userGroups tr").hide();
		$("#userGroups tr").each(function(i) {
			if (i >= begin && i < end)//显示第page页的记录
				$(this).show();
		});

	}
</script>
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
					<h2>顺心会>app版本发布</h2>
				</div>
				<!--导航区  end-->

				<!--页面主体 -->
				<div class="main_bd">
					<div class="global_mod user_global_opr float_layout query_area"
						style="padding-top: 20px; padding-bottom: 20px;">
						<div class="global_extra">
							<a href="javascript:;" data-y="3"
								class="btn btn_primary btn_add query_btn"
								id="update_version_btn"><i class="icon14_common add_white"></i>更新版本</a>
						</div>
					</div>
					<div class="inner_container_box side_r cell_layout">
						<div class="bd">
							<div class="table_wrp user_list">
								<div style="padding-bottom: 20px;">
									<table class="table" cellspacing="0" style="width: 100%;">
										<thead class="thead">
											<tr>
												<th class="table_cell user no_extra">序号</th>
												<th class="table_cell">版本号</th>
												<th class="table_cell">应用平台</th>
												<th class="table_cell">APK下载地址</th>
												<th class="table_cell">上传时间</th>
												<th class="table_cell">版本大小</th>
												<th class="table_cell">更新内容</th>
												<th class="table_cell">操作</th>
											</tr>
										</thead>
										<tbody class="tbody" id="userGroups">
											<c:forEach items="${appversionList}" var="appversionList"
												varStatus="status">
												<tr>
													<td class="table_cell">${status.index +1}</td>
													<td class="table_cell">${appversionList.versionnum}</td>
													<td class="table_cell">${appversionList.appplatform}</td>
													<td class="table_cell">${appversionList.appurl}</td>
													<td class="table_cell">${appversionList.uploadtime}</td>
													<td class="table_cell">${appversionList.appsize}</td>
													<td class="table_cell left">${appversionList.updatecontent}
													</td>
													<td class="table_cell user_group_opr"><a
														href="javascript:;" class="mod_link js_tag_edit_btn">编辑</a>
														<%
															if ("1".equals(userrole)) {
														%><a href="javascript:;" class="mod_link">删除</a>
														<%
															}
														%></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!--分页条   
								<div class="tool_area">
									<div class="pagination_wrp js_pageNavigator">
										<div class="pagination" id="wxPagebar_1462287141558">
											<span class="page_nav_area"> <a
												href="javascript:void(0);" class="btn page_first"
												style="display: none;"></a> <a href="javascript:void(0);"
												class="btn page_prev" style="display: none;"><i
													class="arrow"></i></a> <span class="page_num"> <label>1</label>
													<span class="num_gap">/</span> <label>2</label>
											</span> <a href="javascript:void(0);" class="btn page_next"><i
													class="arrow"></i></a> <a href="javascript:void(0);"
												class="btn page_last" style="display: none;"></a>
											</span> <span class="goto_area"> <input type="text">
												<a href="javascript:void(0);" class="btn page_go">跳转</a>
											</span>
										</div>
									</div>
								</div>
								分页条   end-->
							</div>
						</div>
					</div>
					<input type=button id="btn1" value="首页"> <input type=button
						id="btn2" value="上一页"> <input type=button id="btn3"
						value="下一页"> <input type=button id="btn4" value="尾页">
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
					<span id="dialog_action"></span>更新版本
				</h3>
				<a href="javascript:;" onclick="return false"
					class="icon16_opr closed pop_closed">关闭</a>
			</div>
			<div class="dialog_bd">
				<div class="audio_box">
					<div class="audio_box_bd audio_list_container" id="">
						<div class="account_setting_area" id="settingArea">
							<ul>
								<li class="account_setting_item wrp_pic_item_spe2">
									<div class="center" id="searchBar">
										<label class="frm_input_box label">版本号</label> <span
											class="frm_input_box search"> <a
											class="del_btn jsSearchInputClose" href="javascript:"
											style="display: none"> <i class="icon_search_del"></i>&nbsp;
										</a> <input type="text" value="" class="frm_input jsSearchInput"
											placeholder="请输入版本号">
										</span>
									</div>
								</li>
								<li class="account_setting_item wrp_pic_item_spe2">
									<div class="center" id="searchBar">
										<label class="frm_input_box label">APK下载地址</label> <span
											class="frm_input_box search"> <a
											class="del_btn jsSearchInputClose" href="javascript:"
											style="display: none"> <i class="icon_search_del"></i>&nbsp;
										</a> <input type="text" value="" class="frm_input jsSearchInput"
											placeholder="请输入下载地址">
										</span>
									</div>
								</li>
								<li class="account_setting_item wrp_pic_item_spe2">
									<div class="center" id="searchBar">
										<label class="frm_input_box label">版本大小</label> <span
											class="frm_input_box search"> <a
											class="del_btn jsSearchInputClose" href="javascript:"
											style="display: none"> <i class="icon_search_del"></i>&nbsp;
										</a> <input type="text" value="" class="frm_input jsSearchInput"
											placeholder="请输入下载地址">
										</span>
									</div>
								</li>
								<li class="account_setting_item wrp_pic_item_spe2">
									<div class="center" id="searchBar">
										<label class="frm_input_box label">更新内容</label> <span
											class="frm_input_box search"> <a
											class="del_btn jsSearchInputClose" href="javascript:"
											style="display: none"> <i class="icon_search_del"></i>&nbsp;
										</a> <textarea class="frm_input jsSearchInput"
												placeholder="请输入更新内容"></textarea>
										</span>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="dialog_ft">
				<span class="btn btn_primary btn_input js_btn_p"><button
						type="button" class="js_btn close_dialog_btn" data-index="0">确定</button></span>
				<span class="btn btn_default btn_input js_btn_p"><button
						type="button" class="js_btn close_dialog_btn" data-index="1">取消</button></span>
			</div>
		</div>
	</div>
	<!--编辑框 end-->
	<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
	<script type="text/javascript" src="../js/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery.ui.datepicker.js"></script>
	<script type="text/javascript"
		src="../js/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript" src="../js/app.js"></script>
	<script type="text/javascript">
		//			$("#fromDate").datepicker($.datepicker.regional[ "zh-CN" ]);
		$("#fromDate").datepicker({
			onClose : function(selectedDate) {
				$("#toDate").datepicker("option", "minDate", selectedDate);
			}
		});
		//			$("#toDate").datepicker($.datepicker.regional[ "zh-CN" ]);
		$("#toDate").datepicker({
			onClose : function(selectedDate) {
				$("#fromDate").datepicker("option", "maxDate", selectedDate);
			}
		});
		$("#fromDate").next("i").on('click', function() {
			$("#fromDate").datepicker("show");
		});
		$("#toDate").next("i").on('click', function() {
			$("#toDate").datepicker("show");
		});
	</script>
</body>
</html>