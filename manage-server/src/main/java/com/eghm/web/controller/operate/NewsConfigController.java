package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.dto.business.news.config.NewsConfigEditRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.NewsConfig;
import com.eghm.service.operate.NewsConfigService;
import com.eghm.vo.business.news.NewsConfigResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@RestController
@Tag(name= "资讯配置")
@AllArgsConstructor
@RequestMapping(value = "/manage/news/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsConfigController {

    private final NewsConfigService newsConfigService;

    @Operation(summary = "列表")
    @GetMapping("/listPage")
    public RespBody<PageData<NewsConfig>> getByPage(PagingQuery request) {
        Page<NewsConfig> scenicPage = newsConfigService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @Operation(summary = "创建")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody NewsConfigAddRequest request) {
        newsConfigService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody NewsConfigEditRequest request) {
        newsConfigService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        newsConfigService.deleteById(dto.getId());
        return RespBody.success();
    }

    @Operation(summary = "全部资讯分类")
    @GetMapping("/list")
    public RespBody<List<NewsConfigResponse>> list() {
        List<NewsConfigResponse> configList = newsConfigService.getList();
        return RespBody.success(configList);
    }

    @Operation(summary = "查询资讯配置")
    @GetMapping(value = "/select")
    @Parameter(name = "code", description = "资讯编码", required = true)
    public RespBody<NewsConfig> select(@RequestParam("code") String code) {
        NewsConfig config = newsConfigService.getByCode(code);
        return RespBody.success(config);
    }
}
