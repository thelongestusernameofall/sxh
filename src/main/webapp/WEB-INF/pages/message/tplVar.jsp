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
		if (len == 1) {
			$("#userGroups tr").show();
		}

	}
	//确定按钮的JS方法
	function queding() {
		var tmpid = document.getElementById("tmpid").value;

		if (tmpid == "") {
			var tmpkey = $("#tmpkey").val();
			if (tmpkey == "") {
				alert("请填写变量字段名称!");
				return false;
			} else if (tmpkey.length > 10) {
				alert("变量字段名称长度过长!");
				return false;
			}
			var tmpname = $("#tmpname").val();
			if (tmpname == "") {
				alert("请填写变量中文名称!");
				return false;
			} else if (tmpname.length > 10) {
				alert("变量中文名称长度过长!");
				return false;
			}
			var tmpstatus = $('input[name="tmpstatus"]:checked').val();
			$.ajax({
				url : "../template/addtemplate.do",
				type : "post",
				dataType : "json",
				data : {
					tmpkey : tmpkey,
					tmpname : tmpname,
					tmpstatus : tmpstatus
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						if (map.success == "添加成功!") {
							alert(map.success);
							location.href = "../template/showtemplates.do";
						}
					}
				},
				error : function() {
					alert("请求失败！");
				}
			});
		} else {
			var tmpid = $("#tmpid").val();
			var tmpkey = $("#tmpkey").val();
			var tmpname = $("#tmpname").val();
			if (tmpkey == "") {
				alert("请填写变量字段名称!");
				return false;
			} else if (tmpkey.length > 10) {
				alert("变量字段名称长度过长!");
				return false;
			}
			if (tmpname == "") {
				alert("请填写变量中文名称!");
				return false;
			} else if (tmpname.length > 10) {
				alert("变量中文名称长度过长!");
				return false;
			}
			var tmpstatus = $('input[name="tmpstatus"]:checked').val();
			$.ajax({
				url : "../template/updatetemplate.do",
				type : "post",
				dataType : "json",
				data : {
					tmpid : tmpid,
					tmpkey : tmpkey,
					tmpname : tmpname,
					tmpstatus : tmpstatus
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						if (map.success == "修改成功!") {
							alert(map.success);
							location.href = "../template/showtemplates.do";
						} else {
							alert("修改失败!");
						}
					}
				},
				error : function() {
					alert("请求失败！");
				}
			});
		}

	}
	//删除的JS
	function del(id) {
		if (window.confirm('你确定要删除此变量吗?')) {
			$.ajax({
				url : "../template/deltemplate.do",
				type : "post",
				dataType : "json",
				data : {
					tmpid : id,
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						if (map.success == "删除成功!") {
							alert(map.success);
							location.href = "../template/showtemplates.do";
						}
					}
				},
				error : function() {
					alert("请求失败！");
				}
			});
		} else {
			return false;
		}
	}
	//编辑的JS方法
	function bianji(id, key, name, status) {
		document.getElementById("tmpid").value = id;
		document.getElementById("tmpkey").value = key;
		document.getElementById("tmpname").value = name;
		$("input[name='tmpstatus'][value='" + status + "']").attr("checked",
				true);
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
					<h2>顺心会>消息管理>模板变量管理</h2>
				</div>
				<!--导航区  end-->
				<!--页面主体 -->
				<div class="main_bd">

					<div class="global_mod  float_layout query_area">
						<div class="global_info">
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">关键词</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" value="" id="searchtmpname"
									name="searchtmpname" class="frm_input jsSearchInput"
									placeholder="请输入关键词">
								</span>
							</div>

							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">状态</label>
								<div class="filter_content">
									<div id="js_sex" class="dropdown_menu">
										<a href="javascript:;"
											class="btn dropdown_switch jsDropdownBt"><label
											class="jsBtLabel">全部</label><i class="arrow"></i></a>
										<div class="dropdown_data_container jsDropdownList"
											style="display: none;">
											<ul class="dropdown_data_list" id="searstatus">
												<li class="dropdown_data_item " value="2"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="0" data-index="0"
													data-name="全部">全部</a></li>

												<li class="dropdown_data_item " value="1"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="1" data-index="1"
													data-name="已启用">已启用</a></li>

												<li class="dropdown_data_item " value="0"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="2" data-index="2"
													data-name="已禁用">已禁用</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="global_extra">
							<a href="javascript:search();" data-y="3"
								class="btn btn_primary btn_add query_btn" id="js_tag_add_btn"><i
								class="icon14_common add_white"></i>搜索</a> <a href="javascript:;"
								data-y="3" class="btn btn_primary btn_add query_btn add_btn"
								id="js_tag_add_btn"><i class="icon14_common add_white"></i>新增用户标签</a>
						</div>
					</div>
					<div class="inner_container_box side_r cell_layout">
						<div class="bd">
							<div class="table_wrp user_list">
								<table class="table" cellspacing="0">
									<thead class="thead">
										<tr>
											<th class="table_cell user no_extra">序号</th>
											<th class="table_cell">key</th>
											<th class="table_cell">名称</th>
											<th class="table_cell">状态</th>
											<th class="table_cell">操作</th>
										</tr>
									</thead>
									<tbody class="tbody" id="userGroups">
										<c:forEach items="${templateVariablesList}"
											var="templateVariablesList" varStatus="status">
											<tr>
												<td class="table_cell">${status.index +1}</td>
												<td class="table_cell">${templateVariablesList.tmpkey}</td>
												<td class="table_cell">${templateVariablesList.tmpname}</td>
												<c:if test="${templateVariablesList.tmpstatus==0}">
													<td class="table_cell">禁用</td>
												</c:if>
												<c:if test="${templateVariablesList.tmpstatus==1}">
													<td class="table_cell">启用</td>
												</c:if>
												<td class="table_cell user_group_opr"><a
													href="javascript:bianji('${templateVariablesList.id}','${templateVariablesList.tmpkey}','${templateVariablesList.tmpname}','${templateVariablesList.tmpstatus}');"
													class="mod_link js_tag_edit_btn">编辑</a>
													<%
															if ("1".equals(userrole)) {
														%>
													<a
													class="mod_link js_tag_del_btn"
													href="javascript:del('${templateVariablesList.id}')">删除</a><%}%></td>
											</tr>
										</c:forEach>
									</tbody>

								</table>

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

	<!--mask-->
	<div class="mask ui-draggable" style="display: none;">
		<iframe frameborder="0"
			style="filter: progid:DXImageTransform.Microsoft.Alpha(opacity:0); position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;"
			src="about:blank"></iframe>
	</div>
	<!--mask end-->

	<!--编辑框 -->
	<div
		class="dialog_wrp align_edge audio_dialog ui-draggable center_dialog"
		style="width: 460px; display: none;" id="edit_dialog">
		<div class="dialog">
			<div class="dialog_hd">
				<h3>
					<span id="dialog_action">新增</span>模板变量
				</h3>
				<a href="javascript:;" onclick="return false"
					class="icon16_opr closed pop_closed">关闭</a>
			</div>
			<div class="dialog_bd">
				<div class="audio_box">
					<div class="audio_box_bd audio_list_container" id="">
						<div class="account_setting_area" id="settingArea">
							<div style="display: none">
								<input type="hidden" id="tmpid" name="tmpid" value="" />
							</div>
							<ul>
								<li class="account_setting_item wrp_pic_item_spe2">
									<div class="center" id="searchBar">
										<label class="frm_input_box label">key</label> <span
											class="frm_input_box search"> <a
											class="del_btn jsSearchInputClose" href="javascript:"
											style="display: none"> <i class="icon_search_del"></i>&nbsp;
										</a> <input type="text" value="" id="tmpkey" name="tmpkey"
											class="frm_input jsSearchInput" placeholder="字段命名">
										</span>
									</div>
								</li>
								<li class="account_setting_item wrp_pic_item_spe2">
									<div class="center" id="searchBar">
										<label class="frm_input_box label">名称</label> <span
											class="frm_input_box search"> <a
											class="del_btn jsSearchInputClose" href="javascript:"
											style="display: none"> <i class="icon_search_del"></i>&nbsp;
										</a> <input type="text" value="" id="tmpname" name="tmpname"
											class="frm_input jsSearchInput" placeholder="模板变量中文名称">
										</span>
									</div>
								</li>
								<li class="account_setting_item wrp_pic_item_spe2">
									<div class="center" id="searchBar">
										<label class="frm_input_box label">是否启用</label> <span
											class="frm_input_box search" style="width: 140px;"> <a
											class="del_btn jsSearchInputClose" href="javascript:"
											style="display: none"> <i class="icon_search_del"></i>&nbsp;
										</a> <input type="radio" name="tmpstatus" checked value="1" />启用
											<input type="radio" name="tmpstatus" value="0" />禁用
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
						type="button" class="js_btn close_dialog_btn" data-index="0"
						onclick="queding()">确定</button></span> <span
					class="btn btn_default btn_input js_btn_p"><button
						type="button" class="js_btn close_dialog_btn" data-index="1">取消</button></span>

			</div>

		</div>
	</div>
	<!--编辑框 end-->
	<script>
		//搜索的JS方法
		var status = "";
		var tmpname = "";
		var ul = document.getElementById('searstatus');
		var lis = ul.getElementsByTagName('li');
		for (var i = 0; i < lis.length; i++) {
			lis[i].onclick = function() {
				status = this.value;
			}
		}

		function search() {
			tmpname = document.getElementById("searchtmpname").value;
			var temp = document.createElement("form");
			temp.action = "../template/showtemplates.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "templatename";
			opt.value = tmpname;
			temp.appendChild(opt);
			var opt1 = document.createElement("input");
			opt1.name = "templatestatus";
			opt1.value = status;
			temp.appendChild(opt1);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
		}
	</script>
</body>
</html>