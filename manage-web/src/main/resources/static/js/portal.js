
//加载门户首页信息
var portal ; //门户

function loadPortal(){
	 $('#portal').layout({
			fit : true
	});
	panels = [ {
		id : 'p1',
		title : '待填充',
		iconCls:'curve_ico',
		height : 280,
		collapsible:true,
		href : ''
	},{
		id : 'p2',
		title : '待填充',
		iconCls:'rank_ico',
		height : 280,
		collapsible:true,
		href : ''
	},{
		id : 'p3',
		title : '待填充',
		iconCls:'cal_ico',
		height : 280,
		collapsible:true,
		href : ''
	},{
		id : 'p4',
		title : '待填充',
		iconCls:'pie_ico',
		height : 280,
		collapsible:true,
		href : ''
	}];
	
	portal = $('#portal').portal({
		border : false
	});
	
	var state = "p1,p3:p2,p4"; //:为竖向切分 ,为横向切分
	addPanel(state);
	portal.portal('resize');
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
				 portal.portal("add",{
					 panel			:p,
					 columnIndex	:i
				 });
			 }
		}
	}
}

function getOptionById(id){
	for(var i =0 ;i< panels.length;i++){
		if(panels[i].id == id){
			return panels[i];
		}
	}
	return undefined;
}


$(function(){
	loadPortal();
});
















