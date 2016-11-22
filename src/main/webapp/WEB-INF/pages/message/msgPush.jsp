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
	//清除号码的方法
	function clear() {
		document.getElementById("phonenumber").value = "";
	}
	//号码去重方法
	function clearrepeat() {
		var phonenums = document.getElementById("phonenumber").value;
		if (phonenums == "") {
			alert("请填写手机号!");
			return false;
		}
		alert(phonenums);
		//数组解析号码串
		var oldTels = new Array(); //定义一数组 
		oldTels = phonenums.split(",");
		var date1 = new Date();
		var tel;
		var repeatTels = {}; //重复手机
		var newTels = []; //过滤后手机
		var len = oldTels.length;
		for (var j = 0; j < len; j++) {
			tel = oldTels[j];
			if (repeatTels[tel] !== 1) {
				repeatTels[tel] = 1;
				newTels.push(tel);
			}
		}
		document.getElementById("phonenumber").value = newTels.toString();
	}
	function tijiao() {
		var phonenums = document.getElementById("phonenumber").value;
		var type = 0;
		var messagetype = 4;
		if (phonenums == "") {
			alert("请填写手机号!");
			return false;
		} else {
			var temp = document.createElement("form");
			temp.action = "../message/messagepush.do";
			temp.method = "post";
			temp.style.display = "none";
			var opt = document.createElement("input");
			opt.name = "phonenumbers";
			opt.value = phonenums;
			temp.appendChild(opt);
			var opt1 = document.createElement("input");
			opt1.name = "type";
			opt1.value = type;
			temp.appendChild(opt1);
			var opt2 = document.createElement("input");
			opt2.name = "messagetype";
			opt2.value = messagetype;
			temp.appendChild(opt2);
			document.body.appendChild(temp);
			temp.submit();
			return temp;
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
					<h2>顺心会>消息管理>消息推送</h2>
					<div class="extra_info mini_tips icon_after">
						<a href="javascript:history.go(-1);">返回</a><i
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
											<td class="table_cell">推送渠道</td>
											<td class="table_cell">
												<div class="" id="searchBar">
													<div class="filter_content">
														<div id="js_sex" class="dropdown_menu">
															<a href="javascript:;"
																class="btn dropdown_switch jsDropdownBt"><label
																class="jsBtLabel"></label><i class="arrow"></i></a>
															<div class="dropdown_data_container jsDropdownList"
																style="display: none;">
																<ul class="dropdown_data_list" id="messagetype">
																	<li class="dropdown_data_item " value="0"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="0" data-index="0"
																		data-name="站内信">站内信</a></li>
																	<li class="dropdown_data_item " vallue="1"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="1" data-index="1"
																		data-name="短信">短信</a></li>
																</ul>
															</div>
														</div>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">收件人</td>
											<td class="table_cell" style="text-align: left;">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search textarea"
														style="border: none;"> <span> <span><input
																type="radio" name="status" /><label>系统用户</label></span>
															<div class="filter_content" style="display: inline;">
																<!-- <div id="js_sex" class="dropdown_menu">
																	<a href="javascript:;"
																		class="btn dropdown_switch jsDropdownBt"><label
																		class="jsBtLabel"></label><i class="arrow"></i></a>
																	<div class="dropdown_data_container jsDropdownList"
																		style="display: none;">
																	<ul class="dropdown_data_list">
																			<li class="dropdown_data_item "><a
																				onclick="return false;" href="javascript:;"
																				class="jsDropdownItem" data-value="0" data-index="0"
																				data-name="站内信">站内信</a></li>

																			<li class="dropdown_data_item "><a
																				onclick="return false;" href="javascript:;"
																				class="jsDropdownItem" data-value="1" data-index="1"
																				data-name="短信">短信</a></li>
																		</ul>
																	</div>
																</div>-->
															</div>
													</span>
														<div></div> <span> <span> <input
																type="radio" name="status" /><label>自定义用户</label></span> <textarea
																id="phonenumber" name="phonenumber"
																class="frm_input jsSearchInput"
																placeholder="(请输入多个手机，多个用户用英文“,”分割)"
																style="border: 1px solid #e7e7eb;"></textarea>
													</span>
														<div class="tool_bar tc left"
															style="padding: 0px; margin: 20px 0px;">
															<a href="javascript:clear();"
																class="btn btn_default js_complete_bnt">号码清空</a> <a
																href="javascript:clearrepeat();"
																class="btn btn_default js_complete_bnt">号码去重</a>
														</div>
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">模板选择</td>
											<td class="table_cell">
												<div class="" id="searchBar">
													<div class="filter_content">
														<div id="js_sex" class="dropdown_menu">
															<a href="javascript:;"
																class="btn dropdown_switch jsDropdownBt"><label
																class="jsBtLabel"></label><i class="arrow"></i></a>
															<div class="dropdown_data_container jsDropdownList"
																style="display: none;">
																<ul class="dropdown_data_list" id="selecttemplet"
																	name="selecttemplet">
																	<li class="dropdown_data_item " value="0"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="0" data-index="0"
																		data-name="站内信">站内信</a></li>

																	<li class="dropdown_data_item " value="1"><a
																		onclick="return false;" href="javascript:;"
																		class="jsDropdownItem" data-value="1" data-index="1"
																		data-name="短信">短信</a></li>
																</ul>
															</div>
														</div>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">推送内容</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search textarea"> <textarea
															id="templetcontent" name="templetcontent"
															class="frm_input jsSearchInput"></textarea>
													</span>
												</div>
											</td>
										</tr>
										<!--  <tr>
											<td class="table_cell">变量选择</td>
											<td class="table_cell center">
												<div class="space"></div>
												<ul class="var_list">
													<li><a href="javascript:;">｛验证码｝</a></li>
													<li><a href="javascript:;">｛用户昵称｝</a></li>
													<li><a href="javascript:;">｛活动名称｝</a></li>
													<li><a href="javascript:;">｛活动时间｝</a></li>
												</ul>
												<div class="space"></div>
											</td>
										</tr>-->
									</tbody>
								</table>
								<div class="tool_bar tc">
									<a href="javascript:;" class="btn btn_warn js_complete_bnt">取消</a>
									<a href="javascript:tijiao();"
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