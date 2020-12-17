<script type="text/javascript">
    $(function () {
        $.fn.dataGridOptions.formSubmit("#form", '/version/add');
        $("#classify").on("change", function () {
            var classify = $(this).val();
            if (classify === "IOS") {
                $("#url").validatebox({
                    "required": true
                });
                $("#file").validatebox({
                    "required": false
                });
                $("#fileDiv").hide();
                $("#urlDiv").show();

            } else {
                $("#url").validatebox({
                    "required": false
                });
                $("#file").validatebox({
                    "required": true
                });
                $("#urlDiv").hide();
                $("#fileDiv").show();
            }
        });
    });
</script>
<div class="platform-form">
    <form id="form" method="post" enctype="multipart/form-data">
        <div class="form-item">
            <label>类型:</label>
            <select title="客户端类型" name="classify" id="classify" class="easyui-validatebox"
                    data-options="required: true">
                <option value="ANDROID">安卓</option>
                <option value="IOS">苹果</option>
            </select>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>版本号:</label>
            <input title="软件版本号" maxlength="10" name="version" class="easyui-validatebox"
                   data-options="required: true"/>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>是否强制:</label>
            <select title="是否强制更新" name="forceUpdate" class="easyui-validatebox" data-options="required: true">
                <option value="true">是</option>
                <option value="false" selected>否</option>
            </select>
            <small>*</small>
        </div>
        <div class="form-item" id="urlDiv" style="display: none;">
            <label>下载地址:</label>
            <input title="苹果appStore地址" maxlength="200" name="url" value="${appStoreUrl!}" class="easyui-validatebox"/>
            <small>*</small>
        </div>
        <div class="form-item" id="fileDiv">
            <label>上传文件:</label>
            <input title="上传安卓版本包" type="file" name="file" id="file" class="easyui-validatebox"
                   data-options="required: true"/>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>备注:</label>
            <textarea title="备注" name="remark" class="h60" maxlength="300"></textarea>
        </div>
    </form>
    <input type="hidden" id="appStoreUrl" value="${appStoreUrl!}">
</div>
