<script type="text/javascript">
    $(function() {
        $.fn.extOptions.dateRange("#targetTime","#startTime","#endTime","datetime");
        $.fn.dataGridOptions.formSubmit("#form",'/system/config/edit');
        $("#timing").on("change",function(){
            var timing = $(this).val();
            if(timing === "1"){
                parent.$.messager.alert('提示', "不在有效期内时,备选值将会生效", 'warning');
                $("#interval").show();
                $("#reserve").show();
                $("#targetTime").validatebox({
                    "required":true
                });
                $("#reserveValue").validatebox({
                    "required":true
                });
            }else{
                $("#interval").hide();
                $("#reserve").hide();
                $("#targetTime").validatebox({
                    "required":false
                });
                $("#reserveValue").validatebox({
                    "required":false
                });
            }
        });
    });
</script>
<div class="platform_form">
    <form id="form"  method="post">
        <div class="form_item">
            <label>参数名称:</label>
            <input title="参数名称" maxlength="50" value="${(config.title)!}" name="title" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>参数分类:</label>
            <@select nid="config_classify" name="classify"  value="${(config.classify)!}" total="false"></@select>
            <small>*</small>
        </div>
        <div class="form_item">
            <label>参数标示:</label>
            <input title="参数标示" maxlength="50" readonly value="${(config.nid)!}"  name="nid" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form_item">
            <label>是否锁定:</label>
            <select title="锁定后,该系统参数将禁止编辑" name="locked" class="easyui-validatebox" data-options="required: true">
                <#if config.locked>
                    <option value="true" selected >是</option>
                    <option value="false" >否</option>
                <#else>
                    <option value="true"  >是</option>
                    <option value="false" selected >否</option>
                </#if>
            </select>
            <small>*</small>
        </div>
        <div class="form_item">
            <label>参数值:</label>
            <textarea title="参数值" name="content"  class="easyui-validatebox h100" data-options="required: true" maxlength="500">${(config.content)!}</textarea>
            <small>*</small>
        </div>
        <div class="form_item">
            <label>启用定时:</label>
            <select title="在指定时间内系统参数有效" id="timing" class="easyui-validatebox" >
                <option value="1" >是</option>
                <option value="0" selected>否</option>
            </select>
        </div>
        <div class="form_item" id="interval" style="display: none;">
            <label>有效期:</label>
            <input title="主参数有效期"  class="easyui-validatebox" data-options="required: false" id="targetTime" />
            <input type="hidden" id="startTime" name="startTime">
            <input type="hidden" id="endTime" name="endTime">
            <small>*</small>
        </div>
        <div class="form_item" id="reserve" style="display: none;">
            <label>备用值:</label>
            <textarea title="在有效期之外该参数会生效" id="reserveValue" name="reserveContent" class="easyui-validatebox h80" data-options="required: false" maxlength="500"></textarea>
            <small>*</small>
        </div>
        <div class="form_item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(config.remark)!}</textarea>
        </div>
    </form>
</div>
