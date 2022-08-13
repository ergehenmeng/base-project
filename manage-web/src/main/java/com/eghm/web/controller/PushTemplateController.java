package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.PushTemplate;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.push.PushTemplateEditRequest;
import com.eghm.model.dto.push.PushTemplateQueryRequest;
import com.eghm.service.common.PushTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:31
 */
@RestController
@Api(tags = "消息模板管理")
@AllArgsConstructor
@RequestMapping("/manage/push")
public class PushTemplateController {

    private final PushTemplateService pushTemplateService;

    @GetMapping("/listPage")
    @ApiOperation("消息模板列表")
    public PageData<PushTemplate> listPage(PushTemplateQueryRequest request) {
        Page<PushTemplate> byPage = pushTemplateService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/update")
    @ApiOperation("编辑推送模板")
    public RespBody<Void> update(@Validated @RequestBody PushTemplateEditRequest request) {
        pushTemplateService.update(request);
        return RespBody.success();
    }

}
