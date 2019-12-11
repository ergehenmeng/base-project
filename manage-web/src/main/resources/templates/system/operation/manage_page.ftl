<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>操作日志管理</title>
    <#include "../../resources.ftl">

    <script type="text/javascript">
        var dataGrid;
        var title = "响应信息";
        var winWidth = 480;
        var winHeight = 300;
        var url = "/system/operation/query_page";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/system/operation_log/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = ''
                            str += '<a href="javascript:void(0);" class="content" onclick="$.fn.dataGridOptions.show('+row.id+',title,winWidth,winHeight,url);" title="操作日志内容"></a>&nbsp;';
                            return str;
                        }
                    },
                    {field : "url",title : "请求地址",width : 280,align : "center"},
                    {field : "operatorName",title : "操作人",width : 100,align : "center"},
                    {field : "request",title : "请求参数",width : 250,align : "center",
                        formatter:function(value){
                            return $.fn.dataGridOptions.maxLength(value);
                        }
                    },
                    {field : "addTime",title : "操作时间",width : 150,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "ip",title : "访问ip",align : "center",width : 250 },
                    {field : "businessTime",title : "业务耗时",align : "center",width : 250,
                        formatter:function(value){
                            return value + "ms";
                        }
                    }
                ] ]
            });
            $.fn.extOptions.dateRange("#targetTime","#startTime","#endTime","date");
        });
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="请求地址、操作人、访问ip" advance=true>
                <li>
                    <span>操作时间</span>
                    <input title="操作时间" type="text" id="targetTime" />
                    <input type="hidden" name="startTime" id="startTime"/>
                    <input type="hidden" name="endTime" id="endTime"/>
                </li>
            </@search>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
</html>