package com.eghm.service.sys.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.config.ConfigEditRequest;
import com.eghm.dto.sys.config.ConfigQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysConfigMapper;
import com.eghm.model.SysConfig;
import com.eghm.service.sys.SysConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.ext.SysConfigResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统参数配置服务类,系统参数无权限删除
 *
 * @author 二哥很猛
 * @since 2018/1/12 09:46
 */
@Service("sysConfigService")
@AllArgsConstructor
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
        sysConfigMapper.updateById(DataUtil.copy(request, SysConfig.class));
    }
}
