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
                        field : "action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            str += '<dl>';
                            str += '<dt><a href="javascript:void(0);">详情<i class="fa fa-angle-down fa-fw"></i></a></dt>';
                            str += '<dd>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.show('+row.id+',title,winWidth,winHeight,url);" title="编辑用户信息"> 编辑</a>';
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "classify",title : "分类",width : 100,align : "center",
                        formatter:function(value){
                            if(value === 0){
                                return "更新";
                            }else if(value === 1){
                                return "删除";
                            }else if(value === 2){
                                return "查询";
                            }else if(value === 3){
                                return "新增";
                            }else{
                                return "组合";
                            }
                        }
                    },
                    {field : "url",title : "请求地址",width : 280,align : "center"},
                    {field : "operatorName",title : "操作人",width : 100,align : "center"},
                    {field : "request",title : "请求参数",width : 250,align : "center",
                        formatter:function(value){
                            return $.fn.dataGridOptions.format(value);
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
                    <span>分类</span>
                    <select name="classify" id="classify" title="分类">
                        <option value="">全部</option>
                        <option value="0">更新</option>
                        <option value="1">删除</option>
                        <option value="2">查询</option>
                        <option value="3">添加</option>
                        <option value="4">组合</option>
                    </select>
                </li>
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