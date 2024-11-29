package com.eghm.web.controller;

import com.eghm.cache.CacheService;
import com.eghm.common.GeoService;
import com.eghm.dto.ext.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * @author wyb
 * @since 2023/6/19
 */
@RestController
@Api(tags = "缓存相关")
@AllArgsConstructor
@RequestMapping(value = "/webapp/redis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RedisController {

    private final GeoService geoService;

    private final CacheService cacheService;

    private final StringRedisTemplate stringRedisTemplate;

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

    @GetMapping("/size")
    @ApiOperation("大小")
    @ApiImplicitParam(name = "key", value = "key", required = true)
    public RespBody<Long> size(@RequestParam("key") String key) {
        long checkSerial = cacheService.getHashSize(key);
        return RespBody.success(checkSerial);
    }

    @GetMapping("/setSet")
    @ApiOperation("设置分数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "value", value = "value", required = true),
            @ApiImplicitParam(name = "score", value = "score", required = true),
    })
    public RespBody<Void> setSet(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("score") long score) {
        cacheService.setSet(key, value, score);
        return RespBody.success();
    }

    @GetMapping("/distance")
    @ApiOperation("距离")
    public RespBody<Double> distance(Double longitude, Double latitude, Double targetLongitude, Double targetLatitude) {
        double distance = geoService.distance(longitude, latitude, targetLongitude, targetLatitude);
        return RespBody.success(distance);
    }

    @GetMapping("/incr")
    @ApiOperation("自增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "ops", value = "ops", required = true),
    })
    public RespBody<Long> incr(@RequestParam("key") String key, @RequestParam("ops") long ops) {
        Long increment = stringRedisTemplate.opsForValue().increment(key, ops);
        return RespBody.success(increment);
    }

    @GetMapping("/setSetIncrement")
    @ApiOperation("累计设置分数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "value", value = "value", required = true),
            @ApiImplicitParam(name = "score", value = "score", required = true),
    })
    public RespBody<Void> setSetIncrement(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("score") long score) {
        cacheService.setSetIncrement(key, value, score);
        return RespBody.success();
    }

    @GetMapping("/getRange")
    @ApiOperation("累计设置分数")
    @ApiImplicitParam(name = "key", value = "key", required = true)
    public RespBody<Set<ZSetOperations.TypedTuple<String>>> getRange(@RequestParam("key") String key) {
        Set<ZSetOperations.TypedTuple<String>> withScore = cacheService.rangeWithScore(key, 4);
        return RespBody.success(withScore);
    }

    @GetMapping("/signIn")
    @ApiOperation("签到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "localDate", value = "localDate", required = true)
    })
    public RespBody<Boolean> signIn(@RequestParam("key") String key, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("localDate") LocalDate localDate) {
        LocalDate registerDate = LocalDate.of(2024, 2, 3);
        long day = ChronoUnit.DAYS.between(registerDate, localDate);
        Boolean signIn = cacheService.getBitmap(key, day);
        if (Boolean.TRUE.equals(signIn)) {
            return RespBody.success(false);
        }
        cacheService.setBitmap(key, day, true);
        return RespBody.success(true);
    }
}
