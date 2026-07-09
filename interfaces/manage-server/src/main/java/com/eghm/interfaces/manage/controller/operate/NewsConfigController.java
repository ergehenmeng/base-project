package com.eghm.interfaces.manage.controller.operate;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.application.shared.dto.business.news.config.NewsConfigEditRequest;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.domain.operate.model.NewsConfig;
import com.eghm.application.operate.port.in.NewsConfigService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.business.news.NewsConfigDetailResponse;
import com.eghm.application.shared.vo.business.news.NewsConfigResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@AllArgsConstructor
@Tag(name = "资讯配置")
@RequestMapping(value = "/manage/news/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsConfigController {

    private final NewsConfigService newsConfigService;

    @Operation(summary = "列表")
    @GetMapping("/listPage")
    public RespBody<PageData<NewsConfigDetailResponse>> getByPage(@ParameterObject PagingQuery request) {
        Page<NewsConfig> scenicPage = newsConfigService.getByPage(request);
        return RespBody.success(DataUtil.copy(scenicPage, NewsConfigDetailResponse.class));
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
    @GetMapping("/select")
    @Parameter(name = "code", description = "资讯编码", required = true)
    public RespBody<NewsConfigDetailResponse> select(@RequestParam("code") String code) {
        NewsConfig config = newsConfigService.getByCode(code);
        return RespBody.success(DataUtil.copy(config, NewsConfigDetailResponse.class));
    }
}
