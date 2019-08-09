<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/system/dict/add');
        $("#locked").on("change",function(){
            var locked = $(this).val();
            if(locked === "true"){
                parent.$.messager.alert('提示', "锁定后,部分参数禁止编辑", 'warning');
            }
        });
    });
</script>
<div class="platform_form">
    <form id="form"  method="post">
        <div class="form_item">
            <label>参数名称:</label>
            <input title="参数名称" maxlength="50" name="name" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>标示符:</label>
            <input title="参数标示" maxlength="50" name="nid" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>编辑状态:</label>
            <select title="锁定后,该数据字典参数将禁止编辑" name="locked" id="locked" class="easyui-validatebox" data-options="required: true">
                <option value="true">是</option>
                <option value="false" selected >否</option>
            </select>
            <small>*</small>
        </div>
        <div class="form_item">
            <label>隐藏值:</label>
            <input title="隐藏值" maxlength="2" name="hiddenValue" class="easyui-validatebox" data-options="required: true,validType:'integer'"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>显示值:</label>
            <input title="显示值" maxlength="20"  name="showValue" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60"></textarea>
        </div>
    </form>
</div>
