<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/system/role/add',"角色添加成功");
    });
</script>
<div class="platform_form">
    <form id="form"  method="post">
        <div class="form_item">
            <label>角色名称:</label>
            <input title="参数名称" maxlength="50" name="roleName"  class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>角色类型:</label>
            <input title="参数标示" maxlength="50"  name="roleType"  class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60"></textarea>
        </div>
    </form>
</div>
