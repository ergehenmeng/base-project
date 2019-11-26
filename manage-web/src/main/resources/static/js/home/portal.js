
//加载门户首页信息
var $portal  = $('#portal');
var panels;
function loadPortal(){
	panels = [ {
		id : 'p1',
		title : '待填充P1',
		iconCls:'curve_ico',
		height : 300,
		collapsible:true,
		href : ''
	},{
		id : 'p2',
		title : '待填充P2',
		iconCls:'rank_ico',
		height : 300,
		collapsible:true,
		href : ''
	},{
		id : 'p3',
		title : '待填充P3',
		iconCls:'cal_ico',
		height : 300,
		collapsible:true,
		href : ''
	},{
		id : 'p4',
		title : '待填充P4',
		iconCls:'pie_ico',
		height : 300,
		collapsible:true,
		href : ''
	}];
	
	$portal.portal({
		border : false
	}).layout({
        fit : true
    });
    //:为竖向切分 ,为横向切分
	var state = "p1,p3:p2,p4";
	addPanel(state);
}

//向首页添加四个面板
function addPanel(state){
	var cols = state.split(":");
	for(var i = 0; i< cols.length; i++){
		var rows =cols[i].split(",");
		for(var j = 0; j< rows.length; j++){
			 var options = getOptionById(rows[j]);
			 if(options){
				 var p = $("<div/>").attr("id",options.id).appendTo("body");
				 p.panel(options);
                 $portal.portal("add",{
					 panel			:p,
					 columnIndex	:i
				 }).portal("disableDragging",p);
			 }
		}
	}
}

function getOptionById(id){
	for(var i =0 ;i< panels.length;i++){
		if(panels[i].id === id){
			return panels[i];
		}
	}
	return undefined;
}


$(function(){
	loadPortal();
});
















