<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>短信记录管理</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/business/sms_log/list_page",
                columns : [ [
                    {field : "smsType",title : "标示符",width : 150,align : "center"},
                    {field : "mobile",title : "手机号",width : 150,align : "center"},
                    {field : "content",title : "短信内容",width : 800,align : "center",
                        formatter :function (value) {
                            return $.fn.dataGridOptions.maxLength(value,50);
                        }
                    },
                    {field : "state",title : "状态",width : 180,align : "center",
                        formatter : function(value) {
                            if(value === 0){
                                return "发送中";
                            }else if(value === 1){
                                return "已发送"
                            }else if(value === 2){
                                return "发送失败";
                            }
                        }
                    },
                    {field : "addTime",title : "发送时间",width : 180,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    }
                ]]
            });
            $.fn.extOptions.dateRange("#targetTime","#startTime","#endTime","datetime");
        });
    </script>
</head>
<body class="tabs-body">
    <div class="easyui-layout" data-options="fit:true,doSize:false">
        <div data-options="region:'north',border:false" class="condition-bar">
            <div class="layout-norths">
                <@search placeholder="标示符、手机号" advance=true>
                    <li>
                        <span>状态</span>
                        <select name="state" title="发送状态">
                            <option value="">全部</option>
                            <option value="0">发送中</option>
                            <option value="1">已发送</option>
                            <option value="2">发送失败</option>
                        </select>
                    </li>
                    <li>
                        <span>时间</span>
                        <input title="发送时间" type="text"  id="targetTime" />
                        <input type="hidden" name="startTime" id="startTime" />
                        <input type="hidden" name="endTime" id="endTime" />
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