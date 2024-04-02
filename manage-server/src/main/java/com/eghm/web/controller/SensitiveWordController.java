package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sensitive.SensitiveRequest;
import com.eghm.service.common.SensitiveWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/match")
    @ApiOperation("校验敏感词")
    @ApiImplicitParam(name = "keyword", value = "keyword", required = true)
    public RespBody<List<String>> match(@RequestParam("keyword") String keyword) {
        List<String> checkSerial = sensitiveWordService.match(keyword);
        return RespBody.success(checkSerial);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除敏感词")
    public RespBody<Void> delete(@RequestBody @Validated SensitiveRequest request) {
        sensitiveWordService.delete(request.getKeyword());
        return RespBody.success();
    }

}
