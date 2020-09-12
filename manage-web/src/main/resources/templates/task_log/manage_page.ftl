<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>任务日志</title>
    <#include "../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 500;

        var title = "错误详情";
        var url = "/task_log/error_msg";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/task_log/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            if(!(row.state)){
                                str += '<a href="javascript:void(0);" class="detail" onclick="$.fn.dataGridOptions.show('+row.id+',title,winWidth,winHeight,url);" title="错误详细信息"></a>';
                            }
                            return str;
                        }
                    },
                    {field : "nid",title : "标示符",width : 150,align : "center"},
                    {field : "beanName",title : "bean名称",width : 180,align : "center"},
                    {field : "state",title : "状态",width : 120,align : "center",
                        formatter : function (value) {
                            if(value){
                                return "成功";
                            }
                            return "<span style='color:red;'>失败</span>";
                        }
                    },
                    {field : "ip",title : "机器IP",width : 120,align : "center" },
                    {field : "startTime",title : "开始时间",width : 180,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "elapsedTime",title : "总耗时(ms)",width : 120,align : "center"}
                ] ]
            });
            $.fn.extOptions.date("#middleTime","datetime");
        });
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true,doSize:false">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="标示符、机器IP" advance=true>
                <li>
                    <span>状态</span>
                    <select name="state" title="结果状态">
                        <option >全部</option>
                        <option value="true">成功</option>
                        <option value="false">失败</option>
                    </select>
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