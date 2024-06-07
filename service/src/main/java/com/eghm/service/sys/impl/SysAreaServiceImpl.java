package com.eghm.service.sys.impl;

import com.eghm.cache.CacheProxyService;
import com.eghm.model.SysArea;
import com.eghm.service.sys.SysAreaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/2/13 10:25
 */
@Service("sysAreaService")
@AllArgsConstructor
public class SysAreaServiceImpl implements SysAreaService {

    private final CacheProxyService cacheProxyService;

    @Override
    public SysArea getById(Long id) {
        if (id == null) {
            return null;
        }
        return cacheProxyService.getAreaById(id);
    }

    @Override
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

    @Override
    public String parseArea(Long provinceId, Long cityId, Long countyId, String address) {
        if (address == null) {
            return this.parseArea(provinceId, cityId, countyId);
        }
        return this.parseArea(provinceId, cityId, countyId) + address;
    }

    @Override
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

    @Override
    public String parseArea(Long cityId, Long countyId, String address) {
        if (address == null) {
            return this.parseArea(cityId, countyId);
        }
        return this.parseArea(cityId, countyId) + address;
    }

    @Override
    public String parseProvinceCity(Long provinceId, Long cityId) {
        if (provinceId == null || cityId == null) {
            return "";
        }
        SysArea sysArea = cacheProxyService.getAreaById(provinceId);
        String address = "";
        if (sysArea != null) {
            address += sysArea.getTitle();
        }
        sysArea = cacheProxyService.getAreaById(cityId);
        if (sysArea != null) {
            address += sysArea.getTitle();
        }
        return address;
    }

    @Override
    public String parseCity(Long cityId) {
        if (cityId == null) {
            return "";
        }
        SysArea sysArea = cacheProxyService.getAreaById(cityId);
        if (sysArea != null) {
            return sysArea.getTitle();
        }
        return "";
    }
}
