<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>公告管理</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 1000;
        var winHeight = 500;

        var addTitle = "添加公告";
        var addUrl = "/business/notice/add_page";

        var editTitle = "编辑公告";
        var editUrl = "/business/notice/edit_page";

        var  delMsg = "确定要执行该操作吗";
        var delUrl = "/business/notice/delete";

        var publishMsg = "确定要发布该公告吗";
        var publishUrl = "/business/notice/publish";

        var cancelPublishMsg = "确定要取消该公告吗";
        var cancelPublishUrl = "/business/notice/cancel_publish";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/business/notice/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            <@auth nid="noticeManageEdit">
                                str += '<a href="javascript:void(0);" class="edit" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑公告信息"></a>&nbsp;';
                            </@auth>
                            <@auth nid="noticeManagePublish">
                                if(row.state === 0){
                                    str += '<a href="javascript:void(0);" class="up" onclick="$.fn.dataGridOptions.confirm('+row.id+',publishUrl,publishMsg);" title="发布公告"></a>&nbsp;';
                                }
                            </@auth>
                            <@auth nid = "noticeManageCancel">
                                if(row.state === 1){
                                    str += '<a href="javascript:void(0);" class="down" onclick="$.fn.dataGridOptions.confirm('+row.id+',cancelPublishUrl,cancelPublishMsg);" title="取消发布"></a>&nbsp;';
                                }
                            </@auth>
                            <@auth nid="noticeManageDelete">
                                str += '<a href="javascript:void(0);" class="delete" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除公告"></a>&nbsp;';
                            </@auth>
                            return str;
                        }
                    },
                    {field : "title",title : "标题",width : 150,align : "center",
                        formatter :function (value,row) {
                            return "<a href='/business/notice/preview?id=" + row.id + "' target='_blank'>" + value + "</a>";
                        }
                    },
                    {field : "classifyName",title : "类型",width : 150,align : "center"},
                    {field : "state",title : "状态",width : 180,align : "center",
                        formatter : function(value) {
                            if(value === 1){
                                return "已发布";
                            }else if(value === 0){
                                return "待发布";
                            }
                        }
                    },
                    {field : "updateTime",title : "更新时间",width : 180,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    }
                ] ]
            });
        });
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true,doSize:false">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="标题" advance=true>
                <li>
                    <span>状态</span>
                    <select name="state" title="发布状态">
                        <option value="">全部</option>
                        <option value="1">已发布</option>
                        <option value="0">未发布</option>
                    </select>
                </li>
                <li>
                    <span>类型</span>
                    <@select name="classify" total="true"  title="公告类型" nid="notice_classify"/>
                </li>
            </@search>
            <@auth nid="noticeManageAdd">
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