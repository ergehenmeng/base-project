package com.eghm.app.manage.controller.sys;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.platform.config.service.SysCacheService;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.config.dto.DeleteRequest;
import com.eghm.platform.config.entity.SysCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 缓存管理
 *
 * @author 二哥很猛
 * @since 2019/1/14 14:12
 */
@RestController
@AllArgsConstructor
@Tag(name = "缓存管理")
@RequestMapping(value = "/manage/cache", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {

    private final CacheService cacheService;

    private final SysCacheService sysCacheService;

    @GetMapping("/list")
    @Operation(summary = "缓存列表(不分页)")
    public RespBody<List<SysCache>> list() {
        List<SysCache> list = sysCacheService.getList();
        return RespBody.success(list);
    }

    @GetMapping("/clear")
    @Operation(summary = "清除缓存")
    @Parameter(name = "cacheNames", description = "缓存名称(数组)", example = "例如:cacheNames=a&cacheNames=b&cacheNames=c", required = true, array = @ArraySchema(schema = @Schema(type = "string")))
    public RespBody<Void> clear(@RequestParam("cacheNames") List<String> cacheNames) {
        sysCacheService.clearCache(cacheNames);
        return RespBody.success();
    }

    @GetMapping("/scan")
    @Operation(summary = "模糊查询")
    public RespBody<List<String>> scan(@RequestParam("key") String key) {
        List<String> list = cacheService.scan(key, 10);
        return RespBody.success(list);
    }

    @GetMapping("/query")
    @Operation(summary = "查询")
    public RespBody<String> query(@RequestParam("key") String key) {
        String value = cacheService.getValue(key);
        return RespBody.success(value);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated DeleteRequest request) {
        cacheService.delete(request.getKey());
        return RespBody.success();
    }

}
