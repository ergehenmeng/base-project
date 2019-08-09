<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>业务管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "resources.ftl">
    <link href="/static/css/home/home.css?v=${version!}" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/static/js/home.js?v=${version!}" ></script>
    <script type="text/javascript" src="/static/js/layer/layer.js?v=${version!}" ></script>
    <script type="text/javascript" >
        var isInit = ${isInit?c};//0不是初始用户,1是初始用户
    </script>
</head>
<body class="easyui-layout">
<div class="div_header" data-options="region:'north',border:false">
    <div class="div_user">
        <span title="当前用户" class="span_user_name">欢迎您:超管</span>
        <a href="#" class="user_role_menu"  title="查看查看"><i class="fa fa-user fa-lg fa-fw"></i>菜单权限</a> |
        <a href="#" class="change_pwd_btn"  title="修改个人密码"><i class="fa fa-unlock-alt fa-lg fa-fw"></i>修改密码</a> |
        <a href="#" class="logout_btn"  title="退出系统"><i class="fa fa-sign-out fa-lg fa-fw"></i>注销</a>
    </div>
</div>
<div data-options="region:'center',border:false" style="overflow: hidden;">
    <div class="easyui-tabs" id="div_tabs" data-options="fit:true,border:false"></div>
</div>

<div class="div_accordion" id="div_accordion" data-options="region:'west',border:false">
    <div class="div_scrollbar">
        <ul id="accordion" class="fanyin_accordion">
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