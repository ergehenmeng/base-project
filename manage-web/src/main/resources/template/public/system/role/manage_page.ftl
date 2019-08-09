<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统角色管理</title>
    <#include "../../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 480;
        var winHeight = 300;

        var authHeight = 500;

        var addTitle = "添加角色";
        var addUrl = "/public/system/role/add_page";

        var editTitle = "编辑角色";
        var editUrl = "/public/system/role/edit_page";

        var authTitle = "角色授权";
        var authUrl = "/public/system/role/auth_page";

        var  delMsg = "删除角色可能导致相关人员无法使用系统,确定要执行该操作";
        var delUrl = "/system/role/delete";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/system/role/list_page",
                columns : [ [
                    {
                        field : "action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            str += '<dl>';
                            str += '<dt><a href="javascript:void(0);">详情<i class="fa fa-angle-down fa-fw"></i></a></dt>';
                            str += '<dd>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑角色信息"> 编辑</a>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.editFun('+row.id+',authTitle,winWidth,authHeight,authUrl);" title="角色菜单授权"> 授权</a>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除角色"> 删除</a>';
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "roleName",title : "角色名称",width : 150,align : "center"},
                    {field : "roleType",title : "角色类型",width : 150,align : "center"},
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
<body class="tabs_body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition_bar">
        <div class="layout_norths">
            <@search placeholder="角色名称" />
            <div class="right">
                <a href="#" class="searchBtn" onclick="$.fn.dataGridOptions.editFun(0,addTitle,winWidth,winHeight,addUrl);"><i class="fa fa-plus"></i>&nbsp;添加</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
</html>