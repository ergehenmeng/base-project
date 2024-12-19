package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.operate.NewsService;
import com.eghm.vo.business.news.NewsDetailVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "新闻资讯")
@AllArgsConstructor
@RequestMapping(value = "/webapp/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsService newsService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<List<NewsVO>> getByPage(PagingQuery request) {
        List<NewsVO> scenicPage = newsService.getByPage(request);
        return RespBody.success(scenicPage);
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    public RespBody<NewsDetailVO> detail(@Validated IdDTO dto) {
        NewsDetailVO detail = newsService.detail(dto.getId());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/praise", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("点赞")
    @AccessToken
    public RespBody<Void> praise(@RequestBody @Validated IdDTO dto) {
        newsService.praise(dto.getId());
        return RespBody.success();
    }
}
