<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>任务日志</title>
    <#include "../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 420;
        var winHeight = 500;

        var title = "错误详情";
        var url = "/business/task_log/error_msg";

        $(function() {
            dataGrid = $.fn.dataGridOptions.dataGrid("#dataGrid",{
                url : "/business/task_log/list_page",
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
                            if(row.state === 0){
                                str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.show('+row.id+',title,winWidth,winHeight,url);" title="错误信息">错误</a>';
                            }
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "nid",title : "标示符",width : 150,align : "center"},
                    {field : "beanName",title : "bean名称",width : 150,align : "center"},
                    {field : "state",title : "执行结果",width : 150,align : "center",
                        formatter : function (value) {
                            if(value === 1){
                                return "成功";
                            }
                            return "失败";
                        }
                    },
                    {field : "ip",title : "机器ip",width : 80,align : "center" },
                    {field : "startTime",title : "开始时间",width : 180,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    },
                    {field : "endTime",title : "结束时间",width : 180,align : "center",
                        formatter : function(value) {
                            return getLocalTime(value, 4);
                        }
                    }
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
            <div class="right">
                <a href="#" class="search-btn" onclick="$.fn.dataGridOptions.editFun(0,addTitle,winWidth,winHeight,addUrl);"><i class="fa fa-plus"></i>&nbsp;添加</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
</html>