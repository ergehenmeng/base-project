<script type="text/javascript">
    var editor;
    $(function() {
        var E = window.wangEditor;
        editor = new E('#editor');
        editor.customConfig.onchange = function(html){
          $("#content").val(html);
        };
        editor.create();
        E.fullscreen.init('#editor');
        $.fn.dataGridOptions.formSubmit("#form",'/business/notice/add');
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
            <label>类型:</label>
            <@select name="classify" title="公告类型" nid="notice_classify"/>
            <small>*</small>
        </div>
        <div class="form-item" >
            <label>内容:</label>
            <div id="editor" style="width: 800px;"></div>
            <textarea title="正文内容" style="display: none;" name="content" id="content"></textarea>
        </div>
    </form>
</div>
