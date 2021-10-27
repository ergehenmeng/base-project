package com.eghm.web.controller;

import cn.hutool.core.util.StrUtil;
import com.eghm.dao.model.SysCache;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.SysCacheService;
import com.eghm.web.annotation.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 缓存管理
 *
 * @author 二哥很猛
 * @date 2019/1/14 14:12
 */
@RestController
public class CacheController {

    private SysCacheService sysCacheService;

    @Autowired
    public void setSysCacheService(SysCacheService sysCacheService) {
        this.sysCacheService = sysCacheService;
    }

    /**
     * 查询所有的缓存列表
     *
     * @return 缓存列表
     */
    @GetMapping("/cache/list")
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
    public RespBody<Object> clear(String cacheName) {
        List<String> cacheList = StrUtil.split(cacheName, ',');
        sysCacheService.clearCache(cacheList);
        return RespBody.success();
    }
}
