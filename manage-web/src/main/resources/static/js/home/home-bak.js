var menuTabs;//tabs菜单对象
var divTabs;//tabs对象
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
	   roleMenu();
    });

	
	//增加首页tabs信息
	addTabs("首页","/portal");
	
	var accordion = new Accordion($('#accordion'), false);
	
	$("#accordion li a").on("click",function(){
		$("#accordion li").removeClass("clicked");
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
	this.multiple = multiple || false;

	
	var links = this.el.find('.link');
	
	links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
};

Accordion.prototype.dropdown = function(e) {
	var $el = e.data.el;
		$this = $(this);
		$next = $this.next();

	$next.slideToggle();
	$this.parent().toggleClass('open');

	if (!e.data.multiple) {
		$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
	}
};

/**
 * 修改密码
 * @param isClose 是否关闭右上角按钮
 */
var changePwd = function(isClose){
    $.windowDialog({
        title : "修改密码",
        width : 400,
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
			$.post("/admin/logout",function(data){
				if(data.result){
					window.location.href = "/admin";
				}
			},"json");
		}
	});
};

/**
 * 当前用户拥有的菜单权限
 */
var roleMenu = function(){
    $.windowDialog({
        title : "菜单权限",
        width : 400,
        height : 400,
        closable:true,
        href : "/admin/mapper.system/p2p/userRoleMenuPage",
        buttons : [{
            text : '关闭',
            handler : function() {
                var f = $.windowDialog.handler.dialog("close");
            }
        }]
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

