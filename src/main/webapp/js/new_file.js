define("common/wx/dropdowns.js", ["biz_web/widget/dropdown.css", "tpl/dropdowns.html.js"], function(e, t, n) {
	try {
		var r = +(new Date);
		"use strict", e("biz_web/widget/dropdown.css");
		var i = e("tpl/dropdowns.html.js"),
			s = {
				lanbel: "娣诲姞鍒�",
				data: [],
				callback: $.noop,
				render: $.noop,
				delay: 500,
				disabled: !1,
				row: -1,
				values: [],
				disabledValue: []
			};

		function o(e) {
			var t = this,
				n = ".jsDropdownsList";
			t.container = $(e.container), t.container.addClass("dropdown_menu dropdown_checkbox"), e.render && typeof e.render && (e.renderHtml = "", $.each(e.data, function(t, n) {
					e.renderHtml += e.render(n);
				})), e = $.extend(!0, {}, s, e), e.label ? e.hasClass && t.container.addClass(e.hasClass) : e.label = e.lanbel, this.isDisabled = e.disabled, e.disabled && t.container.addClass("disabled"), e.length = e.data.length, e.row <= 0 && (e.length > 30 ? e.length % 3 > 0 ? e.row = (e.length - e.length % 3) / 3 + 1 : e.row = e.length / 3 : e.row = 10), e.length % e.row > 0 ? e.col = (e.length - e.length % e.row) / e.row + 1 : e.col = e.length / e.row, t.opt = e, t.container.html(template.compile(i)(e)).find(n).hide(), t.bt = t.container.find(".jsDropdownBt"), t.dropdown = t.container.find(n), t.checkboxes = t.container.find(".js_select").checkbox({
					onChanged: function() {
						return !1;
					}
				}).adjust(t.opt.values).disable(t.opt.disabledValue), $.each(e.data, function(e, n) {
					n.value && $.isPlainObject(n.value) && $.data(t.dropdown.find(".jsDropdownItem")[e], "value", n.value);
				}), typeof e.index != "undefined" && e.data.length !== 0 && (t.bt.find(".jsBtLabel").text(e.data[e.index].name || e.label), t.value = e.data[e.index].value), t.dropdown.on("click", function() {
					t.clickin = !0;
				}), t.bt.on("click", function() {
					e.disabled || (t.container.addClass("open"), t.dropdown.show()), t.clickin = !0;
				}), $(document).on("click", function() {
					t.clickin ? t.clickin = !1 : (t.container.removeClass("open"), t.dropdown.hide());
				}),
				function() {
					$.each(t.container.find(".js_btn"), function(n, r) {
						e.buttons[n].click && $(r).click(function() {
							return e.buttons[n].click.apply(t), !1;
						});
					});
				}();
		}
		o.prototype = {
			selected: function(e) {
				var t = this;
				if (typeof e == "number") {
					if (this.opt.data && this.opt.data[e]) {
						var n = this.opt.data[e].name,
							r = this.opt.data[e].value;
						this.dropdown.find(".jsDropdownItem:eq(" + e + ")").trigger("click", r), this.bt.find(".jsBtLabel").text(n);
					}
				} else $.each(this.opt.data, function(i, s) {
					if (e == s.value || e == s.name) return t.dropdown.find(".jsDropdownItem:eq(" + i + ")").trigger("click", r), t.bt.find(".jsBtLabel").text(n), !1;
				});
				return this;
			},
			reset: function() {
				return this.bt.find(".jsBtLabel").text(this.opt.label), this.value = null, this;
			},
			destroy: function() {
				return this.isDisabled && this.container.removeClass("disabled"), this.container.children().remove(), this.container.off(), this;
			},
			enable: function() {
				return this.opt.disabled = !1, this.container.removeClass("disabled"), this;
			},
			disable: function() {
				return this.opt.disabled = !0, this.container.addClass("disabled"), this;
			},
			hide: function() {
				this.container.removeClass("open"), this.dropdown.hide();
			}
		}, n.exports = o;
	} catch (u) {
		wx.jslog({
			src: "common/wx/dropdowns.js"
		}, u);
	}
});