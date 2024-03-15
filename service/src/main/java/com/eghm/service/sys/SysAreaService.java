package com.eghm.service.sys;

import com.eghm.model.SysArea;

/**
 * @author 二哥很猛
 * @since 2019/2/13 10:25
 */
public interface SysAreaService {

    /**
     * 主键查询
     *
     * @param id id
     * @return 地区
     */
    SysArea getById(Long id);

    /**
     * 根据省市县id进行拼接
     *
     * @param provinceId 省份id
     * @param cityId     城市id
     * @param countyId   县区id
     * @return 浙江省杭州市西湖区
     */
    String parseArea(Long provinceId, Long cityId, Long countyId);

    /**
     * 根据省市县id进行拼接
     *
     * @param cityId   城市id
     * @param countyId 县区id
     * @return 杭州市西湖区
     */
    String parseArea(Long cityId, Long countyId);

    /**
     * 根据省市id进行拼接
     *
     * @param provinceId 省份
     * @param cityId     城市id
     * @return 浙江省杭州市
     */
    String parseProvinceCity(Long provinceId, Long cityId);

    /**
     * 根据市id进行拼接
     *
     * @param cityId     城市id
     * @return 杭州市
     */
    String parseCity(Long cityId);
}

