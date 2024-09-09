package com.eghm.common.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.common.GeoService;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author 二哥很猛
 * @since 2022/7/11
 */
@Service("geoService")
@AllArgsConstructor
public class GeoServiceImpl implements GeoService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public double distance(double longitude, double latitude, double targetLongitude, double targetLatitude) {
        GeoOperations<String, String> operations = stringRedisTemplate.opsForGeo();
        String key = CacheConstant.GEO_DISTANCE + IdWorker.getIdStr();
        String key1 = IdWorker.getIdStr();
        String key2 = IdWorker.getIdStr();
        operations.add(key, new RedisGeoCommands.GeoLocation<>(key1, new Point(longitude, latitude)));
        operations.add(key, new RedisGeoCommands.GeoLocation<>(key2, new Point(targetLongitude, targetLatitude)));
        Distance distance = operations.distance(key, key1, key2, Metrics.MILES);
        stringRedisTemplate.delete(key);
        return distance != null ? distance.getValue() : 0;
    }

    @Override
    public void addPoint(String key, String id, double longitude, double latitude) {
        stringRedisTemplate.opsForGeo().add(key, new RedisGeoCommands.GeoLocation<>(id, new Point(longitude, latitude)));
    }

    @Override
    public double distance(String key, String id, double targetLongitude, double targetLatitude) {
        GeoOperations<String, String> operations = stringRedisTemplate.opsForGeo();
        String id2 = IdWorker.getIdStr();
        operations.add(key, new RedisGeoCommands.GeoLocation<>(id2, new Point(targetLongitude, targetLatitude)));
        Distance distance = operations.distance(key, id, id2, Metrics.MILES);
        operations.remove(key, id2);
        return distance != null ? distance.getValue() : 0;
    }

    @Override
    public LinkedHashMap<String, Double> radius(String key, double longitude, double latitude, double radius) {
        return radius(key, longitude, latitude, radius, CommonConstant.GET_LIMIT);
    }

    @Override
    public LinkedHashMap<String, Double> radius(String key, double longitude, double latitude, double radius, int limit) {
        GeoOperations<String, String> operations = stringRedisTemplate.opsForGeo();
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = operations.radius(key, new Circle(new Point(longitude, latitude), new Distance(radius, Metrics.MILES)),
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending().limit(limit));
        if (geoResults == null || CollUtil.isEmpty(geoResults)) {
            return Maps.newLinkedHashMapWithExpectedSize(1);
        }
        LinkedHashMap<String, Double> hashMap = Maps.newLinkedHashMapWithExpectedSize(limit);
        geoResults.forEach(result -> hashMap.put(result.getContent().getName(), result.getDistance().getValue()));
        return hashMap;
    }
}
