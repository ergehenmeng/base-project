package com.eghm.interfaces.manage.controller.operate;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.operate.template.NoticeTemplateRequest;
import com.eghm.domain.operate.model.NoticeTemplate;
import com.eghm.application.operate.port.in.NoticeTemplateService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.operate.template.NoticeTemplateResponse;
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
@Tag(name = "站内信模板")
@RequestMapping(value = "/manage/notice/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeTemplateController {

    private final NoticeTemplateService noticeTemplateService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<NoticeTemplateResponse>> listPage(@ParameterObject PagingQuery request) {
        Page<NoticeTemplate> byPage = noticeTemplateService.getByPage(request);
        return RespBody.success(DataUtil.copy(byPage, NoticeTemplateResponse.class));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated NoticeTemplateRequest request) {
        noticeTemplateService.update(request);
        return RespBody.success();
    }

}
