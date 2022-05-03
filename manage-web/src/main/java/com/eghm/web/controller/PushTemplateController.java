package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.PushTemplate;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.push.PushTemplateEditRequest;
import com.eghm.model.dto.push.PushTemplateQueryRequest;
import com.eghm.service.common.PushTemplateService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:31
 */
@RestController
@Api(tags = "消息模板管理")
@AllArgsConstructor
@RequestMapping("/push")
public class PushTemplateController {

    private final PushTemplateService pushTemplateService;

    /**
     * 分页查询推送消息模板信息
     */
    @GetMapping("/listPage")
    @ApiOperation("消息模板列表")
    public Paging<PushTemplate> listPage(PushTemplateQueryRequest request) {
        Page<PushTemplate> byPage = pushTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 推送模板编辑保存
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("编辑推送模板")
    public RespBody<Void> edit(@Valid PushTemplateEditRequest request) {
        pushTemplateService.editPushTemplate(request);
        return RespBody.success();
    }

}
