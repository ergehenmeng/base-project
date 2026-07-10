package com.eghm.application.system.service;

import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.domain.system.model.SysArea;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/2/13 10:25
 */
@Service
@AllArgsConstructor
public class SysAreaApplicationService {

    private final CacheProxyService cacheProxyService;

    /**
     * 根据省市县id进行拼接
     *
     * @param provinceId 省份id
     * @param cityId     城市id
     * @param countyId   县区id
     * @return 浙江省杭州市西湖区
     */
    public String parseArea(Long provinceId, Long cityId, Long countyId) {
        if (provinceId == null) {
            return this.parseArea(cityId, countyId);
        }
        SysArea sysArea = cacheProxyService.getAreaById(provinceId);
        if (sysArea == null) {
            return this.parseArea(cityId, countyId);
        }
        return sysArea.getTitle() + this.parseArea(cityId, countyId);
    }

    /**
     * 根据省市县id进行拼接
     *
     * @param cityId   城市id
     * @param countyId 县区id
     * @return 杭州市西湖区
     */
    public String parseArea(Long cityId, Long countyId) {
        if (cityId == null || countyId == null) {
            return "";
        }
        String address = "";
        SysArea sysArea = cacheProxyService.getAreaById(cityId);
        if (sysArea != null) {
            address += sysArea.getTitle();
        }
        sysArea = cacheProxyService.getAreaById(countyId);
        if (sysArea != null) {
            address += sysArea.getTitle();
        }
        return address;
    }

    /**
     * 根据省市县id进行拼接
     *
     * @param cityId   城市id
     * @param countyId 县区id
     * @param address  详细地址
     * @return 杭州市西湖区
     */
    public String parseArea(Long cityId, Long countyId, String address) {
        if (address == null) {
            return this.parseArea(cityId, countyId);
        }
        return this.parseArea(cityId, countyId) + address;
    }
}
