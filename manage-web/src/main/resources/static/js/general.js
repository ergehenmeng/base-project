//全局通用JS
//全局默认页容量
var pageSize = 20;
//全局默认页容量列表
var pageList = [20, 30, 40, 50 ];
$.fn.dataGridOptions = function(){};
$.fn.treeGridOptions = function(){};
$.fn.extOptions = function(){};

/**
 * easyUI列表提炼
 * @param element 元素id
 * @param opts 配置项
 * @returns {*|jQuery} dataGrid对象
 */
$.fn.dataGridOptions.dataGrid = function(element,opts){
    var options = $.extend({
        border: false,
        fit: true,
        fitColumns: false,
        idField: 'id',
        nowrap: false,//可以换行显示
        pagination: true,
        pageSize: pageSize,
        pageList: pageList,
        singleSelect: true,
        loadFilter: $.fn.extOptions.loadFilter,
        onLoadSuccess: $.fn.dataGridOptions.tips
    },opts);
    return $(element).datagrid(options);
};

$.fn.dataGridOptions.tips = function(data){
    $(".dataGrid-tips").poshytip({
        className: 'tip-yellowsimple',
        fade: false,
        slide: false,
        alignX: 'center'
    });
};

$.fn.extOptions.loadFilter = function(data){
    if(data.code === 200){
        return data.data;
    }else{
        parent.$.messager.alert("提示",data.msg || "系统繁忙","error");
    }
};

/**
 * 格式化数据结果
 * @param value 源值
 * @param maxLength 超过该长度后不会显示
 */
$.fn.dataGridOptions.format = function(value,maxLength){
    if(!maxLength){
        maxLength = 40;
    }
    if(value && value.length > maxLength){
        return '<span title=\''+ newLine(value,maxLength) +'\' class="dataGrid-tips">' + value.substring(0,maxLength) + '...</span>';
    }
    return value;
};

/**
 * 自动换行
 * @param value 值
 * @return {*}
 * @param maxLength 单行最大长度
 */
function newLine(value,maxLength){
    var valueLength = value.length;
    var loop = valueLength / maxLength + 1;
    if(loop === 1){
        return value;
    }
    var result = "";
    for (var i=0;i < loop;i++){
        result += value.substring(maxLength * i,maxLength * (i+1)) + "\n" ;
    }
    return result;
}
/**
 * easyUI列表提炼
 * @param element 元素id
 * @param opts 配置项
 * @returns {*|jQuery} dataGrid对象
 */
$.fn.treeGridOptions.treeGrid = function(element,opts){
    var options = $.extend({
        border: false,
        animate: true,
        fit: true,
        fitColumns: false,
        idField: 'id',
        treeField: 'text',
        nowrap: false,
        singleSelect: true,
        loadFilter: $.fn.extOptions.loadFilter
    },opts);
    return $(element).treegrid(options);
};
/**
 * data弹出层
 */
$.fn.dataGridOptions.editFun = function(id,title,width,height,url,data){
	parent.$.windowDialog({
		title: title,
		width: width,
		height: height,
		href: formatUrl(id,url,data),
		buttons: [ {
			text: '确定',
			handler: function() {
				if(dataGrid !== undefined && dataGrid !== null){
                    //必须要设定好这个该dataGridGrid,为后续点击提交表单
					parent.$.windowDialog.openner_dataGrid = dataGrid;
				}
				var f = parent.$.windowDialog.handler.find('form');
				f.submit();
			}
		} ]
	});
};

$.fn.dataGridOptions.show = function(id,title,width,height,url,data){
    parent.$.windowDialog({
        title: title,
        width: width,
        height: height,
        href: formatUrl(id,url,data),
        buttons: [ {
            text: '关闭',
            handler: function() {
                parent.$.windowDialog.handler.dialog('close');
            }
        } ]
    });
};

function formatUrl(id,url,data){
	url = url + "?id=" +id;
	if(data !== undefined){
		url = url + "&" + eachJson(data);
	}
	return url;
}

/**
 * 询问操作
 */
$.fn.dataGridOptions.confirm = function(id,url,msg,data){
	var delMsg = msg || "确定要执行该项操作吗?";
	parent.$.messager.confirm("提示",delMsg,function(r){
		if(r){
			data = $.extend({},{id:id},data);
			$.ajax({
               type: "post",
               dataType: "json",
               data: data,
               url: url,
               success: function(data){
                   if(data.code === 200){
                       dataGrid.datagrid('reload');
                   }else{
                       parent.$.messager.alert("提示",data.msg || "操作失败,请稍后再试","error");
                   }
               },
               error: function(){
                   parent.$.messager.alert("提示","服务器超时,请重试","error");
               }
            });
		}
	});
};



/**
 * 表单提交
 */
$.fn.dataGridOptions.formSubmit = function(formId,url,success){
	var successMsg = success || "操作成功";
	$(formId).form({
		url: url,
		onSubmit: function(){
			parent.$.messager.progress({
	            title: '提示',
	            text: '数据处理中，请稍后....'
	        });				
	        var isValid = $(this).form('validate');
	        if (!isValid) {
	            parent.$.messager.progress('close');
	        }
	        return isValid;
		},
		success: function(data){
			 parent.$.messager.progress('close');
		     data = (typeof data === 'object') ? data: $.parseJSON(data);
		     if(data.code === 200){
                 parent.$.windowDialog.handler.dialog('close');
                 parent.$.messager.alert('提示', successMsg, 'info');
		         parent.$.windowDialog.openner_dataGrid.datagrid('reload');
		     }else{
                 parent.$.messager.alert('提示', data.msg, 'warning');
		     }
		}
	});
};
/**
 * 表单提交 与上面的区别,上面是针对列表页面的弹出表单,
 * 而该方法仅仅是普通的提交表单,在提交成功后,不会刷新datagrid或者treegrid等
 * @param formId
 * @param url
 * @param callback 成功回调函数
 */
$.fn.dataGridOptions.submit = function(formId,url,callback){
    $(formId).form({
        url:url,
        onSubmit:function(){
            parent.$.messager.progress({
                title: '提示',
                text: '数据处理中，请稍后....'
            });
            var isValid = $(this).form('validate');
            if (!isValid) {
                parent.$.messager.progress('close');
            }
            return isValid;
        },
        success:function(data){
            parent.$.messager.progress('close');
            data = (typeof data === 'object') ? data: $.parseJSON(data);
            if(data.code === 200){
                parent.$.messager.alert('提示', data.msg, 'info',callback);
            }else{
                parent.$.messager.alert('提示', data.msg, 'warning');
            }
        }
    });
};

/**
 * treeGrid的询问操作 一般为删除
 * @param id 待删除的数据主键
 * @param url 删除的地址
 * @param msg 删除时提示语
 * @param data 附带的其他参数
 */
$.fn.treeGridOptions.confirm = function(id,url,msg,data){
    var confirmMsg = msg || "确定要执行该项操作吗?";
    parent.$.messager.confirm("提示",confirmMsg,function(r){
        if(r){
            data = $.extend({},{id:id},data);
            $.ajax({
                type:"post",
                url:url,
                data:data,
                dataType:"json",
                success:function(data){
                    if(data.code === 200){
                        treeGrid.treegrid('reload');
                    }else{
                        parent.$.messager.alert("提示",data.msg || "操作失败,请稍后再试","error");
                    }
                },
                error:function(){
                    parent.$.messager.alert('提示', "请求数据失败,请联系管理人员", 'error');
                }
            });
        }
    });
};
/**
 * 表单新增或编辑
 */
$.fn.treeGridOptions.editFun = function(id,title,width,height,url,data){
	parent.$.windowDialog({
		title: title,
		width: width,
		height: height,
		href: formatUrl(id,url,data),
		buttons: [ {
			text: '确定',
			handler: function() {
				parent.$.windowDialog.openner_treeGrid = treeGrid;//必须要设定好这个该dataGridGrid,为后续点击提交表单
				var f = parent.$.windowDialog.handler.find('form');//默认查找form表单 如果多个则会触发多个表单的提交
				f.submit();
			}
		} ]
	});
};

/**
 * 表单提交
 */
$.fn.treeGridOptions.formSubmit = function(formId,url,success){
	var successMsg = success || "操作成功";
	$(formId).form({
		url:url,
		onSubmit:function(){
			
			parent.$.messager.progress({
	            title: '提示',
	            text: '数据处理中，请稍后....'
	        });				
	        var isValid = $(this).form('validate');
	        if (!isValid) {
	            parent.$.messager.progress('close');
	        }
	        return isValid;
		},
		success:function(data){
			 parent.$.messager.progress('close');
		     data = (typeof data === 'object') ? data: $.parseJSON(data);
		     if(data.code === 200){
		    	 parent.$.messager.alert('提示', successMsg, 'info');
		         parent.$.windowDialog.handler.dialog('close');
		         parent.$.windowDialog.openner_treeGrid.treegrid('reload');
		     }else{
                 parent.$.messager.alert('提示', data.msg, 'warning');
		     }
		}
	});
};

/**
 * 普通get请求
 * @param url
 */
$.fn.dataGridOptions.get = function(url){
    parent.$.messager.progress({
        title: '提示',
        text: '数据处理中，请稍后....'
    });
    $.ajax({
        type:"get",
        url:url,
        dataType:"json",
        success:function(data){
            parent.$.messager.progress('close');
            if(data.code === 200){
                parent.$.messager.alert('提示', data.msg, 'info');
                dataGrid.datagrid('reload');
            }else{
                parent.$.messager.alert('提示', data.msg, 'warning');
            }
        },
        error:function(){
            parent.$.messager.progress('close');
            parent.$.messager.alert('提示', "请求数据失败,请联系管理人员", 'error');
        }
    });
};

/**
 * 复选框选中提交
 * @param url 提交地址
 * @param unSelectMsg 未选中时,提示信息
 * @param rowName 选择的列名,同时该列列名作为key传递给后台
 * @param success 成功信息
 * @return {boolean}
 */
$.fn.dataGridOptions.select = function(url,unSelectMsg,rowName,success){
    var rows = dataGrid.datagrid("getSelections");
    var successMsg = success || "操作成功";
    if(rows.length <= 0){
        parent.$.messager.alert("提示",unSelectMsg,"warning");
        return false;
    }
    var rowArray = [];
    $.each(rows,function(i,row){
        rowArray.push(row[rowName]);
    });
    var join = rowArray.join(",");
    parent.$.messager.progress({
        title: '提示',
        text: '数据处理中，请稍后....'
    });

    var data = {};
    data[rowName] = join;
    $.ajax({
        type: "post",
        dataType: "json",
        url: url,
        data: data,
        success: function (data) {
            parent.$.messager.progress('close');
            if(data.code === 200){
                parent.$.messager.alert('提示', successMsg, 'info');
                dataGrid.datagrid("clearSelections");
                dataGrid.datagrid('reload');
            }else{
                parent.$.messager.alert('提示', data.msg, 'warning');
            }
        },
        error:function(){
            parent.$.messager.progress('close');
            parent.$.messager.alert('提示', "请求数据失败,请联系管理人员", 'error');
        }
    });
};

/**
 * 普通dataGrid刷新
 * @param formId
 */
$.fn.dataGridOptions.searchFun = function (formId) {
    dataGrid.datagrid('load', $.serializeObject($(formId)));
};

//树形数据查询
$.fn.treeGridOptions.searchFun = function (formId) {
    treeGrid.datagrid('load', $.serializeObject($(formId)));
};

/**
 * 将列表转换为属性结构
 * @param rows 列表 Array
 * @return {Array} 生成树形结构数据,需要选中的自动勾选上
 * @param id 当前节点id key 字符串
 * @param text 节点名称 key 字符串
 * @param pid 父节点id 字符串
 * @param rootValue 根节点值,注意数据类型一定要和数据库保持一致
 */
$.fn.treeGridOptions.dataFilter = function(rows,id,text,pid,rootValue){
    var nodes = [];
    if(!rows) return nodes;

    for(var s = 0,lens = rows.length; s < lens;s++){
        //一级节点
        var rootRow = rows[s];
        if(rootRow[pid] === rootValue){
            rootRow["text"] = rootRow[text];
            nodes.push(rootRow);
        }
    }
    var topNodes = [];
    for(var x = 0,len = nodes.length; x < len; x++){
        //数组复制引用会改变原数组数据,因此下面增加子元素数据是有效的
        topNodes.push(nodes[x]);
    }

    while(topNodes.length){
        var node = topNodes.shift();
        for(var i = 0,rowLen = rows.length;i < rowLen; i++){
            var row = rows[i];
            if (row[pid] === node[id]){
                var child = $.extend(row,{});
                    child["text"] = child[text];
                if (node.children){
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                //将子菜单放入数组中,查看其是否存在子元素
                topNodes.push(child);
            }
        }
    }
    return nodes;
};

/**
 * 选中指定的节点
 * @param treeObj tree对象
 * @param ids 待选中的节点id 逗号分割
 */
$.fn.treeGridOptions.checkbox = function (treeObj,ids){
    if(ids){
        var splitArray = ids.split(",");
        $.each(splitArray,function (i,id) {
            var node = treeObj.tree('find', id);
            if (node) {
                var isLeaf = treeObj.tree('isLeaf', node.target);
                if (isLeaf)  {
                    treeObj.tree('check', node.target);
                }
            }
        });
    }
};

/**
 * 方法作用:点击树形复选框时返回所有选中的值
 * @param treeObj treeObj必须在调用该方法的地方声明
 * @return {string} 选中的值
 */
$.fn.treeGridOptions.checkNode = function(treeObj){
	var nodes = treeObj.tree("getChecked",['checked','indeterminate']);
	if(nodes !== null && nodes.length > 0){
		var nodeId = "";
		$.each(nodes,function(i,node){
			nodeId += "," + node.id;
		});
		return nodeId.substring(1);
	}
	return "";
};

/**
 * 添加字符串方法 开始
 * @param str 字符串
 * @return {boolean} 是否开头
 */
String.prototype.startWith=function(str){    
	  var reg=new RegExp("^" + str);
	  return reg.test(this);       
	} ;
/**
 * 添加字符串方法 结束
 * @param str 字符串呢
 * @return {boolean} 是否结尾
 */
String.prototype.endWith=function(str){    
  var reg=new RegExp(str + "$");
  return reg.test(this);       
};

/**
 * 所有弹框
 * @param opts panel参数,详细配置见easyUI API
 * @returns {jQuery} jquery panel对象
 */
$.windowDialog = function(opts){
	if($.windowDialog.handler === undefined){
		var options = $.extend({
			title:"easyUI窗口",
			width:680,
			height:480,
			modal:true,
			border:false,
			onClose:function(){
			    //防止部分第三方插件在panel关闭时不会自定关闭
			    $("body").click();
				$.windowDialog.handler = undefined;
				$(this).dialog('destroy');
			}
		},opts);
		return $.windowDialog.handler = $("<div/>").dialog(options);
	}
};
/**
 * 清空查询条件
 * @param formId
 */
$.fn.dataGridOptions.cleanFun = function(formId){
    $(formId + ' input').val('');
    dataGrid.datagrid('load', {});
    $("#showAdw")[0].reset();
};

/**
 * 循环json并以url形式返回
 * @returns
 */
function eachJson(data){
	var url = "";
	$.each(data,function(name,value){
		url += ("&" + name + "=" + value);
	});
	return url;
}


/**
 * 添加tabs
 */
function addTabs(title,url,closable){
	//判断该tabs是否已经打开
    var $tabs = $("#div_tabs");
	var isOpen = $tabs.tabs("exists",title);
	if(isOpen){
        $tabs.tabs("select",title);//选中点击的tabs
        $tabs.tabs("getSelected").children("iframe").attr("src", url);//重新加载数据
	}else{
        $tabs.tabs("add",{
			title:	title,
			content: createIframe(url),
			closable: closable,
			fit: true,
			width: $tabs.parent().width()
		});
	}
}

/**
 * 创建iframe
 * @param url
 * @returns
 */
function createIframe(url){
	return '<iframe allowtransparency="true" scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:99%"></iframe>';
}


//日期显示的格式化	
Date.prototype.format = function (format){
    var o = {
        "M+": this.getMonth() + 1, //month 
        "d+": this.getDate(),    //day 
        "h+": this.getHours(),   //hour 
        "m+": this.getMinutes(), //minute 
        "s+": this.getSeconds(), //second
        //刻度
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds() //millisecond 
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o){
        if (new RegExp("(" + k + ")").test(format)){
            format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/**
 * 给定日期或者时间戳格式化日期
 * @param value Date或时间戳(毫秒值)
 * @param type 格式化类型 1: yyyy年MM月dd日 2: yyyy年MM月dd日 hh:mm:ss 3: yyyy-MM-dd 4:yyyy-MM-dd hh:mm:ss
 * @returns {*}
 */
var getLocalTime = function(value,type) {
    if (!value) {
        return '';
    }
	var dt;
	if (value instanceof Date) {
	    dt = value;
	}else {
	    dt = new Date(value);
	}
	 switch (type){
	 case 1:
		 return dt.format("yyyy年MM月dd日");
	 case 2:
		 return dt.format("yyyy年MM月dd日 hh:mm:ss");
	 case 3:
		 return dt.format("yyyy-MM-dd");
	 case 4:
		 return dt.format("yyyy-MM-dd hh:mm:ss");
	 }
};

/**
 * laydate日期范围选择
 * @param elem 需要绑定的元素
 * @param startElem 开始时间绑定对象(hidden)
 * @param endElem 结束时间绑定对象(hidden)
 * @param type 数据类型
 * year:yyyy,
 * month:yyyy-MM,
 * date:yyyy-MM-dd(默认),
 * datetime:yyyy-MM-dd HH:mm:ss
 */
$.fn.extOptions.dateRange = function (elem,startElem,endElem,type){
    var typeJson = {
        year: 'yyyy',
        month: 'yyyy-MM',
        date: 'yyyy-MM-dd',
        time: 'HH:mm:ss',
        datetime: 'yyyy-MM-dd HH:mm:ss'
    };
    laydate.render({
        elem: elem,
        theme: "molv",
        format: typeJson[type],
        range: "~",
        type: type,
        done: function(value){
            var dateSplit = value.split(" ~ ");
            $(startElem).val(dateSplit[0]);
            $(endElem).val(dateSplit[1]);
        }
    });
};