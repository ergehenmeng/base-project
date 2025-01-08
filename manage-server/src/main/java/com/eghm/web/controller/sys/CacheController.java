package com.eghm.web.controller.sys;

import com.eghm.cache.SysCacheService;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.SysCache;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 缓存管理
 *
 * @author 二哥很猛
 * @since 2019/1/14 14:12
 */
@RestController
@Tag(name="缓存管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/cache", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {

    private final SysCacheService sysCacheService;

    @GetMapping("/list")
    @Operation(summary = "缓存列表(不分页)")
    public RespBody<List<SysCache>> list() {
        List<SysCache> list = sysCacheService.getList();
        return RespBody.success(list);
    }

    @GetMapping("/clear")
    @Operation(summary = "清除缓存")
    @Parameter(name = "cacheNames", description = "缓存名称(数组)", required = true, content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
    public RespBody<Void> clear(@RequestParam("cacheNames") List<String> cacheNames) {
        sysCacheService.clearCache(cacheNames);
        return RespBody.success();
    }

}
