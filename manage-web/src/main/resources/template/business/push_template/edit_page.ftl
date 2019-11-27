<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/business/push_template/edit');
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
            <label>标题:</label>
            <input title="唯一标示符" maxlength="20" name="title" value="${(template.title)!}" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>状态:</label>
            <select title="开启状态" name="state" class="easyui-validatebox" data-options="required: true">
                <#if template.state == 1>
                    <option value="1" selected >是</option>
                    <option value="0" >否</option>
                <#else>
                    <option value="1"  >是</option>
                    <option value="0" selected >否</option>
                </#if>
            </select>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>内容:</label>
            <textarea title="推送内容" name="content" class="h100" maxlength="100" class="easyui-validatebox" data-options="required: true">${(template.content)!}</textarea>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>标签:</label>
            <select title="开启状态" name="state" class="easyui-validatebox" data-options="required: true">
                <#if view?? && view?size gt 0>
                    <#list view as v>
                        <option value="${(v.tag)}" <#if template.tag == v.tag> selected </#if> >${(v.title)!}</option>
                    </#list>
                </#if>
            </select>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(template.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(template.id)!}">
    </form>
</div>
