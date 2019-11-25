<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubmit("#form",'/business/notice/edit');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>标题:</label>
            <input title="标题" maxlength="50" name="title" value="${(banner.title)!}" class="easyui-validatebox" data-options="required: true"  />
            <small>*</small>
        </div>
        <div class="form-item">
            <label>客户端:</label>
            <select title="客户端类型" name="clientType" class="easyui-validatebox" data-options="required: true">
                <option value="PC" <#if banner.clientType == 'PC' >selected</#if> >pc</option>
                <option value="ANDROID" <#if banner.clientType == 'ANDROID' >selected</#if> >android</option>
                <option value="IOS" <#if banner.clientType == 'IOS' >selected</#if> >ios</option>
                <option value="H5" <#if banner.clientType == 'H5' >selected</#if> >h5</option>
                <option value="WECHAT" <#if banner.clientType == 'WECHAT' >selected</#if> >微信小程序</option>
                <option value="ALIPAY" <#if banner.clientType == 'ALIPAY' >selected</#if> >支付宝小程序</option>
            </select>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>分类:</label>
            <@select name="classify" total="true" value="${(banner.classify)!}"  title="轮播图分类" nid="banner_classify"/>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>图片:</label>
            <input title="选择图片" name="imgFile" class="easyui-validatebox" data-options="required: true" type="file" accept="image/gif,image/jpeg,image/jpg,image/png"/>
            <small>*</small>
        </div>
        <div class="form-item" id="interval" style="display: none;">
            <label>有效期:</label>
            <input title="轮播图有效期" class="easyui-validatebox" data-options="required: true" id="targetTime" />
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
                <#if banner.click>
                    <option value="false">否</option>
                    <option value="true" selected >是</option>
                <#else>
                    <option value="false" selected>否</option>
                    <option value="true">是</option>
                </#if>
            </select>
            <small>*</small>
        </div>
        <#if banner.click>
            <div class="form-item" id="skipUrl" >
                <label>跳转链接:</label>
                <input title="点击后跳转的地址" maxlength="200" name="turnUrl" id="turnUrl" class="easyui-validatebox" data-options="required: true" value="${(banner.turnUrl)!}" />
                <small>*</small>
            </div>
        </#if>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60">${(banner.remark)!}</textarea>
        </div>
    </form>
</div>