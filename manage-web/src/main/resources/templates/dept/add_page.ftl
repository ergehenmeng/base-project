<script type="text/javascript">
    $(function() {
        $.fn.treeGridOptions.formSubmit("#form",'/department/add');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>部门名称:</label>
            <input title="部门名称" maxlength="8" name="title" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60" maxlength="100"></textarea>
        </div>
        <input type="hidden" name="paremtnCode" value="${code!}"/>
    </form>
</div>
