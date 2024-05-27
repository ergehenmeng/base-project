package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sensitvie.KeywordDTO;
import com.eghm.model.SensitiveWord;
import com.eghm.service.common.SensitiveWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/4/2
 */

@RestController
@Api(tags = "敏感词管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/sensitive/word", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensitiveWordController {

    private final SensitiveWordService sensitiveWordService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<SensitiveWord>> listPage(PagingQuery query) {
        Page<SensitiveWord> byPage = sensitiveWordService.getByPage(query);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("创建")
    public RespBody<Void> create(@RequestBody @Validated KeywordDTO dto) {
        sensitiveWordService.create(dto.getKeyword());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除敏感词")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        sensitiveWordService.delete(dto.getId());
        return RespBody.success();
    }

}
