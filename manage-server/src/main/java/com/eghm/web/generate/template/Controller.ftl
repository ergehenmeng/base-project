package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import ${template.requestPackage}.${template.selectRequest};
import ${template.requestPackage}.${template.createRequest};
import ${template.requestPackage}.${template.updateRequest};
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import ${template.fileFullName};
import ${template.implPackage}.${template.fileName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author 二哥很猛
* @since ${.now?string('yyyy-MM-dd')}
*/
@RestController
@Api(tags = "xx管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/xx", produces = MediaType.APPLICATION_JSON_VALUE)
public class ${template.fileName}Controller {

    private final ${template.fileName}Service ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Service;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<${template.fileName}>> listPage(${template.selectRequest} request) {
        Page<${template.fileName}> byPage = ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Service.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody ${template.createRequest} request) {
        ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Service.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody ${template.updateRequest} request) {
        ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Service.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Service.delete(request.getId());
        return RespBody.success();
    }
}