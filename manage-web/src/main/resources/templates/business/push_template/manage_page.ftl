<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>推送模板管理</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 450;


        var editTitle = "编辑模板";
        var editUrl = "/business/push_template/edit_page";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/business/push_template/list_page",
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
                            <@auth nid="pushTemplateEdit">
                                str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑"> 编辑</a>';
                            </@auth>
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "nid",title : "标示符",width : 150,align : "center"},
                    {field : "title",title : "标题",width : 150,align : "center"},
                    {field : "state",title : "状态",width : 100,align : "center",
                        formatter :function (value) {
                            if(value === 1){
                                return "开启";
                            }else if(value === 0){
                                return "关闭";
                            }
                        }
                    },
                    {field : "content",title : "内容",width : 500,align : "center",
                        formatter:function (value) {
                            return $.fn.dataGridOptions.maxLength(value);
                        }
                    },
                    {field : "tag",title : "标签",width : 100,align : "center"},
                    {field : "updateTime",title : "结束时间",width : 180,align : "center",
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
            <@search placeholder="标示符、标题、标签" advance=true >
                <li>
                    <span>状态</span>
                    <select name="state" title="状态">
                        <option value="">全部</option>
                        <option value="1">开启</option>
                        <option value="0">关闭</option>
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