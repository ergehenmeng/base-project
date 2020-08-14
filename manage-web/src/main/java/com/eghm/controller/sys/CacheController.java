package com.eghm.controller.sys;

import com.eghm.annotation.Mark;
import com.eghm.dao.model.sys.SysCache;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.cache.SysCacheService;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 缓存管理
 *
 * @author 二哥很猛
 * @date 2019/1/14 14:12
 */
@Controller
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
    @PostMapping("/system/cache/list")
    @ResponseBody
    public Paging<SysCache> list() {
        return new Paging<>(sysCacheService.getList());
    }

    /**
     * 清除缓存
     *
     * @param cacheName 缓存名称
     * @return 成功响应
     */
    @SuppressWarnings("UnstableApiUsage")
    @PostMapping("/system/cache/clear")
    @ResponseBody
    @Mark
    public RespBody<Object> clear(String cacheName) {
        List<String> cacheList = Splitter.on(",").splitToList(cacheName);
        sysCacheService.clearCache(cacheList);
        return RespBody.success();
    }
}
