package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.template.NoticeTemplateRequest;
import com.eghm.model.NoticeTemplate;
import com.eghm.service.common.NoticeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@RestController
@Api(tags = "站内信模板")
@AllArgsConstructor
@RequestMapping(value = "/manage/notice/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeTemplateController {

    private final NoticeTemplateService noticeTemplateService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<NoticeTemplate>> listPage(PagingQuery request) {
        Page<NoticeTemplate> byPage = noticeTemplateService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@RequestBody @Validated NoticeTemplateRequest request) {
        noticeTemplateService.update(request);
        return RespBody.success();
    }

}
