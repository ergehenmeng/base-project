package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.StateRequest;
import com.eghm.dto.business.news.NewsAddRequest;
import com.eghm.dto.business.news.NewsEditRequest;
import com.eghm.dto.business.news.NewsQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.News;
import com.eghm.service.operate.NewsService;
import com.eghm.vo.business.news.NewsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@RestController
@Tag(name= "新闻资讯")
@AllArgsConstructor
@RequestMapping(value = "/manage/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "列表")
    @GetMapping("/listPage")
    public RespBody<PageData<NewsResponse>> getByPage(@ParameterObject @Validated NewsQueryRequest request) {
        Page<NewsResponse> scenicPage = newsService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @Operation(summary = "新增")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody NewsAddRequest request) {
        newsService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody NewsEditRequest request) {
        newsService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        newsService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        newsService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @Operation(summary = "详情")
    @GetMapping("/select")
    public RespBody<News> select(@ParameterObject @Validated IdDTO request) {
        News news = newsService.selectById(request.getId());
        return RespBody.success(news);
    }

    @PostMapping(value = "/updateState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新状态")
    public RespBody<Void> updateState(@Validated @RequestBody StateRequest request) {
        newsService.updateState(request.getId(), request.getState());
        return RespBody.success();
    }
}
