package com.eghm.interfaces.webapp.controller.business;

import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.operate.query.NewsQueryService;
import com.eghm.application.operate.service.NewsApplicationService;
import com.eghm.application.shared.vo.business.news.NewsDetailVO;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.interfaces.webapp.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@RestController
@Tag(name = "新闻资讯")
@AllArgsConstructor
@RequestMapping(value = "/webapp/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    private final NewsApplicationService newsService;

    private final NewsQueryService newsQueryService;

    @Operation(summary = "列表")
    @GetMapping("/listPage")
    public RespBody<List<NewsVO>> getByPage(@ParameterObject PagingQuery request) {
        List<NewsVO> scenicPage = newsQueryService.listClientPage(request);
        return RespBody.success(scenicPage);
    }

    @Operation(summary = "详情")
    @GetMapping("/detail")
    public RespBody<NewsDetailVO> detail(@ParameterObject @Validated IdDTO dto) {
        NewsDetailVO detail = newsService.detail(dto.getId());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/praise", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "点赞")
    @AccessToken
    public RespBody<Void> praise(@RequestBody @Validated IdDTO dto) {
        newsService.praise(dto.getId());
        return RespBody.success();
    }
}
