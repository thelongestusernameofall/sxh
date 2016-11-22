<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	function queding() {
		var id = "${suggestion.id}";
		var replycontent = document.getElementById("replycontent").value;
		if (replycontent.length > 400) {
			alert("回复内容超长");
			return false;
		}
		var replystatus = $("input[name='status']:checked").val();
		if (replystatus == undefined) {
			alert("请选择处理方式！");
			return false;
		} else {
			$.ajax({
				url : "../suggestion/updateSug.do",
				type : "post",
				dataType : "json",
				data : {
					id : id,
					replycontent : replycontent,
					replystatus : replystatus
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
		}
	}
</script>
</head>
<body>
	<jsp:include page="../top.jsp" />
	<!--<div id="body" class="body page_index">-->
	<div id="body" class="body page_user">
		<div id="js_container_box" class="container_box cell_layout side_l">
			<!--菜单栏  end-->
			<jsp:include page="../menu.jsp" />
			<!--主界面   -->
			<div class="col_main">
				<!--导航区  -->
				<div class="main_hd">
					<h2>顺心会>意见反馈>反馈详情</h2>
					<div class="extra_info mini_tips icon_after">
						<a href="../suggestion/showsug.do">返回</a><i
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
											<td class="table_cell">反馈内容</td>
											<td class="table_cell">${suggestion.sugcontent}</td>
										</tr>
										<tr>
											<td class="table_cell">手机型号</td>
											<td class="table_cell">${suggestion.phonemodel}</td>
										</tr>
										<tr>
											<td class="table_cell">对应版本</td>
											<td class="table_cell center">${suggestion.version}</td>
										</tr>
										<tr>
											<td class="table_cell">联系方式</td>
											<td class="table_cell center">1${suggestion.contract}</td>
										</tr>
										<tr>
											<td class="table_cell">反馈时间</td>
											<td class="table_cell">${suggestion.sugtime}</td>
										</tr>
										<tr>
											<td class="table_cell">回复内容</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search textarea"> <textarea
															class="frm_input jsSearchInput" id="replycontent"
															placeholder="请输入回复内容">${suggestion.replycontent}</textarea>
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td class="table_cell">处理方式</td>
											<td class="table_cell">
												<div class="edit_input" id="searchBar">
													<span class="frm_input_box search textarea"> <span><input
															type="radio" id="status" name="status" value="1" /><label>已回复</label></span>
														<span><input type="radio" id="status" name="status"
															value="2" /><label>已解决</label></span> <span><input
															type="radio" id="status" name="status" value="3"><label>不予处理</label></span>
													</span>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								<div class="tool_bar tc">
									<a href="../suggestion/showsug.do"
										class="btn btn_warn js_complete_bnt">取消</a> <a
										href="javascript:queding();"
										class="btn btn_primary js_complete_bnt">确定</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--主界面  end -->
		</div>
	</div>
	<script type="text/javascript">
		var status = '${suggestion.replystatus}';
		var ridaolen = document.getElementsByName("status");
		for (var i = 0; i < ridaolen.length; i++) {
			if (status == ridaolen[i].value) {
				ridaolen[i].checked = true
			}
		}
	</script>
</body>
</html>