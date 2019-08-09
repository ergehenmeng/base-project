<script type="text/javascript">
    $(function() {
        $("#roleSelect").multipleSelect({
            target:"#roleIds",
            url:"/system/role/list",
            loadSuccess:function(data){
                return data.data;
            }
        });
        $.fn.dataGridOptions.formSubmit("#form",'/system/operator/edit',"用户更新成功");
    });
</script>
<div class="platform_form">
    <form id="form"  method="post">
        <div class="form_item">
            <label>用户姓名:</label>
            <input title="参数名称" maxlength="50" name="operatorName" value="${(operator.operatorName)!}" class="easyui-validatebox" data-options="required: true,validType:'chinese'"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>手机号:</label>
            <input title="参数名称" maxlength="50" name="mobile" value="${(operator.mobile)!}"  class="easyui-validatebox" data-options="required: true,validType:'mobile'" max="11" />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>角色:</label>
            <input title="选择角色" readonly="readonly"  id="roleSelect"  class="select" />
        </div>

        <div class="form_item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(operator.remark)!}</textarea>
        </div>
        <input type="hidden" name="id" value="${(operator.id)!}"/>
        <input type="hidden" id="roleIds" name="roleIds" value="${roleIds!}"/>
    </form>
</div>
