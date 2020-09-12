<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>数据字典管理</title>
    <#include "../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 400;

        var addTitle = "添加数据字典";
        var addUrl = "/dict/add_page";

        var editTitle = "编辑数据字典";
        var editUrl = "/dict/edit_page";

        var  delMsg = "删除数据字典可能导致相关人员无法使用系统,确定要执行该操作";
        var delUrl = "/dict/delete";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/dict/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            <@auth nid='dictManageEdit'>
                                str += '<a href="javascript:void(0);" class="edit" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑数据字典"></a>&nbsp;';
                            </@auth>
                            <@auth nid='dictManageDelete'>
                                str += '<a href="javascript:void(0);" class="delete" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除数据字典"></a>&nbsp;';
                            </@auth>
                            return str;
                        }
                    },
                    {field : "title",title : "字典名称",width : 150,align : "center"},
                    {field : "nid",title : "标示符",width : 150,align : "center"},
                    {field : "hiddenValue",title : "隐藏值",width : 100,align : "center"},
                    {field : "showValue",title : "显示值",width : 150,align : "center"},
                    {field : "locked",title : "编辑状态",width : 80,align : "center",
                        formatter : function(value) {
                            return value ? "锁定" : "正常";
                        }
                    },
                    {field : "addTime",title : "添加时间",width : 180,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "updateTime",title : "更新时间",width : 180,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 300 }
                ] ]
            });
        });

    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true,doSize:false">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="字典名称、标示符" advance=true>
                <li>
                    <span>编辑状态</span>
                    <select name="locked" class="type" title="编辑状态">
                        <option value="">全部</option>
                        <option value="true">锁定</option>
                        <option value="false">正常</option>
                    </select>
                </li>
            </@search>
            <@auth nid='dictManageAdd'>
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