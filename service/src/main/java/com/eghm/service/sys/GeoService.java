package com.eghm.service.sys;

import java.util.LinkedHashMap;

/**
 * @author wyb
 * @date 2022/7/11
 */
public interface GeoService {

    /**
     * 计算两点之间的距离
     * @param longitude 经度
     * @param latitude 维度
     * @param targetLongitude 另一个点的经度
     * @param targetLatitude 另一个点的维度
     * @return 距离 单位km
     */
    double distance(double longitude, double latitude, double targetLongitude, double targetLatitude);

    /**
     * 添加点位
     * @param key key
     * @param id  点位的唯一ID
     * @param longitude 经度
     * @param latitude 纬度
     */
    void addPoint(String key, String id, double longitude, double latitude);

    /**
     * 计算两点之间的距离
     * @param key key 类似于分组ID
     * @param id 第一个点
     * @param targetLongitude 另一个点的经度
     * @param targetLatitude 另一个点的维度
     * @return 距离 单位km
     */
    double distance(String key, String id, double targetLongitude, double targetLatitude);

    /**
     * 距离排序
     * @param key key
     * @param longitude 中心点位经度
     * @param latitude 中心点位纬度
     * @param radius  查询的半径范围, 单位:km
     * @return 排序后的member
     */
    LinkedHashMap<String, Double> radius(String key, double longitude, double latitude, double radius);

    /**
     * 距离排序
     * @param key key
     * @param longitude 中心点位经度
     * @param latitude 中心点位纬度
     * @param radius  查询的半径范围, 单位:km
     * @param limit   查询多少条
     * @return 排序 key 唯一ID, value 距离
     */
    LinkedHashMap<String, Double> radius(String key, double longitude, double latitude, double radius, int limit);

}
