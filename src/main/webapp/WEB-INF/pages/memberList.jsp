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

	}

	//禁用启用方法的JS
	function del(id, name, status, op) {
		if (window.confirm('你确定要' + op + '此标签吗?')) {
			$.ajax({
				url : "../member/del.do",
				type : "post",
				dataType : "json",
				data : {
					userid : id,
					nickname : name,
					status : status
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						if (map.success == "禁用成功!") {
							alert(map.success);
							location.href = "../member/showMemberList.do";
						}
						if (map.success == "启用成功!") {
							alert(map.success);
							location.href = "../member/showMemberList.do";
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
	//禁用启用方法的JS
	function detail(id) {
		var temp = document.createElement("form");
		temp.action = "../member/getMemberDetail.do";
		temp.method = "post";
		temp.style.display = "none";
		var opt = document.createElement("input");
		opt.name = "userid";
		opt.value = id;
		temp.appendChild(opt);
		document.body.appendChild(temp);
		temp.submit();
		return temp;
	}
</script>
</head>

<body>
	<jsp:include page="top.jsp" />
	<div id="body" class="body page_user">
		<div id="js_container_box" class="container_box cell_layout side_l">
			<jsp:include page="menu.jsp" />
			<div class="col_main">
				<!--导航区  -->
				<div class="main_hd">
					<h2>顺心会>会员管理</h2>
				</div>
				<div class="main_bd">

					<div class="global_mod float_layout query_area">
						<div class="global_info">
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">昵称</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" id="searchnickname" name="searchnickname"
									value="" class="frm_input jsSearchInput" placeholder="请输入昵称">
								</span>
							</div>

							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">绑定手机</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" id="searchphone" name="searchphone" value=""
									class="frm_input jsSearchInput" placeholder="请输入手机号码">
								</span>
							</div>
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">性别</label>
								<div class="filter_content">
									<div id="js_sex" class="dropdown_menu">
										<a href="javascript:;"
											class="btn dropdown_switch jsDropdownBt"><label
											class="jsBtLabel">全部</label><i class="arrow"></i></a>
										<div class="dropdown_data_container jsDropdownList"
											style="display: none;">
											<ul class="dropdown_data_list" id="searchsex">
												<li class="dropdown_data_item " value="2"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="0" data-index="0"
													data-name="全部">全部</a></li>

												<li class="dropdown_data_item " value="1"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="1" data-index="1"
													data-name="男">男</a></li>

												<li class="dropdown_data_item " value="0"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" data-value="2" data-index="2"
													data-name="女">女</a></li>
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
											<ul class="dropdown_data_list" id="searchstatus"
												name="searstatus">
												<li class="dropdown_data_item " value="2"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" value="2" data-index="2"
													data-name="全部">全部</a></li>

												<li class="dropdown_data_item " value="1"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" value="1" data-index="1"
													data-name="">启用</a></li>

												<li class="dropdown_data_item " value="0"><a
													onclick="return false;" href="javascript:;"
													class="jsDropdownItem" value="0" data-index="0"
													data-name="">禁用</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="global_extra">
							<a href="javascript:search();" data-y="3"
								class="btn btn_primary btn_add query_btn" id="js_tag_add_btn"><i
								class="icon14_common add_white"></i>搜索</a>
						</div>
					</div>
					<div class="inner_container_box side_r cell_layout">
						<div class="bd">
							<div class="table_wrp user_list">
								<table class="table" cellspacing="0">
									<thead class="thead">
										<tr>
											<th class="table_cell user no_extra">序号</th>
											<th class="table_cell">UID</th>
											<th class="table_cell">昵称</th>
											<th class="table_cell">绑定手机</th>
											<th class="table_cell">性别</th>
											<th class="table_cell">注册时间</th>
											<th class="table_cell">状态</th>
											<th class="table_cell">操作</th>
										</tr>
									</thead>
									<tbody class="tbody" id="userGroups">
										<c:forEach items="${memberList}" var="memberList"
											varStatus="status">
											<tr>
												<td class="table_cell">${status.index +1}</td>
												<td class="table_cell">${memberList.uid}</td>
												<td class="table_cell">${memberList.nickname}</td>
												<td class="table_cell">${memberList.phone}</td>
												<c:if test="${memberList.sex==0}">
													<td class="table_cell">女</td>
												</c:if>
												<c:if test="${memberList.sex==1}">
													<td class="table_cell">男</td>
												</c:if>
												<td class="table_cell">${memberList.regtime}</td>
												<c:if test="${memberList.ptatus==0}">
													<td class="table_cell">禁用</td>
												</c:if>
												<c:if test="${memberList.ptatus==1}">
													<td class="table_cell">启用</td>
												</c:if>
												<td class="table_cell user_group_opr"><c:if
														test="${memberList.ptatus==0}">
														<a href="javascript:;"
															onclick="del('${memberList.userid}','${memberList.nickname}','1','启用')"
															class="mod_link js_tag_del_btn">启用</a>
													</c:if> <c:if test="${memberList.ptatus==1}">
														<a href="javascript:;"
															onclick="del('${memberList.userid}','${memberList.nickname}','0','禁用')"
															class="mod_link js_tag_del_btn">禁用</a>
													</c:if> <a class="mod_link js_tag_edit_btn" href="javascript:;"
													onclick="detail('${memberList.userid}')">详情</a></td>
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
	<script>
		//搜索的JS方法
		var nickname = "";
		var phone = "";
		var status = "";
		var sex = "";
		var ul = document.getElementById('searchstatus');
		var lis = ul.getElementsByTagName('li');
		for (var i = 0; i < lis.length; i++) {
			lis[i].onclick = function() {
				status = this.value;
			}
		}
		var ul1 = document.getElementById('searchsex');
		var lis1 = ul1.getElementsByTagName('li');
		for (var i = 0; i < lis1.length; i++) {
			lis1[i].onclick = function() {
				sex = this.value;
			}
		}

		function search() {
			nickname = document.getElementById("searchnickname").value;
			phone = document.getElementById("searchphone").value;
			var temp = document.createElement("form");
			temp.action = "../member/showMemberList.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "nickname";
			opt.value = nickname;
			temp.appendChild(opt);
			var opt1 = document.createElement("input");
			opt1.name = "phone";
			opt1.value = phone;
			temp.appendChild(opt1);
			var opt2 = document.createElement("input");
			opt2.name = "status";
			opt2.value = status;
			temp.appendChild(opt2);
			var opt3 = document.createElement("input");
			opt3.name = "sex";
			opt3.value = sex;
			temp.appendChild(opt3);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
		}
	</script>
</body>
</html>