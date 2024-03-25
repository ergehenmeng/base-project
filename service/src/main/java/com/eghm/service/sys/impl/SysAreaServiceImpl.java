package com.eghm.service.sys.impl;

import com.eghm.model.SysArea;
import com.eghm.cache.CacheProxyService;
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
        return cacheProxyService.getAreaById(id);
    }

    @Override
    public String parseArea(Long provinceId, Long cityId, Long countyId) {
        SysArea sysArea = cacheProxyService.getAreaById(provinceId);
        if (sysArea == null) {
            return this.parseArea(cityId, countyId);
        }
        return sysArea.getTitle() + this.parseArea(cityId, countyId);
    }

    @Override
    public String parseArea(Long cityId, Long countyId) {
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
    public String parseProvinceCity(Long provinceId, Long cityId) {
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
        SysArea sysArea = cacheProxyService.getAreaById(cityId);
        if (sysArea != null) {
            return sysArea.getTitle();
        }
        return null;
    }
}
