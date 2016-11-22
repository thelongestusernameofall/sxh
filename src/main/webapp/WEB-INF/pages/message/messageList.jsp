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
					<h2>顺心会>站内信发送管理</h2>
				</div>
				<!--导航区  end-->

				<!--页面主体 -->
				<div class="main_bd">

					<div class="global_mod user_global_opr float_layout query_area">
						<div class="global_info" style="margin-right: -100px;">
							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">接收手机</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" value="" id="searchphone" name="searchphone" class="frm_input jsSearchInput"
									placeholder="请输入手机号码">
								</span>
							</div>

							<div class="search_bar query_field" id="searchBar">
								<label class="frm_input_box label">内容</label> <span
									class="frm_input_box search"> <a
									class="del_btn jsSearchInputClose" href="javascript:"
									style="display: none"> <i class="icon_search_del"></i>&nbsp;
								</a> <input type="text" value="" id="searchcontent" name="searchcontent" class="frm_input jsSearchInput"
									placeholder="请输入关键词">
								</span>
							</div>

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

						<div class="global_extra">
							<a href="javascript:search();" data-y="3"
								class="btn btn_primary btn_add query_btn" id="js_tag_add_btn"><i
								class="icon14_common add_white"></i>搜索</a>
						</div>
					</div>

					<div class="inner_container_box side_r cell_layout">
						<div class="bd">
							<!-- <div class="global_mod user_group_opr">
				                    <span class="group_name" id="js_groupName">全部用户</span>
				                    <span class="js_groupCommand">
				                        <a href="javascript:;" class="mod_link js_tag_edit_btn">重命名</a><a class="mod_link js_tag_del_btn" href="javascript:;">删除</a>
				                    </span>
				                </div>-->

							<div class="table_wrp user_list">
								<div style="overflow-x: scroll; padding-bottom: 20px;">
									<table class="table" cellspacing="0" style="width: 125%;">
										<thead class="thead">
											<tr>
												<th class="table_cell user no_extra">序号</th>
												<th class="table_cell">接收用户</th>
												<th class="table_cell">站内信标题</th>
												<th class="table_cell">站内信内容</th>
												<th class="table_cell">发送时间</th>
												<th class="table_cell">消息类型</th>
												<th class="table_cell">是否已读</th>
												<th class="table_cell">操作</th>
											</tr>
										</thead>
										<tbody class="tbody" id="userGroups">
											<c:forEach items="${messageList}" var="messageList"
												varStatus="status">
												<tr>
													<td class="table_cell">${status.index +1}</td>
													<td class="table_cell">${messageList.reception}</td>
													<td class="table_cell">${messageList.title}</td>
													<td class="table_cell">${messageList.content}</td>
													<td class="table_cell">${messageList.sendtime}</td>
													<td class="table_cell">系统短信</td>
													<td class="table_cell">未读</td>
													<td class="table_cell user_group_opr">
													<%
															if ("1".equals(userrole)) {
														%>
													<a
														href="javascript:del('${messageList.id}');" class="mod_link js_tag_edit_btn">删除</a>
														<%}%>
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
		function search() {
			var reception = document.getElementById("searchphone").value;
			var content = document.getElementById("searchcontent").value;
			var fromdate = document.getElementById("fromDate").value;
			var todate = document.getElementById("toDate").value;
			var temp = document.createElement("form");
			temp.action = "../message/messagelist.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "reception";
			opt.value = reception;
			temp.appendChild(opt);
			var opt1 = document.createElement("input");
			opt1.name = "content";
			opt1.value = content;
			temp.appendChild(opt1);
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
		//删除的JS
		function del(id) {
			if (window.confirm('你确定要删除此条信息吗?')) {
				$.ajax({
					url : "../message/delmessage.do",
					type : "post",
					dataType : "json",
					data : {
						id : id,
					},
					success : function(data) {
						var map = eval(data);
						if (map != null) {
							if (map.success == "删除成功!") {
								alert(map.success);
								location.href = "../message/messagelist.do";
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
</body>

</html>