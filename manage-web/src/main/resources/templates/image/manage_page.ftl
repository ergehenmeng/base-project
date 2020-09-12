<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>图片管理</title>
    <#include "../resources.ftl">
    <script type="text/javascript">
        var dataGrid;
        var winWidth = 420;
        var winHeight = 300;

        var addTitle = "添加图片";
        var addUrl = "/image/add_page";

        var editTitle = "编辑图片";
        var editUrl = "/image/edit_page";

        var delMsg = "删除图片可能导致页面展示问题,确定要执行该操作";
        var delUrl = "/image/delete";

        $(function() {
            var address = '${address!}';
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/image/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = ''
                            <@auth nid='imageManageEdit'>
                                str += '<a href="javascript:void(0);" class="edit" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑图片"></a>&nbsp;';
                            </@auth>
                            <@auth nid='imageManageDelete'>
                                str += '<a href="javascript:void(0);" class="delete" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除图片"></a>&nbsp;';
                            </@auth>
                            return str;
                        }
                    },
                    {field : "title",title : "名称",width : 150,align : "center"},
                    {field : "classifyName",title : "分类",width : 80,align : "center"},
                    {field : "path",title : "路径",width : 700,align : "center",
                        formatter:function(value,rows){
                            return '<a href="javascript:void(0);" onclick="parent.imagePreview(\''+ value +'\');">' + address + value + '</a>';
                        }
                    },
                    {field : "size",title : "大小",width : 80,align : "center",
                        formatter:function(value){
                            return (parseFloat(value) / (1024 * 1024)).toFixed(2) + "M";
                        }
                    },
                    {field : "addTime",title : "添加时间",width : 150,align : "center",
                        formatter:function(value){
                            return getLocalTime(value,4);
                        }
                    },
                    {field : "updateTime",title : "更新时间",width : 150,align : "center",
                        formatter:function(value){
                            return getLocalTime(value,4);
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 300 }
                ] ]
            });
        });

    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="名称、备注" advance=true>
                <li>
                    <span>图片分类</span>
                    <@select name="classify" total="true"  title="图片分类" nid="image_classify"/>
                </li>
            </@search>
            <@auth nid='imageManageAdd'>
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