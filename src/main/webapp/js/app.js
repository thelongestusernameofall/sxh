function popup(selector){
	var maskEl=$(".mask"),
		dialog=$(selector),
		maskTpl='<div class="mask ui-draggable" style="display: none;"><iframe frameborder="0" style="filter:progid:DXImageTransform.Microsoft.Alpha(opacity:0);position:absolute;top:0px;left:0px;width:100%;height:100%;" src="about:blank"></iframe></div>';
	if(maskEl.length==0){
		$("body").append(maskTpl);
	}else if(maskEl.length>1){
		maskEl.remove();
		$("body").append(maskTpl);
	}
	$(".mask").show();
	dialog.show();
	dialog.height(dialog.find(".dialog").height());
}

function closePopup(selector){
	var maskEl=$(".mask"),
		dialog=$(selector);
	maskEl.hide();	
	dialog.hide();
}
$(function() {
	/* 下拉框初始化 */
	$(".dropdown_menu").on("click", function(event) {
		var clazz = event.target.className;
		$(".jsDropdownList").hide();
		if (clazz && clazz.indexOf("jsDropdownItem") > -1) {
			$(this).find(".jsBtLabel").text(event.target.innerHTML);
			return;
		}
		$(this).find(".jsDropdownList").show();
		event.stopPropagation();
	});

	$(document).on("click", function() {
		$(".jsDropdownList").hide();
	});

	/* 弹出模态窗口 
	var popup = function(selector) {
		var maskEl = $(".mask"), dialog = $(selector), maskTpl = '<div class="mask ui-draggable" style="display: none;"><iframe frameborder="0" style="filter:progid:DXImageTransform.Microsoft.Alpha(opacity:0);position:absolute;top:0px;left:0px;width:100%;height:100%;" src="about:blank"></iframe></div>';
		if (maskEl.length == 0) {
			$("body").append(maskTpl);
		} else if (maskEl.length > 1) {
			maskEl.remove();
			$("body").append(maskTpl);
		}
		$(".mask").show();
		dialog.show();
		dialog.height(dialog.find(".dialog").height());
	}*/

	var closePopup = function(selector) {
		var maskEl = $(".mask"), dialog = $(selector);
		maskEl.hide();
		dialog.hide();
	}

	$(".add_btn").click(function() {
		$("#dialog_action").text("新增");
		popup("#edit_dialog");
	});

	$(".js_tag_edit_btn").click(function() {
		$("#dialog_action").text("编辑");
		popup("#edit_dialog");
	});

	$(".js_tag_del_btn").click(function() {
		// $("#dialog_action").text("删除");
		// popup("#edit_dialog");
	});

	$("#update_version_btn").click(function() {
		$("#dialog_action").text("");
		popup("#edit_dialog");
	});

	$(".js_tag_disable_btn").click(function() {
		$("#dialog_action").text("停用");
		popup("#edit_dialog");
	});

	$(".icon16_opr.closed,.close_dialog_btn").click(function() {
		closePopup("#edit_dialog");
		closePopup("#auth_dialog");
	});

	$(".img_icon").click(function() {
		$("#dialog_action").text("");
		popup("#auth_dialog");
	});

	//$("#edit_form").validate();

	$("#edit_dialog .btn_primary button").click(function() {
		$("#edit_form").submit();
	});
});
