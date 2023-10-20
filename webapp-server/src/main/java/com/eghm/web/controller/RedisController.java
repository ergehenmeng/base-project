package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.SensitiveWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    public RespBody<Boolean> setLimit(@RequestParam("key") String key, @RequestParam("maxLimit") Integer maxLimit, @RequestParam("maxTtl") Long maxTtl) {
        boolean absent = cacheService.limit(key, maxLimit, maxTtl);
        return RespBody.success(absent);
    }

    @GetMapping("/getBitmap")
    @ApiOperation("getBitmap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "offset", value = "offset", required = true)
    })
    public RespBody<Long> getBitmap32(@RequestParam("key") String key, @RequestParam("offset") Long offset) {
        long absent = cacheService.getBitmapOffset(key, offset);
        return RespBody.success(absent);
    }

    @GetMapping("/setBitmap")
    @ApiOperation("设置bitmap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "offset", value = "offset", required = true)
    })
    public RespBody<Void> setBitmap(@RequestParam("key") String key, @RequestParam("offset") Long offset) {
        cacheService.setBitmap(key, offset, true);
        return RespBody.success();
    }

    @GetMapping("/match")
    @ApiOperation("匹配")
    @ApiImplicitParam(name = "keyword", value = "keyword", required = true)
    public RespBody<Boolean> match(@RequestParam("keyword") String keyword) {
        boolean checkSerial = sensitiveWordService.match(keyword);
        return RespBody.success(checkSerial);
    }

    @GetMapping("/signIn")
    @ApiOperation("签到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "localDate", value = "localDate", required = true)
    })
    public RespBody<Boolean> signIn(@RequestParam("key") String key, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("localDate") LocalDate localDate) {
        LocalDate registerDate = LocalDate.of(2023, 5, 3);
        long day = ChronoUnit.DAYS.between(registerDate, localDate);
        Boolean signIn = cacheService.getBitmap(key, day);
        if (Boolean.TRUE.equals(signIn)) {
            return RespBody.success(false);
        }
        cacheService.setBitmap(key, day, true);
        return RespBody.success(true);
    }
}
