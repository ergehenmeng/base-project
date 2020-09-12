<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/sms_template/edit');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>标示符:</label>
            <input title="唯一标示符" maxlength="20" readonly value="${(template.nid)!}" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>内容:</label>
            <textarea title="短信内容" name="content" class="h60" maxlength="70" class="easyui-validatebox" data-options="required: true">${(template.content)!}</textarea>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(template.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(template.id)!}">
    </form>
</div>
