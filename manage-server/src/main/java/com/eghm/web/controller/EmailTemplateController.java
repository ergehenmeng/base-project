package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.email.EmailTemplateRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.EmailTemplate;
import com.eghm.service.common.EmailTemplateService;
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
@Api(tags = "邮件模板管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/email/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @GetMapping("/listPage")
    @ApiOperation("短信模板列表(分页)")
    public RespBody<PageData<EmailTemplate>> listPage(PagingQuery request) {
        Page<EmailTemplate> byPage = emailTemplateService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑短信模板")
    public RespBody<Void> update(@Validated @RequestBody EmailTemplateRequest request) {
        emailTemplateService.update(request);
        return RespBody.success();
    }
}
