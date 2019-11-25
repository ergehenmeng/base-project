<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>轮播图管理</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 400;

        var addTitle = "添加版本信息";
        var addUrl = "/business/version/add_page";

        var editTitle = "编辑版本信息";
        var editUrl = "/business/version/edit_page";

        var  delMsg = "确定要执行该操作吗";
        var delUrl = "/business/version/delete";

        var putAwayMsg = "确定要上架该版本吗";
        var putAwayUrl = "/business/version/put_away";

        var soldOutMsg = "确定要下架该版本吗";
        var soldOutUrl = "/business/version/sold_out";

        var address = "${address!}";
        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/business/version/list_page",
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
                            <@auth nid="versionManageEdit">
                                str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑"> 编辑</a>';
                            </@auth>
                            <@auth nid="versionManagePut">
                                if(row.state === 0){
                                    str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.confirm('+row.id+',putAwayUrl,putAwayMsg);" title="上架"> 上架</a>';
                                }
                            </@auth>
                            <@auth nid="versionManageDown">
                                if(row.state === 1){
                                    str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.confirm('+row.id+',soldOutUrl,soldOutMsg);" title="下架"> 下架</a>';
                                }
                            </@auth>
                            <@auth nid="versionManageDelete">
                                str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除"> 删除</a>';
                            </@auth>
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "classify",title : "类型",width : 80,align : "center",
                        formatter:function (value) {
                            if("ANDROID" === value){
                                return "安卓";
                            }else if("IOS" === value){
                                return "苹果";
                            }
                        }
                    },
                    {field : "version",title : "版本号",width : 100,align : "center"},
                    {field : "forceUpdate",title : "是否强更",width : 100,align : "center",
                        formatter:function (value) {
                            if(value){
                                return "是";
                            }
                            return "否";
                        }
                    },
                    {field : "state",title : "状态",width : 80,align : "center",
                        formatter:function (value) {
                            if(value === 1){
                                return "上架";
                            }
                            return "下架";
                        }
                    },
                    {field : "url",title : "下载地址",width : 550,align : "center",
                        formatter : function (value,rows) {
                            if(rows.classify === "IOS"){
                                return "<a href='"+ value +"' target='_blank'>" + value + "</a>";
                            }else{
                                return "<a href='javascript:void(0);' onclick='$.fn.downloadFun(\"" + address + value + "\");'>" + value + "</a>";
                            }
                        }
                    },
                    {field : "updateTime",title : "更新时间",width : 150,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 300 }
                ] ]
            });
        });
        $.fn.extOptions.date("#middleTime","datetime");
    </script>
</head>
<body class="tabs-body">
<div class="easyui-layout" data-options="fit:true,doSize:false">
    <div data-options="region:'north',border:false" class="condition-bar">
        <div class="layout-norths">
            <@search placeholder="版本号" advance=true>
                <li>
                    <span>客户端</span>
                    <select name="classify" title="客户端类型">
                        <option value="">全部</option>
                        <option value="ANDROID">安卓</option>
                        <option value="IOS">苹果</option>
                    </select>
                </li>
                <li>
                    <span>上架状态</span>
                    <select name="state" title="上架状态">
                        <option value="">全部</option>
                        <option value="1">上架</option>
                        <option value="0">下架</option>
                    </select>
                </li>
            </@search>
            <@auth nid = "versionManageAdd">
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