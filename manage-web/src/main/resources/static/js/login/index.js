$(function(){
    if (window.parent !== window){//防止在登陆后sessison过期,再次访问其他页面时,直接在对话框中显示页面
        parent.location.reload();
    }
    $(".login-btn").on("click",function(){
        loginFun();
    });
    $(window).on("keydown",function(event) {
        if (event.keyCode === 13) {
            loginFun();
        }
    });
});

/**
 * 错误提示
 * @param msg
 */
function errorTip(msg){
    $(".msg-tip").show().html(msg);
}

// login
function loginFun() {
    let $mobile = $("#mobile");
    let mobile = $mobile.val();
    if (!verifyMobile(mobile)) {
        errorTip("手机号码格式错误");
        $mobile.focus();
        return;
    }
    let $password = $("#password");
    let password = $password.val();
    if (!password) {
        errorTip("密码格式错误");
        $password.focus();
        return;
    }
    let $validCode = $("#validCode");
    let validCode = $validCode.val();
    if (!validCode) {
        $validCode.focus();
        errorTip("请输入验证码");
        return;
    }

    if (validCode.length < 4) {
        $validCode.focus();
        errorTip("验证码输入错误");
        return;
    }

    $.post("/login",{mobile : mobile,password : md5(Base64.encode(password)),validCode : validCode},function(data){
        if (data.code === 200) {
            // 跳转前清空密码框
            window.open("/main","_self");
        } else {
            errorTip(data.msg);
            $validCode.val("");
            // 刷新图形验证码
            $(".valid-code-img").trigger("click");
        }
    },"json");
}

/**
 * 校验手机号码是否合法
 * @param mobile
 * @returns {boolean}
 */
function verifyMobile(mobile){
    let regexp = /^((13[0-9])|(14[579])|(15([^4]))|(18[0-9])|(17[01235678]))\d{8}$/;
    return regexp.test(mobile);
}

function successAnimate(){
    $(".login-panel").animate({
        left:"1390px",
        opacity:"0",
        height:"0",
        width:"0"
    },500,function(){
        var $loginTips = $(".login-tips").show();
        setInterval(function () {
            var text = $loginTips.html();
            if(text.length >= 11){
                text = text.substring(0,6);
            }else{
                text += ".";
            }
            $loginTips.html(text);
        },500);
        setTimeout(function(){
            window.location.href = "/main";
        },2000);
    });
}