package com.eghm.application.system.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.configuration.config.ConfigRegistry;
import com.eghm.application.shared.dto.sys.config.ConfigEditRequest;
import com.eghm.application.shared.dto.sys.config.ConfigQueryRequest;
import com.eghm.application.system.query.SysConfigQueryService;
import com.eghm.application.system.service.SysConfigApplicationService;
import com.eghm.domain.system.model.SysConfig;
import com.eghm.domain.system.repository.SysConfigRepository;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.sys.ext.SysConfigResponse;
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
public class SysConfigApplicationServiceImpl implements SysConfigApplicationService {

    private final SysConfigRepository sysConfigRepository;

    private final SysConfigQueryService sysConfigQueryGateway;

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
