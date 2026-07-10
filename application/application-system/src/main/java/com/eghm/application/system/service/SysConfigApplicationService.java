package com.eghm.application.system.service;

import com.eghm.application.shared.configuration.config.ConfigRegistry;
import com.eghm.application.shared.dto.sys.config.ConfigEditRequest;
import com.eghm.domain.system.model.SysConfig;
import com.eghm.domain.system.repository.SysConfigRepository;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统参数配置服务类,系统参数无权限删除
 *
 * @author 二哥很猛
 * @since 2018/1/12 09:46
 */
@Service
@AllArgsConstructor
public class SysConfigApplicationService {

    private final SysConfigRepository sysConfigRepository;

    /**
     * 更新系统参数
     *
     * @param request 待更新的参数对象
     */
    public void update(ConfigEditRequest request) {
        SysConfig config = sysConfigRepository.findById(request.getId());
        config.assertEditable();
        ConfigRegistry.handle(config.getNid(), request.getContent());
        SysConfig updateConfig = DataUtil.copy(request, SysConfig.class);
        updateConfig.changeContent(request.getContent());
        sysConfigRepository.update(updateConfig);
    }
}
