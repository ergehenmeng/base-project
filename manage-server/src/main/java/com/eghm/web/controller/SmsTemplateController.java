package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SmsTemplate;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.sms.SmsTemplateEditRequest;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import com.eghm.service.sys.SmsTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 短信模板修改
 *
 * @author 二哥很猛
 * @date 2019/8/21 18:01
 */
@RestController
@Api(tags = "短信模板管理")
@AllArgsConstructor
@RequestMapping("/manage/sms/template")
public class SmsTemplateController {

    private final SmsTemplateService smsTemplateService;

    @GetMapping("/listPage")
    @ApiOperation("短信模板列表(分页)")
    public PageData<SmsTemplate> listPage(SmsTemplateQueryRequest request) {
        Page<SmsTemplate> byPage = smsTemplateService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/update")
    @ApiOperation("编辑短信模板")
    public RespBody<Void> update(@Validated @RequestBody SmsTemplateEditRequest request) {
        smsTemplateService.update(request);
        return RespBody.success();
    }
}
