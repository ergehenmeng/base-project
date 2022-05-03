package com.eghm.web.controller;

import cn.hutool.core.util.StrUtil;
import com.eghm.dao.model.SysCache;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.SysCacheService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/cache")
public class CacheController {

    private final SysCacheService sysCacheService;

    /**
     * 查询所有的缓存列表
     *
     * @return 缓存列表
     */
    @GetMapping("/list")
    @ApiOperation("缓存列表(不分页)")
    public Paging<SysCache> list() {
        return new Paging<>(sysCacheService.getList());
    }

    /**
     * 清除缓存
     *
     * @param cacheName 缓存名称
     * @return 成功响应
     */
    @PostMapping("/cache/clear")
    @Mark
    @ApiOperation("清除缓存")
    @ApiImplicitParam(name = "cacheName", value = "缓存名称,逗号分割", required = true)
    public RespBody<Void> clear(@RequestParam("cacheName") String cacheName) {
        List<String> cacheList = StrUtil.split(cacheName, ',');
        sysCacheService.clearCache(cacheList);
        return RespBody.success();
    }
}
