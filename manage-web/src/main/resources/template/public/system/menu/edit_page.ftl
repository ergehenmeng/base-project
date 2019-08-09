<script type="text/javascript">
    $(function() {
        $.fn.treeGridOptions.formSubmit("#form",'/system/menu/edit');
    });
</script>
<div class="platform_form">
    <form id="form"  method="post">
        <div class="form_item">
            <label>菜单名称:</label>
            <input title="菜单名称" maxlength="8" value="${(menu.title)!}" name="title" class="easyui-validatebox" data-options="required: true,validType:'chinese'"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>菜单标示:</label>
            <input title="菜单标示" maxlength="20" value="${(menu.nid)!}" name="nid" class="easyui-validatebox" data-options="required: true,validType:'english'"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>排序:</label>
            <input title="排序" maxlength="2" value="${(menu.sort)!0}" name="sort" class="easyui-validatebox" data-options="required: true,validType:'integer'"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>菜单类型:</label>
            <select title="菜单类型" name="classify">
            <#if menu.classify == 0 >
                <option value="0" selected="selected">导航菜单</option>
                <option value="1" >按钮菜单</option>
            <#elseif menu.classify == 1>
                <option value="0" >导航菜单</option>
                <option value="1" selected="selected">按钮菜单</option>
            </#if>
            </select>
        </div>
        <div class="form_item">
            <label>菜单链接:</label>
            <input title="菜单链接" maxlength="200" value="${(menu.url)!}" name="url" />
        </div>
        <div class="form_item">
            <label>子菜单:</label>
            <textarea title="子菜单" name="subUrl" class="h80" placeholder="该链接包含的URL,以分号隔开" maxlength="500">${(menu.subUrl)!}</textarea>
        </div>
        <div class="form_item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(menu.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(menu.id)!}"/>
    </form>
</div>
