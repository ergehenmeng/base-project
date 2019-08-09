//loadSuccess数据结果应为
//[{"hide":"","show":"1"},{....}]
$.fn.multipleSelect = function(options){
    var opts = $.extend({
        target:"#target",//隐藏值保存的地方
        url:"",
        loadSuccess:""//远程加载成功后的回调 用于格式化数据
    },options || {});
    var self = this;
    var s = ".multipleSelect";
    var checkboxSelect = {};

    /**
     * 远程加载数据列表
     * 格式必须为[{hide:1,show:'业务部'},{hide:2,show,'销售部'},{hide:3,show:'行政部'},]
     */
    checkboxSelect.getRemoteData = function () {
        if(opts.url){
            $.post(opts.url,{},function(data){
                checkboxSelect.data = data;
                if(typeof opts.loadSuccess === "function"){
                    checkboxSelect.data = opts.loadSuccess(data);
                }
                checkboxSelect.panel();
                checkboxSelect.render();
                checkboxSelect.bindMove();
                checkboxSelect.click();
            },"json");
        }
    };

    /**
     * 绑定panel移动事件
     * @param options
     */
    checkboxSelect.bindMove = function () {
        $(".panel-title").mousemove(function(){
            checkboxSelect.position();
        })
    };

    //checkbox选中
    checkboxSelect.add = function(hide,show){
        var hideValue = $(opts.target).val();//隐藏的值
        var showValue = self.val();//显示的值
        if(!hideValue){//无元素
            $(opts.target).val(hide);
            $(self).val(show);
        }else{
            $(opts.target).val(hideValue + "," + hide);
            self.val(showValue + "," + show);
        }
    };
    checkboxSelect.remove =function(hide,show){
        var hideValue = $(opts.target).val().split(",");//隐藏的值
        var showValue = self.val().split(",");//显示的值
        var index = hideValue.indexOf(hide);
        if(index !== -1 ){ //为空
            hideValue.removeValue(hide);
            $(opts.target).val(hideValue.join());
            showValue.removeValue(show);
            self.val(showValue.join());
        }
    };

    /**
     * 定位下拉复选框要显示的位置
     */
    checkboxSelect.position = function () {
        $(s).css("position","absolute").css("z-index",99999999);
        var x = self[0].getBoundingClientRect().left + document.documentElement.scrollLeft;
        var y = self[0].getBoundingClientRect().bottom + document.documentElement.scrollTop;
        $(s).css("left",x);
        $(s).css("top",y);
    };

    /**
     * 渲染存放checkbox的面板
     * @param options
     */
    checkboxSelect.panel = function () {
        if($(s).length === 0){
            $("<div class='multipleSelect' />").appendTo("body").append("<ul id='selectOption'></ul>");
        }
    };

    /**
     * 渲染checkbox列表
     */
    checkboxSelect.render = function () {
        if(checkboxSelect.data != null){
            var target =  checkboxSelect.getTargetVal();
            $("#selectOption").html("");
            $.each(checkboxSelect.data,function(i,v){//渲染隐藏值
                var checked = checkboxSelect.checked(target,v.hide);
                checkboxSelect.renderShowValue(checked,v.show);
                $("#selectOption").append("<li><label><input value="+ v.hide +" type='checkbox' " + checked + "/>" + v.show + "</label></li>");
            });
            checkboxSelect.binding();
        }
    };
    /**
     * 渲染显示值
     * @param value
     */
    checkboxSelect.renderShowValue = function (checked,value) {
        if(checked){
            var showValue = $(self).val();
            if(showValue){
                $(self).val(showValue + "," + value);
            }else{
                $(self).val(value);
            }
        }
    };

    /**
     * 绑定checkbox点击事件
     */
    checkboxSelect.binding = function () {
        $("#selectOption input").off().on("click",function(event){
            if($(this).prop("checked")){
                checkboxSelect.add($(this).val(),$(this).parent().text().trim());
            }else{
                checkboxSelect.remove($(this).val(),$(this).parent().text().trim());
            }
        });
    };
    /**
     *
     * 根据给定的v 查看是否在array中存在该对象
     * 检查该复选框是否为需要选中
     * @param array
     * @param v
     * @returns {string}
     */
    checkboxSelect.checked = function(array,v){
        var check = "";
        if(!$.isEmptyObject(array)){
            $.each(array,function(i,value){
                if(value ==  v){
                    check =  "checked=checked";
                    return "";
                }
            })
        }
        return check;
    };

    /**
     * 获取存放隐藏值的input框的值
     * @param target
     * @returns
     */
    checkboxSelect.getTargetVal = function () {
        var targetValue = $(opts.target).val();
        if(targetValue){
            return targetValue.split(",");
        }
        return new Array();
    };

    self.getRemoteData = checkboxSelect.getRemoteData;
    if(checkboxSelect.getRemoteData){
        checkboxSelect.getRemoteData();
    }
    /**
     * 绑定下拉框点击事件
     */
    checkboxSelect.click = function(){
        $(self).on("click",function(event){
            checkboxSelect.position();
            if($(s).is(":hidden")){
                $(s).show();
                event.stopPropagation();
            }
        });
    };

    $(document).on("click",s,function(e) {
        e.stopPropagation();
    });
    $(document).click(function(event) {
        $(s).hide();
    });
    return self;
};



//查看数组中是否存在某个元素
Array.prototype.indexOf=function(v){
    for(var i = 0;i<this.length;i++){
        if(this[i]==v){
            return i ;
        }
    }
    return -1;
};
Array.prototype.removeValue = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};


















