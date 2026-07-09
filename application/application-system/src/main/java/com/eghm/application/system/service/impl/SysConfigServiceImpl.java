package com.eghm.application.system.service.impl;

import com.eghm.dto.ext.Page;
import com.eghm.configuration.config.ConfigRegistry;
import com.eghm.dto.sys.config.ConfigEditRequest;
import com.eghm.dto.sys.config.ConfigQueryRequest;
import com.eghm.application.system.service.SysConfigQueryGateway;
import com.eghm.application.system.service.SysConfigService;
import com.eghm.domain.system.model.SysConfig;
import com.eghm.domain.system.repository.SysConfigRepository;
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
@AllArgsConstructor
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigRepository sysConfigRepository;

    private final SysConfigQueryGateway sysConfigQueryGateway;

    @Override
    public Page<SysConfigResponse> getByPage(ConfigQueryRequest request) {
        return sysConfigQueryGateway.getByPage(request);
    }

    @Override
    public void update(ConfigEditRequest request) {
        SysConfig config = sysConfigRepository.findById(request.getId());
        config.assertEditable();
        ConfigRegistry.handle(config.getNid(), request.getContent());
        SysConfig updateConfig = DataUtil.copy(request, SysConfig.class);
        updateConfig.changeContent(request.getContent());
        sysConfigRepository.update(updateConfig);
    }
}
