package com.eghm.interfaces.manage.controller.operate;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.email.EmailTemplateRequest;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.domain.operate.model.EmailTemplate;
import com.eghm.application.operate.query.EmailTemplateQueryService;
import com.eghm.application.operate.service.EmailTemplateApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.operate.template.EmailTemplateResponse;
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
@AllArgsConstructor
@Tag(name = "邮件模板管理")
@RequestMapping(value = "/manage/email/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailTemplateController {

    private final EmailTemplateApplicationService emailTemplateService;

    private final EmailTemplateQueryService emailTemplateQueryService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<EmailTemplateResponse>> listPage(@ParameterObject PagingQuery request) {
        Page<EmailTemplate> byPage = emailTemplateQueryService.getByPage(request);
        return RespBody.success(DataUtil.copy(byPage, EmailTemplateResponse.class));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody EmailTemplateRequest request) {
        emailTemplateService.update(request);
        return RespBody.success();
    }
}
