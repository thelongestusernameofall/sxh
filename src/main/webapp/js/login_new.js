var Login=function(){
function e(e){
o.err.html('<i class="icon18_common error"></i><span class="err_tips">'+e+"</span>").show();
}
function n(){
c&&o.verifyImg.attr("src","/cgi-bin/verifycode?username="+$.trim(o.account.val())+"&r="+ +new Date);
}
function r(r,t,i){
$.post("/cgi-bin/login",{
username:r,
pwd:$.md5(t.substr(0,16)),
imgcode:i,
f:"json"
},function(t){
switch(t.base_resp.ret+""){
case"0":
$.cookie("noticeLoginFlag",1,{
expires:30
}),o.check.prop("checked")?$.cookie("remember_acct",r,{
expires:30
}):$.removeCookie("remember_acct"),location.href=t.redirect_url;
break;

case"-1":
e("系统错误，请稍候再试。");
break;

case"200002":
e("帐号或密码错误。");
break;

case"200007":
e("您目前处于访问受限状态。");
break;

case"200008":
c=!0,$("#verifyDiv").show(),o.verify.val("").focus(),e("请输入图中的验证码");
break;

case"200021":
e("不存在该帐户。");
break;

case"200023":
o.pwd.focus(),e("您输入的帐号或者密码不正确，请重新输入。");
break;

case"200025":
e('海外帐号请在公众平台海外版登录,<a href="http://admin.wechat.com/">点击登录</a>');
break;

case"200026":
e("该公众会议号已经过期，无法再登录使用。");
break;

case"200027":
o.verify.val("").focus(),e("您输入的验证码不正确，请重新输入");
break;

case"200121":
e('该帐号属于微信开放平台，请点击<a href="https://open.weixin.qq.com/">此处登录</a>');
break;

default:
e("未知的返回。");
}
0!=t.base_resp.ret&&n();
});
}
function t(){
$("#loginForm").find("input").focus(function(){
$(this).parent().addClass("focus");
}).blur(function(){
$(this).parent().removeClass("focus");
}),$("#verifyChange").click(function(){
n();
}),$("#pwd,#verify").keydown(function(e){
var n="which"in e?e.which:e.keyCode;
13==n&&$("#loginBt").trigger("click");
}),$("#loginBt").click(function(){
var n=$.trim(o.account.val())||"",t=$.trim(o.pwd.val())||"",i=$.trim(o.verify.val())||"";
return 0==n.length?(e("你还没有输入帐号！"),void o.account.focus()):0==t.length?(e("你还没有输入密码！"),
void o.pwd.focus()):1==c&&0==i.length?(e("你还没有输入验证码！"),void o.verify.focus()):void r(n,t,i);
}),$("#rememberCheck").change(function(){
$(this).prop("checked")?$(this).parent().addClass("selected"):$(this).parent().removeClass("selected");
}),$.cookie("remember_acct")&&($("#rememberCheck").trigger("click"),$("#account").val($.cookie("remember_acct")),
$("#pwd").focus());
}
var o={
account:$("#account"),
pwd:$("#pwd"),
err:$("#err"),
verify:$("#verify"),
verifyImg:$("#verifyImg"),
check:$("#rememberCheck")
},c=!1;
return o.account.focus(),{
init:t
};
}(),Case=function(){
function e(){
jQuery(window).scroll(function(){
n();
}),$(".jsIconItem").hover(function(){
clearInterval(t),s=$(this).data("index"),$(".jsIcon").removeClass("on"),$(".jsImg").removeClass("on"),
$(".jsIcon").eq(s).addClass("on"),$(".jsImg").eq(s).addClass("on");
},function(){
t=setInterval(r,1500);
}),n();
}
function n(){
0==i&&($(".jsIcon").eq(s).addClass("on"),$(document).scrollTop()+document.documentElement.clientHeight>o.offset().top&&(i=!0,
t=setInterval(r,1500))),0==a&&$(document).scrollTop()+document.documentElement.clientHeight>c.parent().offset().top&&(a=!0,
$(".jsImg").eq(s).addClass("on"),$(".jsImg>img").each(function(e,n){
$(n).attr("src",$(n).data("src"));
})),i&&a&&jQuery(window).unbind("scroll");
}
function r(){
6>s?s++:6==s&&(s=0),$(".jsIcon").removeClass("on").eq(s).addClass("on"),$(".jsImg").removeClass("on").eq(s).addClass("on");
}
var t,o=$("#iconList"),c=$(".jsImg"),i=!1,a=!1,s=0;
return{
init:e
};
}();
$(function(){
Login.init(),Case.init();
}),function(e){
"use strict";
function n(e,n){
var r=(65535&e)+(65535&n),t=(e>>16)+(n>>16)+(r>>16);
return t<<16|65535&r;
}
function r(e,n){
return e<<n|e>>>32-n;
}
function t(e,t,o,c,i,a){
return n(r(n(n(t,e),n(c,a)),i),o);
}
function o(e,n,r,o,c,i,a){
return t(n&r|~n&o,e,n,c,i,a);
}
function c(e,n,r,o,c,i,a){
return t(n&o|r&~o,e,n,c,i,a);
}
function i(e,n,r,o,c,i,a){
return t(n^r^o,e,n,c,i,a);
}
function a(e,n,r,o,c,i,a){
return t(r^(n|~o),e,n,c,i,a);
}
function s(e,r){
e[r>>5]|=128<<r%32,e[(r+64>>>9<<4)+14]=r;
var t,s,u,f,l,d=1732584193,m=-271733879,h=-1732584194,p=271733878;
for(t=0;t<e.length;t+=16)s=d,u=m,f=h,l=p,d=o(d,m,h,p,e[t],7,-680876936),p=o(p,d,m,h,e[t+1],12,-389564586),
h=o(h,p,d,m,e[t+2],17,606105819),m=o(m,h,p,d,e[t+3],22,-1044525330),d=o(d,m,h,p,e[t+4],7,-176418897),
p=o(p,d,m,h,e[t+5],12,1200080426),h=o(h,p,d,m,e[t+6],17,-1473231341),m=o(m,h,p,d,e[t+7],22,-45705983),
d=o(d,m,h,p,e[t+8],7,1770035416),p=o(p,d,m,h,e[t+9],12,-1958414417),h=o(h,p,d,m,e[t+10],17,-42063),
m=o(m,h,p,d,e[t+11],22,-1990404162),d=o(d,m,h,p,e[t+12],7,1804603682),p=o(p,d,m,h,e[t+13],12,-40341101),
h=o(h,p,d,m,e[t+14],17,-1502002290),m=o(m,h,p,d,e[t+15],22,1236535329),d=c(d,m,h,p,e[t+1],5,-165796510),
p=c(p,d,m,h,e[t+6],9,-1069501632),h=c(h,p,d,m,e[t+11],14,643717713),m=c(m,h,p,d,e[t],20,-373897302),
d=c(d,m,h,p,e[t+5],5,-701558691),p=c(p,d,m,h,e[t+10],9,38016083),h=c(h,p,d,m,e[t+15],14,-660478335),
m=c(m,h,p,d,e[t+4],20,-405537848),d=c(d,m,h,p,e[t+9],5,568446438),p=c(p,d,m,h,e[t+14],9,-1019803690),
h=c(h,p,d,m,e[t+3],14,-187363961),m=c(m,h,p,d,e[t+8],20,1163531501),d=c(d,m,h,p,e[t+13],5,-1444681467),
p=c(p,d,m,h,e[t+2],9,-51403784),h=c(h,p,d,m,e[t+7],14,1735328473),m=c(m,h,p,d,e[t+12],20,-1926607734),
d=i(d,m,h,p,e[t+5],4,-378558),p=i(p,d,m,h,e[t+8],11,-2022574463),h=i(h,p,d,m,e[t+11],16,1839030562),
m=i(m,h,p,d,e[t+14],23,-35309556),d=i(d,m,h,p,e[t+1],4,-1530992060),p=i(p,d,m,h,e[t+4],11,1272893353),
h=i(h,p,d,m,e[t+7],16,-155497632),m=i(m,h,p,d,e[t+10],23,-1094730640),d=i(d,m,h,p,e[t+13],4,681279174),
p=i(p,d,m,h,e[t],11,-358537222),h=i(h,p,d,m,e[t+3],16,-722521979),m=i(m,h,p,d,e[t+6],23,76029189),
d=i(d,m,h,p,e[t+9],4,-640364487),p=i(p,d,m,h,e[t+12],11,-421815835),h=i(h,p,d,m,e[t+15],16,530742520),
m=i(m,h,p,d,e[t+2],23,-995338651),d=a(d,m,h,p,e[t],6,-198630844),p=a(p,d,m,h,e[t+7],10,1126891415),
h=a(h,p,d,m,e[t+14],15,-1416354905),m=a(m,h,p,d,e[t+5],21,-57434055),d=a(d,m,h,p,e[t+12],6,1700485571),
p=a(p,d,m,h,e[t+3],10,-1894986606),h=a(h,p,d,m,e[t+10],15,-1051523),m=a(m,h,p,d,e[t+1],21,-2054922799),
d=a(d,m,h,p,e[t+8],6,1873313359),p=a(p,d,m,h,e[t+15],10,-30611744),h=a(h,p,d,m,e[t+6],15,-1560198380),
m=a(m,h,p,d,e[t+13],21,1309151649),d=a(d,m,h,p,e[t+4],6,-145523070),p=a(p,d,m,h,e[t+11],10,-1120210379),
h=a(h,p,d,m,e[t+2],15,718787259),m=a(m,h,p,d,e[t+9],21,-343485551),d=n(d,s),m=n(m,u),
h=n(h,f),p=n(p,l);
return[d,m,h,p];
}
function u(e){
var n,r="";
for(n=0;n<32*e.length;n+=8)r+=String.fromCharCode(e[n>>5]>>>n%32&255);
return r;
}
function f(e){
var n,r=[];
for(r[(e.length>>2)-1]=void 0,n=0;n<r.length;n+=1)r[n]=0;
for(n=0;n<8*e.length;n+=8)r[n>>5]|=(255&e.charCodeAt(n/8))<<n%32;
return r;
}
function l(e){
return u(s(f(e),8*e.length));
}
function d(e,n){
var r,t,o=f(e),c=[],i=[];
for(c[15]=i[15]=void 0,o.length>16&&(o=s(o,8*e.length)),r=0;16>r;r+=1)c[r]=909522486^o[r],
i[r]=1549556828^o[r];
return t=s(c.concat(f(n)),512+8*n.length),u(s(i.concat(t),640));
}
function m(e){
var n,r,t="0123456789abcdef",o="";
for(r=0;r<e.length;r+=1)n=e.charCodeAt(r),o+=t.charAt(n>>>4&15)+t.charAt(15&n);
return o;
}
function h(e){
return unescape(encodeURIComponent(e));
}
function p(e){
return l(h(e));
}
function v(e){
return m(p(e));
}
function g(e,n){
return d(h(e),h(n));
}
function $(e,n){
return m(g(e,n));
}
e.md5=function(e,n,r){
return n?r?g(n,e):$(n,e):r?p(e):v(e);
};
}("function"==typeof jQuery?jQuery:this),function(e,n,r){
function t(e){
return e;
}
function o(e){
return c(decodeURIComponent(e.replace(a," ")));
}
function c(e){
return 0===e.indexOf('"')&&(e=e.slice(1,-1).replace(/\\"/g,'"').replace(/\\\\/g,"\\")),
e;
}
function i(e){
return s.json?JSON.parse(e):e;
}
var a=/\+/g,s=e.cookie=function(c,a,u){
if(a!==r){
if(u=e.extend({},s.defaults,u),null===a&&(u.expires=-1),"number"==typeof u.expires){
var f=u.expires,l=u.expires=new Date;
l.setDate(l.getDate()+f);
}
return a=s.json?JSON.stringify(a):String(a),n.cookie=[encodeURIComponent(c),"=",s.raw?a:encodeURIComponent(a),u.expires?"; expires="+u.expires.toUTCString():"",u.path?"; path="+u.path:"",u.domain?"; domain="+u.domain:"",u.secure?"; secure":""].join("");
}
for(var d=s.raw?t:o,m=n.cookie.split("; "),h=c?null:{},p=0,v=m.length;v>p;p++){
var g=m[p].split("="),$=d(g.shift()),k=d(g.join("="));
if(c&&c===$){
h=i(k);
break;
}
c||(h[$]=i(k));
}
return h;
};
s.defaults={},e.removeCookie=function(n,r){
return null!==e.cookie(n)?(e.cookie(n,null,r),!0):!1;
};
}(jQuery,document);