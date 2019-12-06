package com.fanyin.service.system.impl;


import com.fanyin.common.constant.CacheConstant;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.dao.mapper.system.SystemConfigMapper;
import com.fanyin.dao.model.system.SystemConfig;
import com.fanyin.model.dto.system.config.ConfigEditRequest;
import com.fanyin.model.dto.system.config.ConfigQueryRequest;
import com.fanyin.service.system.SystemConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统参数配置服务类,系统参数无权限删除
 * @author 二哥很猛
 * @date 2018/1/12 09:46
 */
@Service("systemConfigService")
@Transactional(rollbackFor = RuntimeException.class)
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public void updateConfig(ConfigEditRequest request) {
        int i = systemConfigMapper.updateConfig(request);
        if(i != 1){
            throw new BusinessException(ErrorCode.UPDATE_CONFIG_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = RuntimeException.class)
    public PageInfo<SystemConfig> getByPage(ConfigQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<SystemConfig> list = systemConfigMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYSTEM_CONFIG,key = "#p0",unless = "#result == null")
    @Transactional(readOnly = true,rollbackFor = RuntimeException.class)
    public String getByNid(String nid) {
        SystemConfig config = systemConfigMapper.getByNid(nid);
        if(config != null){
            return config.getContent();
        }
        return null;
    }

    @Override
    public SystemConfig getById(Integer id) {
        return systemConfigMapper.selectByPrimaryKey(id);
    }


}
