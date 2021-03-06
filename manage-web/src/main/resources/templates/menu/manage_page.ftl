<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>菜单管理</title>
    <#include "../resources.ftl">
    <script type="text/javascript">
        var treeGrid;

        var winWidth = 420;
        var winHeight = 450;

        var addTitle = "添加菜单";
        var addUrl = "/menu/add_page";

        var editTitle = "编辑菜单";
        var editUrl = "/menu/edit_page";

        var delMsg = "确定要删除该菜单选项吗?";
        var delUrl = "/menu/delete";

        $(function() {
            treeGrid = $.fn.treeGridOptions.treeGrid("#treeGrid",{
                url : "/menu/list_page",
                loadFilter : pageFilter,
                onLoadSuccess : function(){
                  treeGrid.treegrid("collapseAll");
                },
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            <@auth nid='menuManageAdd'>
                                if (row.grade < 3){
                                    str += '<a href="javascript:void(0);" class="add" title="添加子菜单" onclick="$.fn.treeGridOptions.editFun('+row.id+',addTitle,winWidth,winHeight,addUrl,{\'grade\':\''+ row.grade +'\'});"></a>&nbsp;';
                                }
                            </@auth>
                            <@auth nid='menuManageEdit'>
                                str += '<a href="javascript:void(0);" class="edit" title="编辑菜单" onclick="$.fn.treeGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);"></a>&nbsp;';
                            </@auth>
                            <@auth nid='menuManageDelete'>
                                str += '<a href="javascript:void(0);" class="delete" title="删除菜单" onclick="$.fn.treeGridOptions.confirm('+row.id+',delUrl,delMsg);"></a>&nbsp;';
                            </@auth>
                            return str;
                        }
                    },
                    {field : "text",title : "菜单名称",width : 150,align : "center"},
                    {field : "nid",title : "菜单标示",width : 180,align : "center"},
                    {field : "url",title : "菜单URL",width : 300,align : "center"},
                    {field : "subUrl",title : "子菜单URL",width : 300,align : "center",
                        formatter:function (value) {
                            return $.fn.dataGridOptions.maxLength(value,50);
                        }
                    },
                    {field : "grade",title : "类型",width : 80,align : "center",
                        formatter : function(value) {
                            return value === 3 ? "按钮" : "导航";
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
            return $.fn.treeGridOptions.dataFilter(rows.data,"id","title","pid",0,"closed");
        }
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@auth nid="menuManageAdd">
                <div class="right">
                    <a href="#" class="search-btn" onclick="$.fn.treeGridOptions.editFun(0,addTitle,winWidth,winHeight,addUrl)"><i class="fa fa-plus"></i>&nbsp;添加</a>
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