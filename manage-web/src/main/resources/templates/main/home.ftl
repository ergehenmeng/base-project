<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>业务管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "../resources.ftl">
    <link href="/static/css/main/home.css?v=${version!}" type="text/css" rel="stylesheet">
    <script src="/static/js/common/home.js?v=${version!}" type="text/javascript" ></script>
    <script src="/static/js/layer/layer.js?v=${version!}" type="text/javascript"  ></script>
    <script type="text/javascript" >
        let isInit = ${isInit?c};
        let isLock = ${isLock?c};
    </script>
</head>
<body class="easyui-layout">
<div class="div-header" data-options="region:'north',border:false">
    <div class="div-user">
        <span title="当前用户" class="span-user-name">欢迎您：超管</span>
        <a href="#" class="user-role-menu"  title="查看查看"><i class="fa fa-user fa-lg fa-fw"></i>菜单权限</a> |
        <a href="#" class="change-pwd-btn"  title="修改个人密码"><i class="fa fa-unlock-alt fa-lg fa-fw"></i>修改密码</a> |
        <a href="#" class="lock-screen"  title="锁屏"><i class="fa fa-lock fa-lg fa-fw"></i>锁屏</a> |
        <a href="#" class="logout-btn"  title="退出系统"><i class="fa fa-sign-out fa-lg fa-fw"></i>注销</a>
    </div>
</div>
<div data-options="region:'center',border:false" style="overflow: hidden;">
    <div class="easyui-tabs" id="div-tabs" data-options="fit:true,border:false"></div>
</div>

<div class="div-accordion" id="div-accordion" data-options="region:'west',border:false">
    <div class="div-scrollbar">
        <ul id="accordion" class="fanyin-accordion">
        <#if menuList?? && menuList?size gt 0 >
            <#list menuList as menu >
                <li>
                    <div class="link">
                    ${menu.title}<i class="fa fa-angle-down"></i>
                    </div>
                    <#if menu.subList?? && menu.subList?size gt 0 >
                        <ul class="submenu">
                            <#list menu.subList as child >
                                <li><a href="javascript:void(0);" rel="${(child.url)!}">${(child.title)!}</a></li>
                            </#list>
                        </ul>
                    </#if>
                </li>
            </#list>
        </#if>
        </ul>
    </div>
</div>
</body>
</html>