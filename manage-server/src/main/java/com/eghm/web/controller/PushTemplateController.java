package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.push.PushTemplateEditRequest;
import com.eghm.dto.push.PushTemplateQueryRequest;
import com.eghm.service.common.PushTemplateService;
import com.eghm.vo.push.PushTemplateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/8/29 16:31
 */
@RestController
@Api(tags = "消息模板管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/push", produces = MediaType.APPLICATION_JSON_VALUE)
public class PushTemplateController {

    private final PushTemplateService pushTemplateService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<PushTemplateResponse>> listPage(PushTemplateQueryRequest request) {
        Page<PushTemplateResponse> byPage = pushTemplateService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody PushTemplateEditRequest request) {
        pushTemplateService.update(request);
        return RespBody.success();
    }

}
