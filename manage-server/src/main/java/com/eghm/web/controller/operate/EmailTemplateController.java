package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.email.EmailTemplateRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.EmailTemplate;
import com.eghm.service.operate.EmailTemplateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@RestController
@Tag(name = "邮件模板管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/email/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<EmailTemplate>> listPage(@ParameterObject PagingQuery request) {
        Page<EmailTemplate> byPage = emailTemplateService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody EmailTemplateRequest request) {
        emailTemplateService.update(request);
        return RespBody.success();
    }
}
