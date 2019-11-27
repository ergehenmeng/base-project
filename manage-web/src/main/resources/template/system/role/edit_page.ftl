<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/system/role/edit');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>角色名称:</label>
            <input title="参数名称" maxlength="50" name="roleName" value="${(role.roleName)!}" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>角色nid:</label>
            <input title="参数标示" maxlength="50"  name="roleType" value="${(role.roleType)!}" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(role.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(role.id)!}">
    </form>
</div>
