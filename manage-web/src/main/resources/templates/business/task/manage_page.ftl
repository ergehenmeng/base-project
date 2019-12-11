<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>任务配置</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 400;

        var editTitle = "编辑任务";
        var editUrl = "/business/task/edit_page";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/business/task/list_page",
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
                            <@auth nid = "taskConfigEdit">
                                str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑"> 编辑</a>';
                            </@auth>
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "title",title : "标题",width : 150,align : "center"},
                    {field : "nid",title : "标示符",width : 150,align : "center"},
                    {field : "beanName",title : "bean名称",width : 150,align : "center"},
                    {field : "cronExpression",title : "表达式",width : 200,align : "center" },
                    {field : "state",title : "状态",width : 80,align : "center",
                        formatter :function (value) {
                            if(value === 1){
                                return "开启";
                            }
                            return "关闭";
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
        var clearUrl = "/business/task/refresh";
        var clearMsg = "确定要重置定时任务配置吗";
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true,doSize:false">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="标题、标示符、bean名称" advance=true>
                <li>
                    <span>状态</span>
                    <select name="state" title="状态">
                        <option value="">全部</option>
                        <option value="1">开启</option>
                        <option value="0">关闭</option>
                    </select>
                </li>
            </@search>
            <@auth nid='taskConfigRefreseh'>
                <div class="right">
                    <a href="#" class="search-btn" onclick="$.fn.dataGridOptions.confirm(0,clearUrl,clearMsg);"><i class="fa fa-refresh">&nbsp;</i>重置</a>
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