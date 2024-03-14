package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.dto.business.news.config.NewsConfigEditRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.NewsConfig;
import com.eghm.service.business.NewsConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@RestController
@Api(tags = "资讯配置")
@AllArgsConstructor
@RequestMapping(value = "/manage/news/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsConfigController {

    private final NewsConfigService newsConfigService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<NewsConfig>> getByPage(PagingQuery request) {
        Page<NewsConfig> scenicPage = newsConfigService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @ApiOperation("创建")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody NewsConfigAddRequest request) {
        newsConfigService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody NewsConfigEditRequest request) {
        newsConfigService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        newsConfigService.deleteById(dto.getId());
        return RespBody.success();
    }
}
