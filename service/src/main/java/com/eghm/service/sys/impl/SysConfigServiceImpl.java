package com.eghm.service.sys.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.SysConfigMapper;
import com.eghm.dao.model.SysConfig;
import com.eghm.model.dto.config.ConfigEditRequest;
import com.eghm.model.dto.config.ConfigQueryRequest;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.SysConfigService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统参数配置服务类,系统参数无权限删除
 *
 * @author 二哥很猛
 * @date 2018/1/12 09:46
 */
@Service("sysConfigService")
@AllArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public void update(ConfigEditRequest request) {
        SysConfig config = DataUtil.copy(request, SysConfig.class);
        sysConfigMapper.updateById(config);
    }

    @Override
    public Page<SysConfig> getByPage(ConfigQueryRequest request) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getLocked() != null, SysConfig::getLocked, request.getLocked());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysConfig::getTitle, request.getQueryName()).or()
                        .like(SysConfig::getNid, request.getQueryName()).or()
                        .like(SysConfig::getRemark, request.getQueryName()));
        return sysConfigMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public String getByNid(String nid) {
        return cacheProxyService.getConfigByNid(nid);
    }

    @Override
    public SysConfig getById(Long id) {
        return sysConfigMapper.selectById(id);
    }


}
