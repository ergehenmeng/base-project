package com.eghm.web.controller.sys;

import com.eghm.cache.SysCacheService;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.SysCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "缓存管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/cache", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {

    private final SysCacheService sysCacheService;

    @GetMapping("/list")
    @ApiOperation("缓存列表(不分页)")
    public RespBody<List<SysCache>> list() {
        List<SysCache> list = sysCacheService.getList();
        return RespBody.success(list);
    }

    @GetMapping("/clear")
    @ApiOperation("清除缓存")
    @ApiImplicitParam(name = "cacheNames", value = "缓存名称(数组)", required = true)
    public RespBody<Void> clear(@RequestParam("cacheNames") List<String> cacheNames) {
        sysCacheService.clearCache(cacheNames);
        return RespBody.success();
    }

}
