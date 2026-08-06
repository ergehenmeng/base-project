package com.eghm.platform.config.service.impl;

import com.eghm.platform.config.entity.SysArea;
import com.eghm.platform.config.service.ConfigCacheService;
import com.eghm.platform.config.service.SysAreaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/2/13 10:25
 */
@Service
@AllArgsConstructor
public class SysAreaServiceImpl implements SysAreaService {

    private final ConfigCacheService configCacheService;

    @Override
    public String parseArea(Long provinceId, Long cityId, Long countyId) {
        if (provinceId == null) {
            return this.parseArea(cityId, countyId);
        }
        SysArea sysArea = configCacheService.getAreaById(provinceId);
        if (sysArea == null) {
            return this.parseArea(cityId, countyId);
        }
        return sysArea.getTitle() + this.parseArea(cityId, countyId);
    }

    @Override
    public String parseArea(Long cityId, Long countyId) {
        if (cityId == null || countyId == null) {
            return "";
        }
        String address = "";
        SysArea sysArea = configCacheService.getAreaById(cityId);
        if (sysArea != null) {
            address += sysArea.getTitle();
        }
        sysArea = configCacheService.getAreaById(countyId);
        if (sysArea != null) {
            address += sysArea.getTitle();
        }
        return address;
    }

    @Override
    public String parseArea(Long cityId, Long countyId, String address) {
        if (address == null) {
            return this.parseArea(cityId, countyId);
        }
        return this.parseArea(cityId, countyId) + address;
    }

}
