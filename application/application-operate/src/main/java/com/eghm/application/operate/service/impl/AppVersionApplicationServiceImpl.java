package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.operate.version.VersionAddRequest;
import com.eghm.application.shared.dto.operate.version.VersionEditRequest;
import com.eghm.domain.operate.model.AppVersion;
import com.eghm.domain.operate.repository.AppVersionRepository;
import com.eghm.domain.operate.service.AppVersionDomainService;
import com.eghm.application.operate.service.AppVersionApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.VersionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/8/22 14:38
 */
@Slf4j
@AllArgsConstructor
@Service("versionService")
public class AppVersionApplicationServiceImpl implements AppVersionApplicationService {

    private final AppVersionRepository appVersionRepository;

    private static final AppVersionDomainService APP_VERSION_DOMAIN_SERVICE = new AppVersionDomainService();

    @Override
    public void create(VersionAddRequest request) {
        APP_VERSION_DOMAIN_SERVICE.assertVersionAvailable(appVersionRepository, request.getVersion());
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.initialize(VersionUtil.parseInt(request.getVersion()));
        appVersionRepository.save(version);
    }

    @Override
    public void update(VersionEditRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        appVersionRepository.update(version);
    }

    @Override
    public void updateState(Long id, Boolean state) {
        AppVersion version = appVersionRepository.findById(id);
        if (version == null) {
            return;
        }
        version.changeState(state);
        appVersionRepository.updateState(version.getId(), version.getState());
    }

    @Override
    public void delete(Long id) {
        appVersionRepository.deleteById(id);
    }

}
