package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.sms.SmsTemplateEditRequest;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.vo.template.SmsTemplateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 短信模板修改
 *
 * @author 二哥很猛
 * @since 2019/8/21 18:01
 */
@RestController
@Api(tags = "短信模板管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/sms/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class SmsTemplateController {

    private final SmsTemplateService smsTemplateService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<SmsTemplateResponse>> listPage(PagingQuery request) {
        Page<SmsTemplateResponse> byPage = smsTemplateService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody SmsTemplateEditRequest request) {
        smsTemplateService.update(request);
        return RespBody.success();
    }
}
