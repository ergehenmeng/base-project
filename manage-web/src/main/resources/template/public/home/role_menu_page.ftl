<script type="text/javascript">
    $(function(){
        $("#tree").tree({
            // 获取所有的菜单及角色拥有的菜单
            url:"/system/operator/menu_list",
            loadFilter:loadFilter
        });
    });

    /** 行转列 */
    function loadFilter(data){
        return $.fn.treeGridOptions.dataFilter(data.data,"id","title","pid",0);
    }
</script>
<div class="user_role">
    <ul id="tree" >
    </ul>
</div>