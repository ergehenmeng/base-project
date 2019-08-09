$(function(){
    if(isInit){
        $.messager.alert("提示","您的密码为原始密码,请先修改","warning",function(){
            changePwd(false);
        });
    }

	$("body").layout({
		fit:true
	});
	
	$(".change_pwd_btn").on("click",function(){
		changePwd(true);
	});
	
	$(".logout_btn").on("click",function(){
		logout();
	});

	$(".user_role_menu").on("click",function(){
        $.fn.dataGridOptions.show(null,"菜单权限",350,400,"/public/home/role_menu_page");
    });

	//增加首页tabs信息
	addTabs("首页","/portal");
	var $accordion = $("#accordion");
	new Accordion($accordion, false);

    var $li = $accordion.find("li");
    $li.find("a").on("click",function(){
        $li.removeClass("clicked");
		$(this).parent("li").addClass("clicked");
		var url = $(this).attr("rel");
		addTabs($(this).text(),url,true);
	});

    /**
     * 锁屏
     */
	$(document).keydown(function(event){
        if(event.ctrlKey === true && event.keyCode === 76){
            lockScreen();
        }
    });

});


var Accordion = function(el, multiple) {
	this.el = el || {};
	multiple = multiple || false;

	var links = this.el.find('.link');
	
	links.on('click', {el: this.el, multiple: multiple}, this.dropdown);
};

Accordion.prototype.dropdown = function(e) {
	var $el = e.data.el;
	var	$next = $(this).next();

	$next.slideToggle("fast");
    $(this).parent().toggleClass('open');

	if (!e.data.multiple) {
		$el.find('.submenu').not($next).slideUp("fast").parent().removeClass('open');
	}
};

/**
 * 修改密码
 * @param isClose 是否显示关闭按钮
 */
var changePwd = function(isClose){
    $.windowDialog({
        title : "修改密码",
        width : 480,
        height : 250,
        closable:isClose,
        href : "/public/home/change_password_page",
        buttons : [{
            text : '确定',
            handler : function() {
                var f = $.windowDialog.handler.find('form');
                f.submit();
            }
        }]
    });
};

/**
 * 注销
 */
var logout = function(){
	$.messager.confirm("提示","您确定要退出该系统吗?",function(r){
		if(r){
		    $.ajax({
                url:"/logout",
                dataType:"json",
                type:"post",
                success:function(data){
                    if(data.code === 200){
                        window.location.href = "/";
                    }
                },
                error:function(){
                    $.messager.alert("服务器超时,请重试","warning");
                }
            });
		}
	});
};



var lockScreen = function () {
    $.windowDialog({
        title : "锁屏",
        width : 400,
        height : 140,
        closable:false,
        href : "/admin/lockScreenPage",
        buttons : [{
            text : '确定',
            handler : function() {
                var f = $.windowDialog.handler.find("form");
                f.submit();
            }
        },{
            text : '注销',
            handler :logout
        }]
    });
};
var imagePreview = function(photoUrl,title){
    layer.photos({
        photos: {
            title:title,
            data:[{
                src:photoUrl
            }]
        },
        anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
    });
};
