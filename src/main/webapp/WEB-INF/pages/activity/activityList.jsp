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
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
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
	//删除的JS
	function delactivity(id) {
		if (window.confirm('你确定要删除此活动吗?')) {
			$.ajax({
				url : "../activity/delactivity.do",
				type : "post",
				dataType : "json",
				data : {
					activityid : id
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						if (map.success == "删除成功!") {
							alert(map.success);
							location.href = "../activity/showactivities.do";
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
					<h2>顺心会>活动管理</h2>
				</div>
				<!--导航区  end-->
				<!--页面主体 -->
				<div class="main_bd">
					<div class="global_mod float_layout query_area">
						<div class="global_info">
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">活动名称</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" value="" id="searchactivityname"
									name="searchactivityname" class="frm_input jsSearchInput"
									placeholder="请输入活动名称">
								</span>
							</div>
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">活动类型</label>
								<div class="filter_content">
									<div id="js_sex" class="dropdown_menu">
										<a href="javascript:;"
											class="btn dropdown_switch
										jsDropdownBt"><label
											class="jsBtLabel">全部</label><i class="arrow"></i></a>
										<div class="dropdown_data_container jsDropdownList"
											style="display: none;">
											<ul class="dropdown_data_list" id="searchactivitytype"
												name="activitytype">
												<c:forEach items="${activityTypeList}"
													var="activityTypeList" varStatus="status">
													<li class="dropdown_data_item "
														value="${activityTypeList.activitytypeid}"><a
														onclick="return false;" href="javascript:;"
														class="jsDropdownItem" data-value="2" data-index="2"
														data-name="${activityTypeList.activityname}">${activityTypeList.activityname}</a></li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">是否免费</label>
								<div class="filter_content">
									<div id="js_sex" class="dropdown_menu">
										<a href="javascript:;"
											class="btn dropdown_switch jsDropdownBt"><label
											class="jsBtLabel">全部</label><i class="arrow"></i></a>
										<div class="dropdown_data_container jsDropdownList"
											style="display: none;">
											<ul class="dropdown_data_list" id="searchactivitycost">
												<li class="dropdown_data_item " value="2"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="0" data-index="0"
													data-name="全部">全部</a></li>

												<li class="dropdown_data_item " value="1"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="1" data-index="1"
													data-name="收费">收费</a></li>

												<li class="dropdown_data_item " value="0"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="2" data-index="2"
													data-name="免费">免费</a></li>
											</ul>
										</div>
									</div>
								</div>
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
											<ul class="dropdown_data_list" id="searchactivitystatus">
												<li class="dropdown_data_item " value="4"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="0" data-index="0"
													data-name="全部">全部</a></li>

												<li class="dropdown_data_item " value="0"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="0" data-index="0"
													data-name="已发布">已发布</a></li>

												<li class="dropdown_data_item " value="1"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="1" data-index="1"
													data-name="已完成">已完成</a></li>

												<li class="dropdown_data_item " value="2"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="2" data-index="2"
													data-name="已关闭">已关闭</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="global_info">
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">发布时间</label>
								<div class="date_select timepicker frm_input_box search"
									style="display: inline;">
									<div class="datepicker_area">
										<span class="btn datepicker_switch"> <input type="text"
											class="frm_input valid" style="ime-mode: disabled"
											onpaste="return false" id="fromDate"> </input> <i
											class="icon_datepicker"></i>
										</span> - <span class="btn datepicker_switch"> <input
											type="text" class="frm_input valid"
											style="ime-mode: disabled" onpaste="return false" id="toDate">
											</input> <i class="icon_datepicker"></i>
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="global_extra center">
							<a href="javascript:search();" data-y="3"
								class="btn btn_primary btn_add query_btn" id="js_tag_add_btn"><i
								class="icon14_common add_white"></i>搜索</a> <a
								href="/sxh/activity/addactivities.do" data-y="3"
								class="btn btn_primary btn_add query_btn" id="js_tag_add_btn"><i
								class="icon14_common add_white"></i>新建</a>
						</div>
					</div>
					<div class="inner_container_box side_r cell_layout">
						<div class="bd">
							<div class="table_wrp user_list">
								<div style="overflow-x: scroll; padding-bottom: 20px;">
									<table class="table" cellspacing="0" style="width: 125%;">
										<thead class="thead">
											<tr>
												<th class="table_cell user no_extra">序号</th>
												<th class="table_cell">活动名称</th>
												<th class="table_cell">活动类型</th>
												<th class="table_cell">费用(元)</th>
												<th class="table_cell">限制人数</th>
												<th class="table_cell">已报名数</th>
												<th class="table_cell">发布时间</th>
												<th class="table_cell">状态</th>
												<th class="table_cell">操作</th>
											</tr>
										</thead>
										<tbody class="tbody" id="userGroups">
											<c:forEach items="${activitiesList}" var="activitiesList"
												varStatus="status">
												<tr>
													<td class="table_cell">${status.index +1}</td>
													<td class="table_cell">${activitiesList.activityname}</td>
													<td class="table_cell">${activitiesList.activityadd}</td>
													<c:if test="${activitiesList.activitycost==0}">
														<td class="table_cell">免费</td>
													</c:if>
													<c:if test="${activitiesList.activitycost!=0}">
														<td class="table_cell">${activitiesList.activitycost}</td>
													</c:if>
													<td class="table_cell">${activitiesList.peoplecount}</td>
													<td class="table_cell">${activitiesList.entercount}</td>
													<td class="table_cell">${activitiesList.createtime}</td>
													<c:if test="${activitiesList.activitystatus==0}">
														<td class="table_cell">已发布</td>
													</c:if>
													<c:if test="${activitiesList.activitystatus==1}">
														<td class="table_cell">已完成</td>
													</c:if>
													<c:if test="${activitiesList.activitystatus==2}">
														<td class="table_cell">已关闭</td>
													</c:if>
													<td class="table_cell user_group_opr">
														<%
															if ("1".equals(userrole)) {
														%> <a
														href="javascript:delactivity('${activitiesList.activityid}');"
														class="mod_link js_tag_edit_btn">删除</a> <%
 	}
 %> <a
														href="javascript:update('${activitiesList.activityid}');"
														class="mod_link js_tag_edit_btn">编辑</a><a
														class="mod_link js_tag_del_btn"
														href="javascript:detail('${activitiesList.activityid}');">详情</a>
													</td>
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
		function detail(activityid) {
			var temp = document.createElement("form");
			temp.action = "../activity/getActivityDetail.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "activityid";
			opt.value = activityid;
			temp.appendChild(opt);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
		}
		function update(activityid) {
			var temp = document.createElement("form");
			temp.action = "../activity/updateActivityDetail.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "activityid";
			opt.value = activityid;
			temp.appendChild(opt);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
		}
		//获取选中的活动类别
		var searchactivitytypeid = "0";
		var ul = document.getElementById('searchactivitytype');
		var lis = ul.getElementsByTagName('li');
		for (var i = 0; i < lis.length; i++) {
			lis[i].onclick = function() {
				searchactivitytypeid = this.value;
			}
		}
		//获取是否免费
		var searchactivitycost = "2";
		var ul1 = document.getElementById('searchactivitycost');
		var lis1 = ul1.getElementsByTagName('li');
		for (var i = 0; i < lis1.length; i++) {
			lis1[i].onclick = function() {
				searchactivitycost = this.value;
			}
		}
		//获取活动状态
		var searchactivitystatus = "4";
		var ul2 = document.getElementById('searchactivitystatus');
		var lis2 = ul2.getElementsByTagName('li');
		for (var i = 0; i < lis2.length; i++) {
			lis2[i].onclick = function() {
				searchactivitystatus = this.value;
			}
		}

		function search() {
			var activityname = document.getElementById("searchactivityname").value;
			var fromdate = document.getElementById("fromDate").value;
			var todate = document.getElementById("toDate").value;
			var temp = document.createElement("form");
			temp.action = "../activity/showactivities.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "activityname";
			opt.value = activityname;
			temp.appendChild(opt);
			var opt1 = document.createElement("input");
			opt1.name = "activitycost";
			opt1.value = searchactivitycost;
			temp.appendChild(opt1);
			var opt2 = document.createElement("input");
			opt2.name = "activitytypeid";
			opt2.value = searchactivitytypeid;
			temp.appendChild(opt2);
			var opt3 = document.createElement("input");
			opt3.name = "activitystatus";
			opt3.value = searchactivitystatus;
			temp.appendChild(opt3);
			var opt4 = document.createElement("input");
			opt4.name = "starttime";
			opt4.value = fromdate;
			temp.appendChild(opt4);
			var opt5 = document.createElement("input");
			opt5.name = "endtime";
			opt5.value = todate;
			temp.appendChild(opt5);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
		}
	</script>
</body>
</html>