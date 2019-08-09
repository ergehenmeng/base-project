<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>缓存管理</title>
    <#include "../../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var  clearMsg = "请选择一条数据";
        var clearUrl = "/system/cache/clear";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/system/cache/list",
                singleSelect : false,
                columns : [ [
                    {
                        field : "actionss",
                        title : "操作",
                        width : 120,
                        align : "center",
                        checkbox:true,
                        formatter : function(value, row, index) {
                            var str = '';
                            str += '<dl>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "title",title : "缓存名称",width : 200,align : "center"},
                    {field : "cacheName",title : "缓存标示符",width : 200,align : "center"},

                    {field : "updateTime",title : "上次刷新时间",width : 200,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 300 }
                ] ],
                rowStyler:function(index,rows){
                    var updateTime = rows.updateTime;
                    //5分钟后不显示颜色
                    if(updateTime != null && (new Date().getTime() - parseFloat(updateTime)) < 300000){
                        if(rows.state === 1){
                            return 'background-color:#C7EECE';
                        }else if(rows.state === 2){
                            return 'background-color:#ff7575';
                        }
                    }
                }
            });
        });

    </script>
</head>
<body class="tabs_body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition_bar">
        <div class="layout_norths">
            <div class="right">
                <a href="#" class="searchBtn" onclick="$.fn.dataGridOptions.select(clearUrl,clearMsg,'cacheName');"><i class="fa fa-refresh">&nbsp;</i>清除</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
</html>