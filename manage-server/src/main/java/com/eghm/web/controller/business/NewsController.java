package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.business.news.NewsAddRequest;
import com.eghm.dto.business.news.NewsEditRequest;
import com.eghm.dto.business.news.NewsQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.News;
import com.eghm.service.business.NewsService;
import com.eghm.vo.business.news.NewsResponse;
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
@Api(tags = "新闻资讯")
@AllArgsConstructor
@RequestMapping(value = "/manage/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsService newsService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<NewsResponse>> getByPage(@Validated NewsQueryRequest request) {
        Page<NewsResponse> scenicPage = newsService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @ApiOperation("新增")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody NewsAddRequest request) {
        newsService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody NewsEditRequest request) {
        newsService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        newsService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        newsService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @ApiOperation("详情")
    @GetMapping("/select")
    public RespBody<News> select(@Validated IdDTO request) {
        News news = newsService.selectById(request.getId());
        return RespBody.success(news);
    }

}
