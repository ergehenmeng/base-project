$(function(){
    if (window.parent != window){//防止在登陆后sessison过期,再次访问其他页面时,直接在对话框中显示页面
        parent.location.reload();
    }
	$(".loginBtn").on("click",function(){
		loginFun();
	});
});


$(document).keydown(function(event) {
	if (event.keyCode === 13) {
		loginFun();
	}
});

// 登录方法
function loginFun() {
	var accountName = $("#userName").val();
	if (accountName == "" || accountName == undefined) {
		$(".msgTip").show().html("请输入用户名");
		$("#userName").focus();
		return;
	}
	var pwd = $("#password").val();
	if (pwd == "" || pwd == undefined) {
		$(".msgTip").show().html("请输入密码");
		$("#password").focus();
		return;
	}
	var validCode = $("#validCode").val();
	if (validCode == "" || validCode == undefined) {
		$(".msgTip").show().html("请输入验证码");
		return;
	}
	// 加密处理
	doEncrypt("#password","#pwd");
	
	var password = $("#pwd").val();
	$.post("/admin/login",{accountName : accountName,pwd : password,validCode : validCode},function(data){
		if (data.result) {
			$("#password").val(""); // 跳转前清空密码框
			location.href = "/admin/home?type=2";
		} else {
			$(".msgTip").show().html(data.msg);
			$("#validCode").val("");
			$(".valicode_img").trigger("click"); // 刷新图形验证码
		}
	},"json");

}