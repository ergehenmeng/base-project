<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统角色管理</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 300;

        var authHeight = 500;

        var addTitle = "添加角色";
        var addUrl = "/sys/role/add_page";

        var editTitle = "编辑角色";
        var editUrl = "/sys/role/edit_page";

        var authTitle = "角色授权";
        var authUrl = "/sys/role/auth_page";

        var  delMsg = "删除角色可能导致相关人员无法使用系统,确定要执行该操作";
        var delUrl = "/sys/role/delete";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/sys/role/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            <@auth nid='roleManageEdit'>
                               str += '<a href="javascript:void(0);" class="edit" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑角色信息"></a>&nbsp;';
                            </@auth>
                            <@auth nid='roleManageAuth'>
                                str += '<a href="javascript:void(0);" class="auth" onclick="$.fn.dataGridOptions.editFun('+row.id+',authTitle,winWidth,authHeight,authUrl);" title="角色菜单授权"></a>&nbsp;';
                            </@auth>
                            <@auth nid='roleManageDelete'>
                                str += '<a href="javascript:void(0);" class="delete" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除角色"></a>&nbsp;';
                            </@auth>
                            return str;
                        }
                    },
                    {field : "roleName",title : "角色名称",width : 150,align : "center"},
                    {field : "roleType",title : "角色nid",width : 150,align : "center"},
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
            <@search placeholder="角色名称" />
            <@auth nid='roleManageAdd'>
                <div class="right">
                    <a href="#" class="search-btn" onclick="$.fn.dataGridOptions.editFun(0,addTitle,winWidth,winHeight,addUrl);"><i class="fa fa-plus"></i>&nbsp;添加</a>
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