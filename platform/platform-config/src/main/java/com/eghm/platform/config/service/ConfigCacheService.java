package com.eghm.platform.config.service;

import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.web.utility.MybatisUtil;
import com.eghm.platform.config.entity.SysArea;
import com.eghm.platform.config.entity.SysConfig;
import com.eghm.platform.config.entity.SysDictItem;
import com.eghm.platform.config.mapper.SysAreaMapper;
import com.eghm.platform.config.mapper.SysConfigMapper;
import com.eghm.platform.config.mapper.SysDictItemMapper;
import com.eghm.platform.config.vo.SysAreaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("configCacheService")
@RequiredArgsConstructor
public class ConfigCacheService {

    private final SysAreaMapper sysAreaMapper;
    private final SysConfigMapper sysConfigMapper;
    private final SysDictItemMapper sysDictItemMapper;

    @Cacheable(cacheNames = CacheConstant.SYS_AREA, unless = "#result.size() == 0")
    public List<SysAreaVO> getAreaList() {
        return sysAreaMapper.getList(null);
    }

    @Cacheable(cacheNames = CacheConstant.SYS_AREA_ID, key = "#p0", unless = "#result == null")
    public SysArea getAreaById(Long id) {
        return sysAreaMapper.selectById(id);
    }

    @Cacheable(cacheNames = CacheConstant.SYS_CONFIG, key = "#p0", unless = "#result == null")
    public String getConfigByNid(String nid) {
        SysConfig config = MybatisUtil.getOne(sysConfigMapper, SysConfig::getNid, nid);
        return config != null ? config.getContent() : null;
    }

    @Cacheable(cacheNames = CacheConstant.SYS_DICT, key = "#p0", unless = "#result.size() == 0")
    public List<SysDictItem> getDictByNid(String nid) {
        return MybatisUtil.getList(sysDictItemMapper, SysDictItem::getNid, nid);
    }
}
