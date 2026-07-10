package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.version.VersionAddRequest;
import com.eghm.application.shared.dto.operate.version.VersionEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.VersionUtil;
import com.eghm.domain.operate.model.AppVersion;
import com.eghm.domain.operate.repository.AppVersionRepository;
import com.eghm.domain.operate.service.AppVersionDomainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/8/22 14:38
 */

@Service
@AllArgsConstructor
public class AppVersionApplicationService {

    private final AppVersionRepository appVersionRepository;

    private static final AppVersionDomainService APP_VERSION_DOMAIN_SERVICE = new AppVersionDomainService();

    /**
     * 添加app版本管理信息
     *
     * @param request 前台参数
     */
    public void create(VersionAddRequest request) {
        APP_VERSION_DOMAIN_SERVICE.assertVersionAvailable(appVersionRepository, request.getVersion());
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.initialize(VersionUtil.parseInt(request.getVersion()));
        appVersionRepository.save(version);
    }

    /**
     * 编辑保存app版本管理信息
     *
     * @param request 前台参数
     */
    public void update(VersionEditRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        appVersionRepository.update(version);
    }

    /**
     * 更新状态
     *
     * @param id    id
     * @param state 状态
     */
    public void updateState(Long id, Boolean state) {
        AppVersion version = appVersionRepository.findById(id);
        if (version == null) {
            return;
        }
        version.changeState(state);
        appVersionRepository.updateState(version.getId(), version.getState());
    }

    /**
     * 删除版本信息
     *
     * @param id 主键
     */
    public void delete(Long id) {
        appVersionRepository.deleteById(id);
    }
}
