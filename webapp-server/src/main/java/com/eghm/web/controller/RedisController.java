package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.enums.Channel;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.SensitiveWordService;
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

    private final SensitiveWordService sensitiveWordService;

    @GetMapping("/setValue")
    @ApiOperation("设置值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "value", value = "value", required = true)
    })
    public RespBody<Boolean> setValue(@RequestParam("key") String key, @RequestParam("value") String value) {
        boolean absent = cacheService.setIfAbsent(key, value);
        return RespBody.success(absent);
    }

    @GetMapping("/setLimit")
    @ApiOperation("限制设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "maxLimit", value = "maxLimit", required = true),
            @ApiImplicitParam(name = "maxTtl", value = "maxTtl", required = true),
    })
    @ClientType(Channel.WECHAT)
    public RespBody<Boolean> setLimit(@RequestParam("key") String key, @RequestParam("maxLimit") Integer maxLimit, @RequestParam("maxTtl") Long maxTtl) {
        boolean absent = cacheService.limit(key, maxLimit, maxTtl);
        return RespBody.success(absent);
    }

    @GetMapping("/getBitmap64")
    @ApiOperation("bitmap64长度")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "end", value = "end", required = true)
    })
    @ClientType(Channel.WECHAT)
    public RespBody<Long> getBitmap64(@RequestParam("key") String key, @RequestParam("end") Long end) {
        long absent = cacheService.getBitmap64(key, end);
        return RespBody.success(absent);
    }

    @GetMapping("/setBitmap")
    @ApiOperation("设置bitmap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "offset", value = "offset", required = true)
    })
    @ClientType(Channel.WECHAT)
    public RespBody<Void> setBitmap(@RequestParam("key") String key, @RequestParam("offset") Long offset) {
        cacheService.setBitmap(key, offset, true);
        return RespBody.success();
    }

    @GetMapping("/checkSerial")
    @ApiOperation("检查是否连续(64以内)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "end", value = "end", required = true),
            @ApiImplicitParam(name = "succession", value = "succession", required = true),
    })
    @ClientType(Channel.WECHAT)
    public RespBody<Boolean> checkSerial(@RequestParam("key") String key, @RequestParam("end") Long end, @RequestParam("succession") Integer succession) {
        boolean checkSerial = cacheService.checkSerial(key, end, succession);
        return RespBody.success(checkSerial);
    }

    @GetMapping("/match")
    @ApiOperation("匹配")
    @ApiImplicitParam(name = "keyword", value = "keyword", required = true)
    @ClientType(Channel.WECHAT)
    public RespBody<Boolean> match(@RequestParam("keyword") String keyword) {
        boolean checkSerial = sensitiveWordService.match(keyword);
        return RespBody.success(checkSerial);
    }
}
