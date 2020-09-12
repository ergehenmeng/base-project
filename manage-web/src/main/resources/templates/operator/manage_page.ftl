<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>系统用户管理</title>
    <#include "../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 300;

        var addTitle = "添加用户";
        var addUrl = "/operator/add_page";

        var editTitle = "编辑用户";
        var editUrl = "/operator/edit_page";

        var delMsg = "确定要执行删除操作?";
        var delUrl = "/operator/delete";

        var lockMsg = "确定要执行锁定操作?";
        var lockUrl = "/operator/lock_operator";

        var unlockMsg = "确定要执行解锁操作?";
        var unlockUrl = "/operator/unlock_operator";

        var resetMsg = "确定要执行重置操作?";
        var resetUrl = "/operator/reset_password";


        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/operator/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 120,
                        align : "center",
                        formatter : function(value, row) {
                            var str = ''
                            <@auth nid='sysUserEdit'>
                                str += '<a href="javascript:void(0);" class="edit" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑用户信息"></a>&nbsp;';
                            </@auth>
                            <@auth nid='sysUserLock'>
                                if(row.state === 1){
                                    str += '<a href="javascript:void(0);" class="lock" onclick="$.fn.dataGridOptions.confirm('+row.id+',lockUrl,lockMsg);" title="锁定账户状态"></a>&nbsp;';
                                }
                            </@auth>
                            <@auth nid='sysUserUnlock'>
                                if(row.state === 0){
                                    str += '<a href="javascript:void(0);" class="unlock" onclick="$.fn.dataGridOptions.confirm('+row.id+',unlockUrl,unlockMsg)" title="解锁账户状态"></a>&nbsp;';
                                }
                            </@auth>
                            <@auth nid='sysUserReset'>
                                str += '<a href="javascript:void(0);" class="reset" onclick="$.fn.dataGridOptions.confirm('+row.id+',resetUrl,resetMsg);" title="重置登陆密码"></a>&nbsp;';
                            </@auth>
                            <@auth nid='sysUserDelete'>
                                str += '<a href="javascript:void(0);" class="delete" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除用户"></a>&nbsp;';
                            </@auth>
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "operatorName",title : "用户名称",width : 150,align : "center"},
                    {field : "mobile",title : "手机号",width : 150,align : "center"},
                    {field : "state",title : "状态",width : 80,align : "center",
                        formatter:function(value){
                            if(value === 0){
                                return "锁定";
                            }else if(value === 1){
                                return "正常";
                            }
                        }
                    },
                    {field : "addTime",title : "添加时间",width : 150,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "updateTime",title : "更新时间",width : 150,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 250 }
                ] ]
            });
        });
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="用户名称、手机号" />
            <@auth nid='sysUserAdd'>
                <div class="right">
                    <a href="#" class="search-btn"
                       onclick="$.fn.dataGridOptions.editFun(0,addTitle,winWidth,winHeight,addUrl);"><i class="fa fa-plus"></i>&nbsp;添加</a>
                </div>
            </@auth>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
</html>