<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>短信模板管理</title>
    <#include "../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 350;


        var editTitle = "编辑模板";
        var editUrl = "/sms_template/edit_page";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/sms_template/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            <@auth nid="smsTemplateEdit">
                                str += '<a href="javascript:void(0);" class="edit" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑短信模板"></a>';
                            </@auth>
                            return str;
                        }
                    },
                    {field : "nid",title : "标示符",width : 150,align : "center"},
                    {field : "content",title : "内容",width : 600,align : "center",
                        formatter:function (value) {
                            return $.fn.dataGridOptions.maxLength(value);
                        }
                    },
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
            <@search placeholder="标示符" />
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
</html>