var menuTabs;//tabs菜单对象
var divTabs;//tabs对象
$(function(){
	
	$("body").layout({
		fit:true
	});
	
	$(".change_pwd_btn").on("click",function(){
		changePwd();
	});
	
	$(".logout_btn").on("click",function(){
		logout();
	});
	
	//增加首页tabs信息
	addTabs("首页","/portal.ftl");
	
	var accordion = new Accordion($('#accordion'), false);
	
	$("#accordion li a").on("click",function(){
		$("#accordion li").removeClass("clicked");
		$(this).parent("li").addClass("clicked");
		var url = $(this).attr("rel");
		addTabs($(this).text(),url,true);
	});
	
	divTabs = $('#div_tabs').tabs({
		onContextMenu : function(e, title) {
			e.preventDefault();
			menuTabs.menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		}
	});
	menuTabs = $("#main-menu").menu({
		onClick:function(item){
			var curTabTitle = $(this).data('tabTitle');
			closeTabs(item.id,curTabTitle);
		}
	});
});

var homePage = "首页";

function closeTabs(type,curTabTitle){
	var curTab =divTabs.tabs('getTab', curTabTitle);//当前鼠标所在的
	var allTabs = divTabs.tabs("tabs");//所有的tabs;
	var allTabTitle = [];
	
	$.each(allTabs,function(i,n){
		allTabTitle.push($(n).panel("options").title);
	});
	
	if(type == "refresh"){
		divTabs.tabs('getTab', curTabTitle).panel('refresh');
	}else if(type == "close"){
		if(curTab.panel("options").closable) {
			divTabs.tabs("close",curTabTitle);
		};
	}else if(type == "closeright"){
		var index = divTabs.tabs("getTabIndex",curTab);
		$.each(allTabTitle,function(i,n){
			if(i > index){
				if(n != homePage){
					divTabs.tabs("close",n);
				}
			}
		});
	}else if(type == "closeother"){
		var tabs = divTabs.tabs("tabs");
		$.each(allTabTitle,function(i,n){
			if(n != homePage && n != curTabTitle){
				divTabs.tabs("close",n);
			}
		});
	}else if(type == "closeall"){
		var tabs = divTabs.tabs("tabs");
		$.each(allTabTitle,function(i,n){
			if(n != homePage){
				divTabs.tabs("close",n);
			}
		});
	}
}
var Accordion = function(el, multiple) {
	this.el = el || {};
	this.multiple = multiple || false;

	
	var links = this.el.find('.link');
	
	links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
}

Accordion.prototype.dropdown = function(e) {
	var $el = e.data.el;
		$this = $(this),
		$next = $this.next();

	$next.slideToggle();
	$this.parent().toggleClass('open');

	if (!e.data.multiple) {
		$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
	};
}

/**
 * 修改原始密码
 */
var changePwd = function(){
	
};

/**
 * 注销
 */
var logout = function(){
	$.messager.confirm("提示","您确定要退出该系统吗?",function(r){
		if(r){
			$.post("/logout.html",function(data){
				if(data.result){
					window.location.href = "/index.ftl";
				}
			},"json");
		}
	});
};
























