<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>轮播图管理</title>
    <#include "../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 500;

        var addTitle = "添加轮播图";
        var addUrl = "/banner/add_page";

        var editTitle = "编辑轮播图";
        var editUrl = "/banner/edit_page";

        var  delMsg = "确定要执行该操作吗";
        var delUrl = "/banner/delete";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/banner/list_page",
                columns : [ [
                    {
                        field : "icon-action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            <@auth nid='bannerManageEdit'>
                                str += '<a href="javascript:void(0);" class="edit" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑轮播图"></a>&nbsp;';
                            </@auth>
                            <@auth nid='bannerManageDelete'>
                                str += '<a href="javascript:void(0);" class="delete" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除轮播图"></a>';
                            </@auth>
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "title",title : "标题",width : 150,align : "center",
                        formatter:function(value,rows){
                            return '<a href="javascript:void(0);" onclick="parent.imagePreview(\''+ rows.imgUrl +'\');">' + value + '</a>';
                        }
                    },
                    {field : "clientType",title : "客户端",width : 80,align : "center"},
                    {field : "classifyName",title : "轮播图类型",width : 100,align : "center"},
                    {field : "sort",title : "排序",width : 60,align : "center" },
                    {field : "startTime",title : "开始时间",width : 150,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "updateTime",title : "结束时间",width : 150,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "click",title : "是否可点击",width : 80,align : "center",
                        formatter :function (value) {
                            if(value){
                                return "是";
                            }else{
                                return "否";
                            }
                        }
                    },
                    {field : "turnUrl",title : "跳转地址",width : 400,align : "center",
                        formatter:function (value) {
                            return $.fn.dataGridOptions.maxLength(value);
                        }
                    },
                    {field : "updateTime",title : "更新时间",width : 150,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 200,
                        formatter:function (value) {
                            return $.fn.dataGridOptions.maxLength(value);
                        }
                    }
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
            <@search placeholder="标题" advance=true>
                <li>
                    <span>客户端</span>
                    <select name="clientType" title="客户端类型">
                        <option value="">全部</option>
                        <option value="PC">pc</option>
                        <option value="ANDROID">android</option>
                        <option value="IOS">ios</option>
                        <option value="H5">h5</option>
                        <option value="WECHAT">微信小程序</option>
                        <option value="ALIPAY">支付宝小程序</option>
                    </select>
                </li>
                <li>
                    <span>分类</span>
                    <@select name="classify" total="true"  title="轮播图分类" nid="banner_classify"/>
                </li>
                <li>
                    <span>播放时间</span>
                    <input title="在此时间内有效的录播信息" type="text" name="middleTime" id="middleTime" />
                </li>
            </@search>
            <@auth nid='bannerManageAdd'>
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