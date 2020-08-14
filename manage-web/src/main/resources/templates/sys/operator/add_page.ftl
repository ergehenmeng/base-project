<script type="text/javascript">
    $(function() {
        $("#roleSelect").multipleSelect({
            target:"#roleIds",
            url:"/sys/role/list",
            loadSuccess:function(data){
                return data.data;
            }
        });
        $.fn.dataGridOptions.formSubmit("#form",'/sys/operator/add');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>用户姓名:</label>
            <input title="参数名称" maxlength="50" name="operatorName"  class="easyui-validatebox" data-options="required: true,validType:'chinese'"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>手机号:</label>
            <input title="参数名称" maxlength="50" name="mobile"  class="easyui-validatebox" data-options="required: true,validType:'mobile'" max="11" />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>角色:</label>
            <input title="选择角色" readonly="readonly"  id="roleSelect"  class="select" />
        </div>

        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60"></textarea>
        </div>
        <input type="hidden" id="roleIds" name="roleIds" />
    </form>
</div>
