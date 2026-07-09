package com.eghm.interfaces.manage.controller.operate;

import com.eghm.dto.ext.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.sensitive.KeywordDTO;
import com.eghm.domain.operate.model.SensitiveWord;
import com.eghm.application.operate.service.SensitiveWordService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.sensitive.SensitiveWordResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/4/2
 */

@RestController
@AllArgsConstructor
@Tag(name = "敏感词管理")
@RequestMapping(value = "/manage/sensitive/word", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensitiveWordController {

    private final SensitiveWordService sensitiveWordService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<SensitiveWordResponse>> listPage(@ParameterObject PagingQuery query) {
        Page<SensitiveWord> byPage = sensitiveWordService.getByPage(query);
        return RespBody.success(DataUtil.copy(byPage, SensitiveWordResponse.class));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建")
    public RespBody<Void> create(@RequestBody @Validated KeywordDTO dto) {
        sensitiveWordService.create(dto.getKeyword());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        sensitiveWordService.delete(dto.getId());
        return RespBody.success();
    }

}
