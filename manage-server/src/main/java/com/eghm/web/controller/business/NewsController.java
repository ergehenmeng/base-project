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
import com.eghm.service.business.NewsConfigService;
import com.eghm.service.business.NewsService;
import com.eghm.vo.business.news.NewsConfigResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@RestController
@Api(tags = "新闻资讯")
@AllArgsConstructor
@RequestMapping("/manage/news")
public class NewsController {

    private final NewsService newsService;

    private final NewsConfigService newsConfigService;

    @ApiOperation("查询列表")
    @GetMapping("/listPage")
    public RespBody<PageData<News>> getByPage(@Validated NewsQueryRequest request) {
        Page<News> scenicPage = newsService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @ApiOperation("全部列表")
    @GetMapping("/list")
    public RespBody<List<NewsConfigResponse>> list() {
        List<NewsConfigResponse> scenicPage = newsConfigService.getList();
        return RespBody.success(scenicPage);
    }

    @ApiOperation("创建")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody NewsAddRequest request) {
        newsService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody NewsEditRequest request) {
        newsService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        newsService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/sort")
    @ApiOperation("排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        newsService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }
}
