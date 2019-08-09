<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>菜单管理</title>
    <#include "../../../resources.ftl">
    <script type="text/javascript">
        var treeGrid;

        var winWidth = 480;
        var winHeight = 450;

        var addTitle = "添加菜单";
        var addUrl = "/public/system/menu/add_page";

        var editTitle = "编辑菜单";
        var editUrl = "/public/system/menu/edit_page";

        var delMsg = "确定要删除该菜单选项吗?";
        var delUrl = "/system/menu/delete";

        $(function() {
            treeGrid = $.fn.treeGridOptions.treeGrid("#treeGrid",{
                url : "/system/menu/list_page",
                loadFilter : pageFilter,
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
                            str += '<a href="javascript:void(0);" onclick="$.fn.treeGridOptions.editFun('+row.id+',addTitle,winWidth,winHeight,addUrl,{\'nid\':\''+ row.nid +'\'});"> 添加</a>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.treeGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);"> 编辑</a>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.treeGridOptions.confirm('+row.id+',delUrl,delMsg);"> 删除</a>';
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "text",title : "菜单名称",width : 150,align : "center"},
                    {field : "nid",title : "菜单标示",width : 150,align : "center"},
                    {field : "url",title : "菜单URL",width : 300,align : "center"},
                    {field : "subUrl",title : "子菜单URL",width : 350,align : "center",
                        formatter:function (value) {
                            return $.fn.dataGridOptions.format(value,50);
                        }
                    },
                    {field : "classify",title : "类型",width : 80,align : "center",
                        formatter : function(value) {
                            return value === 0 ? "导航菜单" : "按钮菜单";
                        }
                    },
                    {field : "sort",title : "排序",width : 50,align : "center"},
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
                    {field : "remark",title : "备注",align : "center",width : 200 }
                ] ]
            });
        });

        function pageFilter(rows) {
            if (!rows || !rows.data){
                return;
            }
            return $.fn.treeGridOptions.dataFilter(rows.data,"id","title","pid",0);
        }
    </script>
</head>
<body class="tabs_body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition_bar">
        <div class="layout_norths">
            <@auth nid="menuManageQuery">
                <div class="right">
                    <a href="#" class="searchBtn" onclick="$.fn.treeGridOptions.editFun(0,addTitle,winWidth,winHeight,addUrl)"><i class="fa fa-plus"></i>&nbsp;添加</a>
                </div>
            </@auth>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="treeGrid" ></table>
    </div>
</div>
</body>
</html>