<script type="text/javascript">
    $(function() {
        $.fn.treeGridOptions.formSubmit("#form",'/system/department/edit');
    });
</script>
<div class="platform_form">
    <form id="form"  method="post">
        <div class="form_item">
            <label>部门名称:</label>
            <input title="部门名称" maxlength="8" name="title" value="${(department.title)!}" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60" maxlength="100">${(department.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(department.id)!}"/>
    </form>
</div>
