<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.wangEditor("#editor","#content","#originalContent");
        var html = $("#content").text();
        editor.txt.html(html);
        $("#originalContent").text(editor.txt.text());
        $.fn.dataGridOptions.formSubmit("#form",'/business/bulletin/edit');
    });
</script>
<div class="platform-form">
    <form id="form"  method="post">
        <div class="form-item">
            <label>标题:</label>
            <input title="公告标题"  name="title" value="${(notice.title)!}"  class="easyui-validatebox" data-options="required: true"/>
            <small>*</small>
        </div>
        <div class="form-item">
            <label>类型:</label>
            <@select name="classify" title="公告类型" value="${(notice.classify)!}" nid="notice_classify"/>
            <small>*</small>
        </div>
        <div class="form-item" >
            <textarea title="html正文内容" style="display: none;" name="content" id="content" >${(notice.content)!}</textarea>
            <textarea title="正文内容" style="display: none;" name="originalContent" id="originalContent" ></textarea>
            <label>内容:</label>
            <div id="editor" style="width: 800px;"></div>
        </div>
        <input type="hidden" name="id" value="${(notice.id)!}"/>
    </form>
</div>
