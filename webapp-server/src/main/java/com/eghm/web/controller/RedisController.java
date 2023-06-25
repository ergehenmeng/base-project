package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.enums.Channel;
import com.eghm.service.cache.CacheService;
import com.eghm.web.annotation.ClientType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @since 2023/6/19
 */
@RestController
@Api(tags = "缓存相关")
@AllArgsConstructor
@RequestMapping("/webapp/redis")
public class RedisController {

    private final CacheService cacheService;

    @GetMapping("/setValue")
    @ApiOperation("设置值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "value", value = "value", required = true)
    })
    @ClientType(Channel.PC)
    public RespBody<Boolean> setValue(@RequestParam("key") String key, @RequestParam("value") String value) {
        boolean absent = cacheService.setIfAbsent(key, value);
        return RespBody.success(absent);
    }
}
