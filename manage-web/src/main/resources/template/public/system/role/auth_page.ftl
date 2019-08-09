<script type="text/javascript">
    $(function(){
        var flag = false;
        var treeObj = $("#roleTree").tree({
            url:"/system/menu/list_page",
            loadFilter:function (data) {
                return $.fn.treeGridOptions.dataFilter(data.data,"id","title","pid",0);
            },
            checkbox: true,
            onCheck:function(){
                //数据加载后渲染之前自动触发一遍,且在onLoadSuccess事件之前
                //如果直接触发该方法会导致menuIds为空,而后的onLoadSuccess取不到值
                if(flag){
                    $("#menuIds").val($.fn.treeGridOptions.checkNode(treeObj));
                }
            },
            onLoadSuccess:function(){
                flag = true;
                //勾选选中的列
                $.fn.treeGridOptions.checkbox(treeObj,$("#menuIds").val());
            }
        });
    });
    $.fn.dataGridOptions.formSubmit("#form",'/system/role/auth',"授权成功");

</script>
<div class="user_role">
    <form id="form" method="post">
        <ul id="roleTree" >
        </ul>
        <input name="menuIds" id="menuIds" type="hidden" value="${menuIds!}"/>
        <input name="roleId"  type="hidden" value="${roleId!}"/>
    </form>
</div>