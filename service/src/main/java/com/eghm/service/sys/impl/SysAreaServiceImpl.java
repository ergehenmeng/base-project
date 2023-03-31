package com.eghm.service.sys.impl;

import com.eghm.utils.StringUtil;
import com.eghm.mapper.SysAreaMapper;
import com.eghm.model.SysArea;
import com.eghm.vo.sys.SysAreaVO;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/2/13 10:25
 */
@Service("sysAreaService")
@AllArgsConstructor
public class SysAreaServiceImpl implements SysAreaService {

    private final SysAreaMapper sysAreaMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public void calcInitial() {
        List<SysArea> list = sysAreaMapper.selectList(null);
        list.forEach(sysArea -> {
            String title = sysArea.getTitle();
            String initial = StringUtil.getInitial(title);
            sysArea.setMark(initial);
            sysAreaMapper.updateById(sysArea);
        });
    }

    @Override
    public List<SysAreaVO> getByPid(Long pid) {
        List<SysArea> selectList = cacheProxyService.getAreaByPid(pid);
        return DataUtil.copy(selectList, sysArea -> DataUtil.copy(sysArea, SysAreaVO.class));

    }

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
}
