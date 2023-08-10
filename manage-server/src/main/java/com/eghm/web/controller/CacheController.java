package com.eghm.web.controller;

import cn.hutool.core.util.StrUtil;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.SysCache;
import com.eghm.service.cache.SysCacheService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 缓存管理
 *
 * @author 二哥很猛
 * @date 2019/1/14 14:12
 */
@RestController
@Api(tags = "缓存管理")
@AllArgsConstructor
@RequestMapping("/manage/cache")
public class CacheController {

    private final SysCacheService sysCacheService;

    @GetMapping("/list")
    @ApiOperation("缓存列表(不分页)")
    public RespBody<PageData<SysCache>> list() {
        List<SysCache> list = sysCacheService.getList();
        return RespBody.success(PageData.toList(list));
    }

    @GetMapping("/clear")
    @ApiOperation("清除缓存")
    @ApiImplicitParam(name = "cacheName", value = "缓存名称", required = true)
    public RespBody<Void> clear(@RequestParam("cacheNames") String cacheName) {
        List<String> cacheList = Lists.newArrayList(cacheName);
        sysCacheService.clearCache(cacheList);
        return RespBody.success();
    }

    @GetMapping("/batchClear")
    @ApiOperation("批量清除缓存")
    @ApiImplicitParam(name = "cacheNames", value = "缓存名称,逗号分割", required = true)
    public RespBody<Void> batchClear(@RequestParam("cacheNames") String cacheName) {
        List<String> cacheList = StrUtil.split(cacheName, ',');
        sysCacheService.clearCache(cacheList);
        return RespBody.success();
    }

}
