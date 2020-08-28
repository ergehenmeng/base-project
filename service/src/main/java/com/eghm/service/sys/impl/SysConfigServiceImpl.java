package com.eghm.service.sys.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.system.SysConfigMapper;
import com.eghm.dao.model.system.SysConfig;
import com.eghm.model.dto.sys.config.ConfigEditRequest;
import com.eghm.model.dto.sys.config.ConfigQueryRequest;
import com.eghm.service.sys.SysConfigService;
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
@Service("sysConfigService")
@Transactional(rollbackFor = RuntimeException.class)
public class SysConfigServiceImpl implements SysConfigService {

    private SysConfigMapper sysConfigMapper;

    @Autowired
    public void setSysConfigMapper(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }

    @Override
    public void updateConfig(ConfigEditRequest request) {
        int i = sysConfigMapper.updateConfig(request);
        if (i != 1) {
            throw new BusinessException(ErrorCode.UPDATE_CONFIG_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public PageInfo<SysConfig> getByPage(ConfigQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysConfig> list = sysConfigMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_CONFIG, key = "#p0", unless = "#result == null")
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public String getByNid(String nid) {
        SysConfig config = sysConfigMapper.getByNid(nid);
        if (config != null) {
            return config.getContent();
        }
        return null;
    }

    @Override
    public SysConfig getById(Integer id) {
        return sysConfigMapper.selectByPrimaryKey(id);
    }


}
