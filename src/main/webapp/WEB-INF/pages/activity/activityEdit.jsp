<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.net.*"%>
<%
	HttpSession sessions = request.getSession();
	String userid = "";
	if (sessions.getAttribute("userid") == null || "".equals(sessions.getAttribute("userid"))) {
		response.sendRedirect("../login.jsp");
	} else {
		userid = sessions.getAttribute("userid").toString();
		System.out.println("userid=" + userid);
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
<link rel="stylesheet" href="../css/datepicker.css" />
<link rel="stylesheet" href="../css/vote_edit.css" />
<link rel="stylesheet" href="../css/webuploader.css" />
<link rel="stylesheet" href="../css/app.css" />
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="../js/app.js"></script>

<link rel="stylesheet" href="../js/Editor/themes/default/default.css" />
<script charset="utf-8" type="text/javascript"
	src="../js/Editor/kindeditor-min.js"></script>
<script charset="utf-8" type="text/javascript"
	src="../js/Editor/lang/zh_CN.js"></script>
<!-- 引入ueditor相关JS -->
<script type="text/javascript" charset="utf-8"
	src="../js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="../js/ueditor/ueditor.all.min.js">
	
</script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="../js/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
	//上传图片JS
	function yyzzupload() {
		var activityname = document.getElementById("activityname").value;
		if (activityname == "") {
			alert("请填写活动名称！");
			self.location.reload();
			return false;
		}
		$
				.ajaxFileUpload({
					url : encodeURI(encodeURI('../activity/uploadActivityPic.do?activityname='
							+ activityname + "&userid=" +
<%=userid%>
	)),//处理图片脚本
					data : {
						activityname : encodeURI(encodeURI(activityname))
					},
					async : false,
					secureuri : false,
					fileElementId : 'yyzzfile',//file控件id
					dataType : 'JSON',
					success : function(data, status) {
						if (data != '' || data != null) {
							fmchangeDel(data);
						}
					},
					error : function(data, status, e) {
						//alert(e);
					}
				});
	}

	function fmchangeDel(data) {
		var jso = delJson(data);
		var jsonobj = eval('(' + jso + ')');
		var error = jsonobj.error;
		if (error == "1") {
			var msg = jsonobj.message;
			alert(msg);
			return false;
		}
		jurl = jsonobj.url;
		jattid = jsonobj.attId;
		var attSrc = ".." + jurl;
		$("#yyzzpic").attr("src", attSrc);
		$("#attachid").attr("value", jattid);
		document.getElementById("yyzzsrc").value = attSrc;
		//document.getElementById("attachid").value = jattid;
	}
	function delJson(str) {
		var data = str;
		var start = data.indexOf(">");
		if (start != -1) {
			var end = data.indexOf("<", start + 1);
			if (end != -1) {
				data = data.substring(start + 1, end);
			}
		}
		return data;
	}
	//确定按钮的JS方法
	function queding() {
		var typeid = document.getElementById("typeid").value;

		if (typeid == "") {
			var typename = $("#typename").val();
			if (typename == "") {
				alert("请填写标签名称!");
				return false;
			} else if (typename.length > 10) {
				alert("标签名称长度过长!");
				return false;
			}
			var typestatus = $('input[name="typestatus"]:checked').val();
			$.ajax({
				url : "../activitytype/addActType.do",
				type : "post",
				dataType : "json",
				data : {
					typename : typename,
					typestatus : typestatus
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						if (map.success == "添加成功!") {
							alert(map.success);
							location.href = "../activitytype/showacttype.do";
						}
					}
				},
				error : function() {
					alert("请求失败！");
				}
			});
		} else {
			var typeid = $("#typeid").val();
			var typename = $("#typename").val();
			if (typename == "") {
				alert("请填写标签名称!");
				return false;
			} else if (typename.length > 10) {
				alert("标签名称长度过长!");
				return false;
			}
			var typestatus = $('input[name="typestatus"]:checked').val();
			$.ajax({
				url : "../activitytype/updateActType.do",
				type : "post",
				dataType : "json",
				data : {
					typeid : typeid,
					typename : typename,
					typestatus : typestatus
				},
				success : function(data) {
					var map = eval(data);
					if (map != null) {
						if (map.success == "修改成功!") {
							alert(map.success);
							location.href = "../activitytype/showacttype.do";
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

	//编辑的JS方法
	function bianji(id, name, status) {
		document.getElementById("typename").value = name;
		$("input[name='typestatus'][value='" + status + "']").attr("checked",
				true);
		document.getElementById("typeid").value = id;
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
					<h2>顺心会>活动管理>新建活动</h2>
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
											<td class="table_cell"><input type="text"
												id="activitynum" name="activitynum" readonly="readonly"
												value="HD20160415001" /></td>
										</tr>
										<tr>
											<td class="table_cell">活动类型</td>
											<td class="table_cell">
												<div class="" id="searchBar">
													<div class="filter_content">
														<div id="js_sex" class="dropdown_menu">
															<a href="javascript:;"
																class="btn dropdown_switch jsDropdownBt"><label
																class="jsBtLabel">全部</label><i class="arrow"></i></a>
															<div class="dropdown_data_container jsDropdownList"
																style="display: none;">
																<ul class="dropdown_data_list" id="activitytype"
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
											</td>
										</tr>
										<tr>
											<td class="table_cell">活动名称</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search"> <input
														type="text" value="" id="activityname" name="activityname"
														class="frm_input jsSearchInput"
														placeholder="请输入活动名称，不超过20个字">
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">活动封面图</td>
											<td class="table_cell"><span> <img id="yyzzpic"
													src="" alt="" width="600px" height="400px" /> <input
													id="attachid" name="attachid" type="hidden" />
													<div>
														<input class="upFileBtn" type="button" value="上传图片"
															onclick="document.getElementById('yyzzfile').click()" />

														<div style="display: none">
															<input type="file" name="upfile" id="yyzzfile"
																style="width: 57px; height: 18px; cursor: pointer; top: 0px; left: 0px; position: absolute;"
																class="hidden" onChange="yyzzupload()" />
														</div>
													</div>
											</span> <!--<div id="fileList" class="uploader-list"></div>--> <!--  <div id="uploader" class="wu-example">
													<div class="queueList">
														<div id="dndArea" class="placeholder">
															<div id="filePicker" class="webuploader-container">
																<div class="webuploader-pick">点击选择图片</div>
																<div id="rt_rt_1ai4ndh7r1g2v1tquudu11th6vq1"
																	style="position: absolute; top: 0px; left: 448px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;">
																	<input type="file" name="file"
																		class="webuploader-element-invisible"
																		multiple="multiple" accept="image/*"><label
																		style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
																</div>
															</div>
															<p>或将照片拖到这里，单次最多可选300张</p>
														</div>
														<ul class="filelist"></ul>
													</div>
													<div class="statusBar" style="display: none;">
														<div class="progress" style="display: none;">
															<span class="text">0%</span> <span class="percentage"
																style="width: 0%;"></span>
														</div>
														<div class="info">共0张（0B），已上传0张</div>
														<div class="btns">

															<div class="uploadBtn state-pedding">开始上传</div>
														</div>
													</div>
												</div>
											</td>-->
										</tr>

										<tr>
											<td class="table_cell">活动详情</td>
											<td class="table_cell"><script id="editor"
													type="text/plain" style="width:700px;height:500px;"></script></td>
										</tr>
										<tr>
											<td class="table_cell">活动时间</td>
											<td class="table_cell">
												<div class="date_select timepicker frm_input_box search"
													style="display: inline;">
													<div class="datepicker_area">
														<span class="btn datepicker_switch"> <input
															type="text" class="frm_input valid"
															style="ime-mode: disabled" onpaste="return false"
															id="fromDate"> </input> <i class="icon_datepicker"></i>
														</span>
														<div class="dropdown_menu time" id="jsVoteHour">
															<a href="javascript:;"
																class="btn dropdown_switch jsDropdownBt"><label
																class="jsBtLabel">00</label><i class="arrow"></i></a>
															<div class="dropdown_data_container jsDropdownList"
																style="display: none;">
																<ul class="dropdown_data_list" id="starthour">
																	<li class="dropdown_data_item " value="00"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="00" data-index="0"
																		data-name="00">00</a></li>

																	<li class="dropdown_data_item " value="01"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="01" data-index="1"
																		data-name="01">01</a></li>

																	<li class="dropdown_data_item " value="02"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="02" data-index="2"
																		data-name="02">02</a></li>

																	<li class="dropdown_data_item " value="03"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="03" data-index="3"
																		data-name="03">03</a></li>

																	<li class="dropdown_data_item " value="04"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="04" data-index="4"
																		data-name="04">04</a></li>

																	<li class="dropdown_data_item " value="05"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="05" data-index="5"
																		data-name="05">05</a></li>

																	<li class="dropdown_data_item " value="06"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="06" data-index="6"
																		data-name="06">06</a></li>

																	<li class="dropdown_data_item " value="07"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="07" data-index="7"
																		data-name="07">07</a></li>

																	<li class="dropdown_data_item " value="08"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="08" data-index="8"
																		data-name="08">08</a></li>

																	<li class="dropdown_data_item " value="09"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="09" data-index="9"
																		data-name="09">09</a></li>

																	<li class="dropdown_data_item " value="10"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="10" data-index="10"
																		data-name="10">10</a></li>

																	<li class="dropdown_data_item " value="11"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="11" data-index="11"
																		data-name="11">11</a></li>

																	<li class="dropdown_data_item " value="12"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="12" data-index="12"
																		data-name="12">12</a></li>

																	<li class="dropdown_data_item " value="13"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="13" data-index="13"
																		data-name="13">13</a></li>

																	<li class="dropdown_data_item " value="14"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="14" data-index="14"
																		data-name="14">14</a></li>

																	<li class="dropdown_data_item " value="15"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="15" data-index="15"
																		data-name="15">15</a></li>

																	<li class="dropdown_data_item " value="16"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="16" data-index="16"
																		data-name="16">16</a></li>

																	<li class="dropdown_data_item " value="17"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="17" data-index="17"
																		data-name="17">17</a></li>

																	<li class="dropdown_data_item " value="18"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="18" data-index="18"
																		data-name="18">18</a></li>

																	<li class="dropdown_data_item " value="19"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="19" data-index="19"
																		data-name="19">19</a></li>

																	<li class="dropdown_data_item " value="20"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="20" data-index="20"
																		data-name="20">20</a></li>

																	<li class="dropdown_data_item " value="21"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="21" data-index="21"
																		data-name="21">21</a></li>

																	<li class="dropdown_data_item " value="22"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="22" data-index="22"
																		data-name="22">22</a></li>

																	<li class="dropdown_data_item " value="23"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="23" data-index="23"
																		data-name="23">23</a></li>
																</ul>
															</div>
														</div>
														<span class="date_select_gap">时</span>
														<div class="dropdown_menu time" id="jsVoteMin">
															<a href="javascript:;"
																class="btn dropdown_switch jsDropdownBt"><label
																class="jsBtLabel">00</label><i class="arrow"></i></a>
															<div class="dropdown_data_container jsDropdownList"
																style="display: none;">
																<ul class="dropdown_data_list" id="startmin">
																	<li class="dropdown_data_item " value="00"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="00" data-index="0"
																		data-name="00">00</a></li>

																	<li class="dropdown_data_item " value="05"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="05" data-index="1"
																		data-name="05">05</a></li>

																	<li class="dropdown_data_item " value="10"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="10" data-index="2"
																		data-name="10">10</a></li>

																	<li class="dropdown_data_item " value="15"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="15" data-index="3"
																		data-name="15">15</a></li>

																	<li class="dropdown_data_item " value="20"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="20" data-index="4"
																		data-name="20">20</a></li>

																	<li class="dropdown_data_item " value="25"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="25" data-index="5"
																		data-name="25">25</a></li>

																	<li class="dropdown_data_item " value="30"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="30" data-index="6"
																		data-name="30">30</a></li>

																	<li class="dropdown_data_item " value="35"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="35" data-index="7"
																		data-name="35">35</a></li>

																	<li class="dropdown_data_item " value="40"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="40" data-index="8"
																		data-name="40">40</a></li>

																	<li class="dropdown_data_item " value="45"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="45" data-index="9"
																		data-name="45">45</a></li>

																	<li class="dropdown_data_item " value="50"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="50" data-index="10"
																		data-name="50">50</a></li>

																	<li class="dropdown_data_item " value="55"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="55" data-index="11"
																		data-name="55">55</a></li>
																</ul>
															</div>
														</div>
														<span class="date_select_gap">分</span> -
														<!--<div>-->
														<span class="btn datepicker_switch"> <input
															type="text" class="frm_input valid"
															style="ime-mode: disabled" onpaste="return false"
															id="toDate"> </input> <i class="icon_datepicker"></i>
														</span>
														<div class="dropdown_menu time" id="jsVoteHour">
															<a href="javascript:;"
																class="btn dropdown_switch jsDropdownBt"><label
																class="jsBtLabel">00</label><i class="arrow"></i></a>
															<div class="dropdown_data_container jsDropdownList"
																style="display: none;">
																<ul class="dropdown_data_list" id="endhour">
																	<li class="dropdown_data_item " value="00"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="00" data-index="0"
																		data-name="00">00</a></li>

																	<li class="dropdown_data_item " value="01"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="01" data-index="1"
																		data-name="01">01</a></li>

																	<li class="dropdown_data_item " value="02"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="02" data-index="2"
																		data-name="02">02</a></li>

																	<li class="dropdown_data_item " value="03"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="03" data-index="3"
																		data-name="03">03</a></li>

																	<li class="dropdown_data_item " value="04"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="04" data-index="4"
																		data-name="04">04</a></li>

																	<li class="dropdown_data_item " value="05"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="05" data-index="5"
																		data-name="05">05</a></li>

																	<li class="dropdown_data_item " value="06"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="06" data-index="6"
																		data-name="06">06</a></li>

																	<li class="dropdown_data_item " value="07"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="07" data-index="7"
																		data-name="07">07</a></li>

																	<li class="dropdown_data_item " value="08"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="08" data-index="8"
																		data-name="08">08</a></li>

																	<li class="dropdown_data_item " value="09"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="09" data-index="9"
																		data-name="09">09</a></li>

																	<li class="dropdown_data_item " value="10"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="10" data-index="10"
																		data-name="10">10</a></li>

																	<li class="dropdown_data_item " value="11"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="11" data-index="11"
																		data-name="11">11</a></li>

																	<li class="dropdown_data_item " value="12"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="12" data-index="12"
																		data-name="12">12</a></li>

																	<li class="dropdown_data_item " value="13"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="13" data-index="13"
																		data-name="13">13</a></li>

																	<li class="dropdown_data_item " value="14"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="14" data-index="14"
																		data-name="14">14</a></li>

																	<li class="dropdown_data_item " value="15"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="15" data-index="15"
																		data-name="15">15</a></li>

																	<li class="dropdown_data_item " value="16"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="16" data-index="16"
																		data-name="16">16</a></li>

																	<li class="dropdown_data_item " value="17"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="17" data-index="17"
																		data-name="17">17</a></li>

																	<li class="dropdown_data_item " value="18"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="18" data-index="18"
																		data-name="18">18</a></li>

																	<li class="dropdown_data_item " value="19"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="19" data-index="19"
																		data-name="19">19</a></li>

																	<li class="dropdown_data_item " value="20"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="20" data-index="20"
																		data-name="20">20</a></li>

																	<li class="dropdown_data_item " value="21"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="21" data-index="21"
																		data-name="21">21</a></li>

																	<li class="dropdown_data_item " value="22"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="22" data-index="22"
																		data-name="22">22</a></li>

																	<li class="dropdown_data_item " value="23"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="23" data-index="23"
																		data-name="23">23</a></li>
																</ul>
															</div>
														</div>
														<span class="date_select_gap">时</span>
														<div class="dropdown_menu time" id="jsVoteMin">
															<a href="javascript:;"
																class="btn dropdown_switch jsDropdownBt"><label
																class="jsBtLabel">00</label><i class="arrow"></i></a>
															<div class="dropdown_data_container jsDropdownList"
																style="display: none;">
																<ul class="dropdown_data_list" id="endmin">
																	<li class="dropdown_data_item " value="00"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="00" data-index="0"
																		data-name="00">00</a></li>

																	<li class="dropdown_data_item " value="05"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="05" data-index="1"
																		data-name="05">05</a></li>

																	<li class="dropdown_data_item " value="10"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="10" data-index="2"
																		data-name="10">10</a></li>

																	<li class="dropdown_data_item " value="15"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="15" data-index="3"
																		data-name="15">15</a></li>

																	<li class="dropdown_data_item " value="20"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="20" data-index="4"
																		data-name="20">20</a></li>

																	<li class="dropdown_data_item " value="25"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="25" data-index="5"
																		data-name="25">25</a></li>

																	<li class="dropdown_data_item " value="30"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="30" data-index="6"
																		data-name="30">30</a></li>

																	<li class="dropdown_data_item " value="35"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="35" data-index="7"
																		data-name="35">35</a></li>

																	<li class="dropdown_data_item " value="40"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="40" data-index="8"
																		data-name="40">40</a></li>

																	<li class="dropdown_data_item " value="45"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="45" data-index="9"
																		data-name="45">45</a></li>

																	<li class="dropdown_data_item " value="50"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="50" data-index="10"
																		data-name="50">50</a></li>
																	<li class="dropdown_data_item " value="55"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="55" data-index="11"
																		data-name="55">55</a></li>
																</ul>
															</div>
														</div>
														<span class="date_select_gap">分</span>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">活动地点</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search"> <input
														type="text" id="activityadd" name="activityadd" value=""
														class="frm_input jsSearchInput" placeholder="请输入活动地点">
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">限制人数</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box number"> <input
														type="text" id="activitycount" name="activitycount"
														value="" class="frm_input jsSearchInput" placeholder=""
														onkeyup="check();">
													</span> <label>人</label>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">活动费用</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box number"> <input
														type="text" value="" id="activitycost" name="activitycost"
														class="frm_input jsSearchInput" placeholder="">
													</span> <label>元/人</label>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">活动流程</td>
											<td class="table_cell left"><script id="editor1"
													type="text/plain" style="width:700px;height:500px;"></script></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="tool_bar tc">
					<a href="javascript:history.go(-1)"
						class="btn btn_warn js_complete_bnt">取消</a> <a
						href="javascript:save();" class="btn btn_primary js_complete_bnt">提交</a>
				</div>
			</div>
			<!--主界面  end -->
		</div>
	</div>
	<div class="pure-u-1" id="abc001"></div>
	<div class="pure-u-1" id="abc002"></div>
	<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="../js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/webuploader.nolog.js"></script>
	<script type="text/javascript" src="../js/imgupload.js"></script>
	<script type="text/javascript" src="../js/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery.ui.datepicker.js"></script>
	<script type="text/javascript"
		src="../js/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript" src="../js/app.js"></script>
	<script type="text/javascript">
		//CKEDITOR.replace('editor');
		//CKEDITOR.replace('editor1');
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue = UE.getEditor('editor');
		var ue1 = UE.getEditor('editor1');

		ue.addListener('beforeInsertImage', function(t, arg) {
			for ( var obj in arg) {
				console.log(arg[obj].src);
				var vhtml = '<br/><img  src="' + arg[obj].src
						+ '"  style="width:100%;"/>';
				//u1.execCommand( 'inserthtml', vhtml);
				$("#abc001").append(vhtml);
			}
			;
		});
		ue1.addListener('beforeInsertImage', function(t, arg) {
			for ( var obj in arg) {
				console.log(arg[obj].src);
				var vhtml = '<br/><img  src="' + arg[obj].src
						+ '"  style="width:100%;"/>';
				//u1.execCommand( 'inserthtml', vhtml);
				$("#abc002").append(vhtml);
			}
			;
		});

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
		//判断限制人数是整数
		function check() {
			if (isNaN(activitycount.value)) {
				alert("请输入整数！");
				activitycount.value = "";
			}
		}

		var starthour = "0";
		var ul = document.getElementById('starthour');
		var lis = ul.getElementsByTagName('li');
		for (var i = 0; i < lis.length; i++) {
			lis[i].onclick = function() {
				starthour = this.value;
			}
		}
		var startmin = "0";
		var ul1 = document.getElementById('startmin');
		var lis1 = ul1.getElementsByTagName('li');
		for (var i = 0; i < lis1.length; i++) {
			lis1[i].onclick = function() {
				startmin = this.value;
			}
		}
		var endhour = "0";
		var ul2 = document.getElementById('endhour');
		var lis2 = ul2.getElementsByTagName('li');
		for (var i = 0; i < lis2.length; i++) {
			lis2[i].onclick = function() {
				endhour = this.value;
			}
		}
		var endmin = "0";
		var ul3 = document.getElementById('endmin');
		var lis3 = ul3.getElementsByTagName('li');
		for (var i = 0; i < lis3.length; i++) {
			lis3[i].onclick = function() {
				endmin = this.value;
			}
		}
		var activitytypeid = "0";
		var ul4 = document.getElementById('activitytype');
		var lis4 = ul4.getElementsByTagName('li');
		for (var i = 0; i < lis4.length; i++) {
			lis4[i].onclick = function() {
				activitytypeid = this.value;
			}
		}
		//提交活动
		function save() {
			if (activitytypeid == 0) {
				alert("请选择活动类型!");
				return false;
			}
			var activitydetail = UE.getEditor('editor').getContent();
			if (activitydetail == "") {
				alert("请填写活动详情!");
				return false;
			}
			var activityprocess = UE.getEditor('editor1').getContent();
			if (activityprocess == "") {
				alert("请填写活动流程!");
				return false;
			}
			var attachid = $("#attachid").val();
			if (attachid == "") {
				alert("请上传活动图片!");
				return false;
			}
			var fromDate = $("#fromDate").val();
			if (fromDate == "") {
				alert("请填写活动开始时间!");
				return false;
			}
			var toDate = $("#toDate").val();
			if (toDate == "") {
				alert("请填写活动结束时间!");
				return false;
			}
			var activityadd = $("#activityadd").val();
			if (activityadd == "") {
				alert("请填写活动地点!");
				return false;
			}
			var activitycount = $("#activitycount").val();
			if (activitycount == "") {
				alert("请填写限制人数!");
				return false;
			}
			var activitycost = $("#activitycost").val();
			if (activitycost == "") {
				alert("请填写活动费用，免费请填0!");
				return false;
			} else {
				var p = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
				if (!p.test(activitycost)) {
					alert("请输入正确的金额,小数只能是两位!");
					return false;
				}
			}
			var activitynum = $("#activitynum").val();
			var activityname = $("#activityname").val();
			if (activityname.length > 20) {
				alert("活动名称输入过长!");
				return false;
			}
			var starthours = starthour.toString();
			if (starthours.length == 1) {
				starthours = "0" + starthours
			}
			var startmins = startmin.toString();
			if (startmins.length == 1) {
				startmins = "0" + startmins
			}
			var starttime = fromDate + " " + starthours + ":" + startmins
					+ ":00";
			var endhours = endhour.toString();
			if (endhours.length == 1) {
				endhours = "0" + endhours
			}
			var endmins = endmin.toString();
			if (endmins.length == 1) {
				endmins = "0" + endmins
			}
			var endtime = toDate + " " + endhours + ":" + endmins + ":00";

			var temp = document.createElement("form");
			temp.action = "../activity/saveactivities.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "activitydetail";
			opt.value = activitydetail;
			temp.appendChild(opt);
			var opt1 = document.createElement("input");
			opt1.name = "activityprocess";
			opt1.value = activityprocess;
			temp.appendChild(opt1);
			var opt2 = document.createElement("input");
			opt2.name = "attachid";
			opt2.value = attachid;
			temp.appendChild(opt2);
			var opt3 = document.createElement("input");
			opt3.name = "starttime";
			opt3.value = starttime;
			temp.appendChild(opt3);
			var opt4 = document.createElement("input");
			opt4.name = "endtime";
			opt4.value = endtime;
			temp.appendChild(opt4);
			var opt5 = document.createElement("input");
			opt5.name = "activityadd";
			opt5.value = activityadd;
			temp.appendChild(opt5);
			var opt6 = document.createElement("input");
			opt6.name = "activitycount";
			opt6.value = activitycount;
			temp.appendChild(opt6);
			var opt7 = document.createElement("input");
			opt7.name = "activitycost";
			opt7.value = activitycost;
			temp.appendChild(opt7);
			var opt8 = document.createElement("input");
			opt8.name = "activitytypeid";
			opt8.value = activitytypeid;
			temp.appendChild(opt8);
			var opt9 = document.createElement("input");
			opt9.name = "activityname";
			opt9.value = activityname;
			temp.appendChild(opt9);
			var opt10 = document.createElement("input");
			opt10.name = "userid";
			opt10.value =
	<%=userid%>
		;
			temp.appendChild(opt10);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
		}
	</script>
</body>
</html>