package com.fanyin.controller.system;

import com.fanyin.annotation.Mark;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.dao.model.system.SystemCache;
import com.fanyin.service.cache.SystemCacheService;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 缓存管理
 * @author 二哥很猛
 * @date 2019/1/14 14:12
 */
@Controller
public class CacheController {

    @Autowired
    private SystemCacheService systemCacheService;

    /**
     * 查询所有的缓存列表
     * @return 缓存列表
     */
    @PostMapping("/system/cache/list")
    @ResponseBody
    public Paging<SystemCache> list(){
        return new Paging<>(systemCacheService.getList());
    }

    /**
     * 清除缓存
     * @param cacheName 缓存名称
     * @return 成功响应
     */
    @PostMapping("/system/cache/clear")
    @ResponseBody
    @Mark
    public RespBody clear(String cacheName){
        List<String> cacheList = Splitter.on(",").splitToList(cacheName);
        systemCacheService.clearCache(cacheList);
        return RespBody.getInstance();
    }
}
