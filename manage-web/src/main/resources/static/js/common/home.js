$(function(){

	$("body").layout({
		fit:true
	});

	//增加首页tabs信息
	addTabs("首页","/portal");
    //导航菜单
    initMenuList();

    if (isInit) {
        $.messager.alert("提示","您的密码为原始密码,请先修改","warning",function(){
            changePwd(false);
        });
        return;
    }
    if (isLock) {
        doLockScreen();
    }
    $(".change-pwd-btn").on("click",function(){
        changePwd(true);
    });
    $(".logout-btn").on("click",function(){
        logout();
    });
    $(".lock-screen").on("click",function(){
        lockScreen();
    });
    $(".user-role-menu").on("click",function(){
        menuList();
    });
});

let initMenuList = function() {
    let $accordion = $("#accordion");
    new accordion($accordion, false);
    let $li = $accordion.find("li");
    $li.find("a").on("click",function(){
        $li.removeClass("clicked");
        $(this).parent("li").addClass("clicked");
        let url = $(this).attr("rel");
        addTabs($(this).text(),url,true);
    });
};

let accordion = function(el, multiple) {
	this.el = el || {};
	multiple = multiple || false;
    let links = this.el.find('.link');
	links.on('click', {el: this.el, multiple: multiple}, this.dropdown);
};

accordion.prototype.dropdown = function(e) {
    let $el = e.data.el;
    let	$next = $(this).next();
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
let changePwd = function(isClose){
    $.windowDialog({
        title : "修改密码",
        width : 400,
        height : 220,
        closable:isClose,
        href : "/main/change_password_page",
        buttons : [{
            text : '确定',
            handler : function() {
                $.windowDialog.handler.find('form').submit();
            }
        }]
    });
};

/**
 * 注销
 */
let logout = function(){
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

/**
 * 菜单列表
 */
let menuList = function(){
    $.fn.dataGridOptions.show(null,"菜单权限",350,400,"/main/role_menu_page");
};

let lockScreen = function () {
    $.ajax({
        url:"/lock",
        dataType:"json",
        type:"post",
        success:function(data){
            if(data.code === 200){
                doLockScreen();
            }
        },
        error:function(){
            $.messager.alert("服务器超时,请重试","warning");
        }
    });
};

let doLockScreen = function () {
    $.windowDialog({
        title : "锁屏",
        width : 400,
        height : 140,
        closable:false,
        href : "/main/lock_screen_page",
        buttons : [{
            text : '确定',
            handler : function() {
                $.windowDialog.handler.find("form").submit();
            }
        },{
            text : '注销',
            handler :logout
        }]
    });
};

let imagePreview = function(photoUrl,title){
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
