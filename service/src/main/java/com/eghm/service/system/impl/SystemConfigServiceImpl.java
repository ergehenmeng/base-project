package com.eghm.service.system.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.system.SystemConfigMapper;
import com.eghm.dao.model.system.SystemConfig;
import com.eghm.model.dto.system.config.ConfigEditRequest;
import com.eghm.model.dto.system.config.ConfigQueryRequest;
import com.eghm.service.system.SystemConfigService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统参数配置服务类,系统参数无权限删除
 *
 * @author 二哥很猛
 * @date 2018/1/12 09:46
 */
@Service("systemConfigService")
@Transactional(rollbackFor = RuntimeException.class)
public class SystemConfigServiceImpl implements SystemConfigService {

    private SystemConfigMapper systemConfigMapper;

    @Autowired
    public void setSystemConfigMapper(SystemConfigMapper systemConfigMapper) {
        this.systemConfigMapper = systemConfigMapper;
    }

    @Override
    public void updateConfig(ConfigEditRequest request) {
        int i = systemConfigMapper.updateConfig(request);
        if (i != 1) {
            throw new BusinessException(ErrorCode.UPDATE_CONFIG_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public PageInfo<SystemConfig> getByPage(ConfigQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SystemConfig> list = systemConfigMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYSTEM_CONFIG, key = "#p0", unless = "#result == null")
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public String getByNid(String nid) {
        SystemConfig config = systemConfigMapper.getByNid(nid);
        if (config != null) {
            return config.getContent();
        }
        return null;
    }

    @Override
    public SystemConfig getById(Integer id) {
        return systemConfigMapper.selectByPrimaryKey(id);
    }


}
