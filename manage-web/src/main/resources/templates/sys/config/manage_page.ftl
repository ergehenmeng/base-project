<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>系统参数管理</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 460;

        var editTitle = "编辑系统参数";
        var editUrl = "/sys/config/edit_page";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/sys/config/list_page",
                columns : [[
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row, index) {
                            var str = '';
                            <@auth nid='sysParamterQuery'>
                                str += '<a href="javascript:void(0);" class="edit" title="编辑系统参数" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);"></a>&nbsp;';
                            </@auth>
                            return str;
                        }
                    },
                    {field : "title",title : "参数名称",width : 200,align : "center"},
                    {field : "nid",title : "参数标示",width : 160,align : "center"},
                    {field : "content",title : "参数值",width : 300,align : "center",
                        formatter:function(value){
                            return $.fn.dataGridOptions.maxLength(value);
                        }
                    },
                    {
                        field: "locked", title: "是否锁定", width: 80, align: "center",
                        formatter: function (value, rows, index) {
                            return value ? "是" : "否";
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 300 },
                    {field : "updateTime",title : "更新时间",width : 180,align : "center",
                        formatter : function(value, rows, index) {
                            return getLocalTime(value, 4);
                        }
                    }
                ]]
            });
        });
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="参数名称、参数标示、备注" />
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
</html>