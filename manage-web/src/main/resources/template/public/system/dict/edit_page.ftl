<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/system/dict/edit');
    });
</script>
<div class="platform_form">
    <form id="form"  method="post">
        <div class="form_item">
            <label>参数名称:</label>
            <input title="参数名称" maxlength="50" value="${(dict.title)!}" <#if dict.locked> readonly </#if> name="name" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>标示符:</label>
            <input title="参数标示" maxlength="50"  value="${(dict.nid)!}" <#if dict.locked> readonly </#if> name="nid" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>编辑状态:</label>
            <#if dict.locked>
                <select title="锁定后,该数据字典参数将禁止编辑" disabled name="locked" class="easyui-validatebox" data-options="required: true">
                    <option value="true" selected >是</option>
                    <option value="false" >否</option>
                </select>
            <#else>
                <select title="锁定后,该数据字典参数将禁止编辑" name="locked" class="easyui-validatebox" data-options="required: true">
                    <option value="true"  >是</option>
                    <option value="false" selected >否</option>
                </select>
            </#if>

            <small>*</small>
        </div>
        <div class="form_item">
            <label>隐藏值:</label>
            <input title="隐藏值" maxlength="2"  value="${(dict.hiddenValue)!}" <#if dict.locked> readonly </#if>  name="hiddenValue" class="easyui-validatebox" data-options="required: true,validType:'integer'"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>显示值:</label>
            <input title="显示值" maxlength="20"  value="${(dict.showValue)!}" <#if dict.locked> readonly </#if> name="showValue" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(dict.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(dict.id)!}"/>
    </form>
</div>
