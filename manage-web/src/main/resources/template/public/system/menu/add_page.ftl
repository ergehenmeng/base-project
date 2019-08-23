<script type="text/javascript">
    $(function() {
        $.fn.treeGridOptions.formSubmit("#form",'/system/menu/add',"菜单添加成功");
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>菜单名称:</label>
            <input title="菜单名称" maxlength="8" name="title" class="easyui-validatebox" data-options="required: true,validType:'chinese'"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>菜单标示:</label>
            <input title="" maxlength="20"  name="nid" class="easyui-validatebox" data-options="required: true,validType:'english'"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>菜单类型:</label>
            <select title="菜单类型" name="classify">
                <option value="0" >导航菜单</option>
                <option value="1">按钮菜单</option>
            </select>
        </div>
        <div class="form-item">
            <label>排序:</label>
            <input title="排序" maxlength="2" name="sort" class="easyui-validatebox" data-options="required: true,validType:'integer'"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>菜单链接:</label>
            <input title="菜单链接" maxlength="200" name="url" />
        </div>
        <div class="form-item">
            <label>子菜单:</label>
            <textarea title="子菜单" name="subUrl" class="h80" placeholder="该链接包含的URL,以分号隔开" maxlength="500"></textarea>
        </div>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60" maxlength="100"></textarea>
        </div>
        <input type="hidden" name="pid" value="${pid!0}"/>
    </form>
</div>
