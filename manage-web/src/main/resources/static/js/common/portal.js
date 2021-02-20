//加载门户首页信息
let portal; //门户

function loadPortal() {
    let $portal = $('#portal');
    $portal.layout({
        fit: true
    });
    panels = [{
        id: 'p1',
        title: '待填充',
        iconCls: 'curve_ico',
        height: 280,
        collapsible: true,
        href: ''
    }, {
        id: 'p2',
        title: '待填充',
        iconCls: 'rank_ico',
        height: 280,
        collapsible: true,
        href: ''
    }, {
        id: 'p3',
        title: '待填充',
        iconCls: 'cal_ico',
        height: 280,
        collapsible: true,
        href: ''
    }, {
        id: 'p4',
        title: '待填充',
        iconCls: 'pie_ico',
        height: 280,
        collapsible: true,
        href: ''
    }];

    portal = $portal.portal({
        border: false
    });
    //:为竖向切分 ,为横向切分
    let state = "p1,p3:p2,p4";
    addPanel(state);
    portal.portal('resize');
}

//向首页添加四个面板
function addPanel(state) {
    let cols = state.split(":");
    for (let i = 0; i < cols.length; i++) {
        let rows = cols[i].split(",");
        for (let j = 0; j < rows.length; j++) {
            let options = getOptionById(rows[j]);
            if (options) {
                let p = $("<div/>").attr("id", options.id).appendTo("body");
                p.panel(options);
                portal.portal("add", {
                    panel: p,
                    columnIndex: i
                });
            }
        }
    }
}

function getOptionById(id) {
    for (let i = 0; i < panels.length; i++) {
        if (panels[i].id === id) {
            return panels[i];
        }
    }
    return undefined;
}


$(function () {
    loadPortal();
});
















