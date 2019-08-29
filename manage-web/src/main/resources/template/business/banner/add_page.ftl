<script type="text/javascript">
    $(function() {
        $.fn.extOptions.dateRange("#targetTime","#startTime","#endTime","datetime");
        $.fn.dataGridOptions.formSubmit("#form",'/business/banner/add');
        $("#click").on("change",function(){
            var timing = $(this).val();
            if(timing === "true"){
                $("#skipUrl").show();
                $("#turnUrl").validatebox({
                    "required":true
                });
            }else{
                $("#skipUrl").hide();
                $("#turnUrl").validatebox({
                    "required":false
                });
            }
        });
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>标题:</label>
            <input title="标题" maxlength="50" name="title" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>客户端:</label>
            <select title="客户端类型" name="clientType" class="easyui-validatebox" data-options="required: true">
                <option value="PC">pc</option>
                <option value="ANDROID">android</option>
                <option value="IOS">ios</option>
                <option value="H5">h5</option>
                <option value="WECHAT">微信小程序</option>
                <option value="ALIPAY">支付宝小程序</option>
            </select>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>分类:</label>
            <@select name="classify" total="true"  title="轮播图分类" nid="banner_classify"/>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>图片:</label>
            <input title="选择图片" name="imgFile" class="easyui-validatebox" data-options="required: true" type="file" accept="image/gif,image/jpeg,image/jpg,image/png"/>
            <small>*</small>
        </div>
        <div class="form-item" id="interval" style="display: none;">
            <label>有效期:</label>
            <input title="轮播图有效期"  class="easyui-validatebox" data-options="required: true" id="targetTime" />
            <input type="hidden" id="startTime" name="startTime">
            <input type="hidden" id="endTime" name="endTime">
            <small>*</small>
        </div>
        <div class="form-item">
            <label>排序:</label>
            <input title="排序" maxlength="2" name="sort" class="easyui-validatebox" data-options="required: true,validType:'integer'"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>是否可点击:</label>
            <select title="是否可点击" name="click" id="click" class="easyui-validatebox" data-options="required: true">
                <option value="false" selected>否</option>
                <option value="true">是</option>
            </select>
            <small>*</small>
        </div>
        <div class="form-item" id="skipUrl" style="display: none;">
            <label>跳转链接:</label>
            <input title="点击后跳转的地址" maxlength="200" name="turnUrl" id="turnUrl" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60"></textarea>
        </div>
    </form>
</div>
