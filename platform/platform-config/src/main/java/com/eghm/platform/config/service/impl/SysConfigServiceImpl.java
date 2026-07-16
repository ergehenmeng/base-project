package com.eghm.platform.config.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.config.config.config.ConfigRegistry;
import com.eghm.platform.config.dto.ConfigEditRequest;
import com.eghm.platform.config.dto.ConfigQueryRequest;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.platform.config.mapper.SysConfigMapper;
import com.eghm.platform.config.entity.SysConfig;
import com.eghm.platform.config.service.SysConfigService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.platform.config.vo.SysConfigResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统参数配置服务类,系统参数无权限删除
 *
 * @author 二哥很猛
 * @since 2018/1/12 09:46
 */
@AllArgsConstructor
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    @Override
    public Page<SysConfigResponse> getByPage(ConfigQueryRequest request) {
        return sysConfigMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void update(ConfigEditRequest request) {
        SysConfig config = sysConfigMapper.selectById(request.getId());
        if (Boolean.TRUE.equals(config.getLocked())) {
            throw new BusinessException(ErrorCode.CONFIG_LOCK_ERROR);
        }
        ConfigRegistry.handle(config.getNid(), request.getContent());
        DataUtil.copy(request, SysConfig.class, sysConfigMapper::updateById);
    }
}
