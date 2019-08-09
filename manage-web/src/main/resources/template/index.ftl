<!DOCTYPE html>
<html>
<head>
    <title>后台管理系统-登陆</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/static/css/index.css?v=${version!}" type="text/css">
</head>
<body>
    <div class="login_panel">
        <h2>后台管理系统</h2>
        <ul>
            <li>
                <span class="user_icon"></span>
                <input type="text" id="mobile" autocomplete="off" maxlength="11" value="13000000000"  placeholder="手机号码" class="login_text" />
            </li>
            <li>
                <span class="pwd_icon"></span>
                <input type="password" id="password" autocomplete="off" minlength="6" value="123456" maxlength="16"  placeholder="登陆密码" class="login_text"/>
            </li>
            <li>
                <span class="valid_icon"></span>
                <input type="text" autocomplete="off" id="validCode" placeholder="验证码" maxlength="4" class="login_text img_code_width"/>
                <img src="/captcha" title="点击刷新" class="valid_code_img" onClick="this.src='/captcha?t=' + Math.random();"/>
            </li>
            <li>
                <input type="button" value="立即登录" class="login_btn" title="回车登陆"/>
            </li>
            <li class="msg_tip"></li>
        </ul>
    </div>
    <h2 class="login_tips">正在登陆系统</h2>
</body>
<!-- jquery库2.0版本 -->
<script type="text/javascript" src="/static/js/jquery.min.js"  charset="UTF-8"></script>
<script type="text/javascript" src="/static/js/base64.min.js"  charset="UTF-8"></script>
<script type="text/javascript" src="/static/js/md5.min.js"  charset="UTF-8"></script>
<script type="text/javascript" src="/static/js/index.js?v=${version!}"   charset="UTF-8"></script>
</html>