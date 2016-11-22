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
		//document.getElementById("currentpage"), value = curPage;
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
	//获取详情的JS方法
	function detail(id) {
		var temp = document.createElement("form");
		temp.action = "../suggestion/getSuggestionById.do";
		temp.method = "post";
		temp.style.display = "none";
		var opt = document.createElement("input");
		opt.name = "id";
		opt.value = id;
		temp.appendChild(opt);
		document.body.appendChild(temp);
		temp.submit();
		return temp;
	}
	//删除的JS方法
	function del(id) {
		if (window.confirm('确定要删除该条意见吗？')) {
			$.ajax({
				url : "../suggestion/delSug.do",
				type : "post",
				dataType : "json",
				data : {
					id : id,
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						alert(map.success);
						location.href = "../suggestion/showsug.do";
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
					<h2>顺心会>意见反馈</h2>
				</div>
				<!--导航区  end-->

				<!--页面主体 -->
				<div class="main_bd">

					<div class="global_mod  float_layout query_area">
						<div class="global_info">
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">反馈内容</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" id="sugcontent" value=""
									class="frm_input jsSearchInput" placeholder="请输入关键词">
								</span>
							</div>

							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">联系方式</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" id="serchcontact" value=""
									class="frm_input jsSearchInput" placeholder="请输入手机号">
								</span>
							</div>

							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">版本</label>
								<div class="filter_content">
									<div id="js_sex" class="dropdown_menu">
										<a href="javascript:;"
											class="btn dropdown_switch jsDropdownBt"><label
											class="jsBtLabel">全部</label><i class="arrow"></i></a>
										<div class="dropdown_data_container jsDropdownList"
											style="display: none;">
											<ul class="dropdown_data_list" id="searchappversion">
												<li class="dropdown_data_item "><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="4" data-index="0"
													data-name="全部">全部</a></li>
												<c:forEach items="${appversionList}" var="appversionList"
													varStatus="status">
													<li class="dropdown_data_item "
														value="${appversionList.versionnum}"><a
														onclick="return false;" href="javascript:;"
														class="jsDropdownItem" data-value="0" data-index="0"
														data-name="v1.0.0">${appversionList.versionnum}</a></li>
												</c:forEach>
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
											<ul class="dropdown_data_list" id="searchstatus">
												<li class="dropdown_data_item " value="4"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="0" data-index="0"
													data-name="已发布">全部</a></li>
												<li class="dropdown_data_item " value="0"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="0" data-index="0"
													data-name="已发布">未处理</a></li>

												<li class="dropdown_data_item " value="1"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="1" data-index="1"
													data-name="已完成">已回复</a></li>

												<li class="dropdown_data_item " value="2"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="2" data-index="2"
													data-name="已关闭">已解决</a></li>

												<li class="dropdown_data_item " value="3"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="2" data-index="2"
													data-name="已关闭">不予处理</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="global_info">
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">反馈时间</label>
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
								class="icon14_common add_white"></i>搜索</a>
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
												<th class="table_cell">意见反馈</th>
												<th class="table_cell">手机型号</th>
												<th class="table_cell">版本</th>
												<th class="table_cell">联系方式</th>
												<th class="table_cell">反馈时间</th>
												<th class="table_cell">回复内容</th>
												<th class="table_cell">状态</th>
												<th class="table_cell">操作</th>
											</tr>
										</thead>
										<tbody class="tbody" id="userGroups">
											<c:forEach items="${suggestionList}" var="suggestionList"
												varStatus="status">
												<tr>

													<td class="table_cell">${status.index +1}</td>
													<td class="table_cell">${suggestionList.sugcontent}</td>
													<td class="table_cell">${suggestionList.phonemodel}</td>
													<td class="table_cell">${suggestionList.version}</td>
													<td class="table_cell">${suggestionList.contract}</td>
													<td class="table_cell">${suggestionList.sugtime}</td>
													<td class="table_cell">${suggestionList.replycontent}</td>
													<!-- 回复状态(0,未处理1,已回复2,已解决3,不予处理) -->
													<c:if test="${suggestionList.replystatus==0}">
														<td class="table_cell">未处理</td>
													</c:if>
													<c:if test="${suggestionList.replystatus==1}">
														<td class="table_cell">已回复</td>
													</c:if>
													<c:if test="${suggestionList.replystatus==2}">
														<td class="table_cell">已解决</td>
													</c:if>
													<c:if test="${suggestionList.replystatus==3}">
														<td class="table_cell">不予处理</td>
													</c:if>
													<td class="table_cell user_group_opr">
														<%
															if ("1".equals(userrole)) {
														%> <a href="javascript:del(${suggestionList.id});"
														class="mod_link js_tag_edit_btn">删除</a>
														<%
															}
														%><a href="javascript:detail(${suggestionList.id});"
														class="mod_link js_tag_edit_btn">详情</a>
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

		//获取版本号
		var searchappversion = "0";
		var ul1 = document.getElementById('searchappversion');
		var lis1 = ul1.getElementsByTagName('li');
		for (var i = 0; i < lis1.length; i++) {
			lis1[i].onclick = function() {
				searchappversion = this.value;
			}
		}
		//获取回复的状态
		var searchstatus = "4";
		var ul2 = document.getElementById('searchstatus');
		var lis2 = ul2.getElementsByTagName('li');
		for (var i = 0; i < lis2.length; i++) {
			lis2[i].onclick = function() {
				searchstatus = this.value;
			}
		}

		function search() {
			var sugcontent = document.getElementById("sugcontent").value;
			var serchcontact = document.getElementById("serchcontact").value;
			var startdate = document.getElementById("fromDate").value;
			var enddate = document.getElementById("toDate").value;
			var temp = document.createElement("form");
			temp.action = "../suggestion/showsug.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "sugcontent";
			opt.value = sugcontent;
			temp.appendChild(opt);
			var opt1 = document.createElement("input");
			opt1.name = "contact";
			opt1.value = serchcontact;
			temp.appendChild(opt1);
			var opt2 = document.createElement("input");
			opt2.name = "version";
			opt2.value = searchappversion;
			temp.appendChild(opt2);
			var opt3 = document.createElement("input");
			opt3.name = "replystatus";
			opt3.value = searchstatus;
			temp.appendChild(opt3);
			var opt4 = document.createElement("input");
			opt4.name = "startdate";
			opt4.value = startdate;
			temp.appendChild(opt4);
			var opt5 = document.createElement("input");
			opt5.name = "enddate";
			opt5.value = enddate;
			temp.appendChild(opt5);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
		}
	</script>
</body>
</html>