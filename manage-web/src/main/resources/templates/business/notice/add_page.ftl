<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.wangEditor("#editor","#content","#originalContent");
        $.fn.dataGridOptions.formSubmit("#form",'/business/notice/add');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>标题:</label>
            <input title="公告标题"  name="title"  class="easyui-validatebox" data-options="required: true"/>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>类型:</label>
            <@select name="classify" title="公告类型" nid="notice_classify"/>
            <small>*</small>
        </div>
        <div class="form-item" >
            <textarea title="html正文内容" style="display: none;" name="content" id="content" ></textarea>
            <textarea title="正文内容" style="display: none;" name="originalContent" id="originalContent" ></textarea>
            <label>内容:</label>
            <div id="editor" style="width: 800px;"></div>
        </div>
    </form>
</div>
