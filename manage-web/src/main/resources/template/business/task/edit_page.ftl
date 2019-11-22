<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/business/task/edit');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>标题:</label>
            <input title="任务名称" maxlength="50" readonly value="${(config.title)!}" name="title" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>标示符:</label>
            <input title="任务唯一标示符" maxlength="20" readonly value="${(config.nid)!}" name="nid" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>bean名称:</label>
            <input title="参数标示" maxlength="50" readonly value="${(config.beanName)!}"  name="beanName" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>表达式:</label>
            <input title="定时任务cron表达式" maxlength="50" value="${(config.cronExpression)!}"  name="cronExpression" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>状态:</label>
            <select title="开启状态" name="state" class="easyui-validatebox" data-options="required: true">
                <#if config.state == 1 >
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
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(config.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(config.id)!}">
    </form>
</div>
