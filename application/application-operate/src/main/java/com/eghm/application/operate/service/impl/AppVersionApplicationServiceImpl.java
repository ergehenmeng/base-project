package com.eghm.application.operate.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.domain.shared.service.AlarmService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.operate.version.VersionAddRequest;
import com.eghm.application.shared.dto.operate.version.VersionEditRequest;
import com.eghm.application.shared.dto.operate.version.VersionQueryRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.AppVersion;
import com.eghm.domain.operate.repository.AppVersionRepository;
import com.eghm.application.operate.query.AppVersionQueryService;
import com.eghm.application.operate.service.AppVersionApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.VersionUtil;
import com.eghm.application.shared.vo.operate.version.AppVersionResponse;
import com.eghm.application.shared.vo.operate.version.AppVersionVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/8/22 14:38
 */
@Slf4j
@AllArgsConstructor
@Service("versionService")
public class AppVersionApplicationServiceImpl implements AppVersionApplicationService {

    private final AlarmService alarmService;

    private final AppVersionRepository appVersionRepository;

    private final AppVersionQueryService appVersionQueryService;

    @Override
    public Page<AppVersionResponse> getByPage(VersionQueryRequest request) {
        return appVersionQueryService.getByPage(request.createPage(), request);
    }

    @Override
    public void create(VersionAddRequest request) {
        this.assertVersionAvailable(request.getVersion());
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
    public AppVersionVO getLatestVersion() {
        String channel = ApiHolder.getChannel();
        String version = ApiHolder.getVersion();
        AppVersion latestVersion = appVersionRepository.findLatestVersion(channel);
        if (latestVersion == null) {
            alarmService.sendMsg(String.format("V%s最新版本尚未配置", channel));
            return AppVersionVO.builder().latest(true).build();
        }
        AppVersionVO response = DataUtil.copy(latestVersion, AppVersionVO.class);
        // 最新版本是强制更新版本
        if (Boolean.TRUE.equals(response.getForceUpdate())) {
            return response;
        }
        // 如果用户版本非常老,最新版本不是强制更新版本,但中间某个版本是强制更新,用户一样需要强制更新
        Integer startVersion = VersionUtil.parseInt(version);
        // 查询用户版本与最新版本之间的版本
        List<AppVersion> versionList = appVersionRepository.findForceUpdateVersions(channel, startVersion, latestVersion.getVersionNo());
        response.setForceUpdate(CollUtil.isNotEmpty(versionList));
        return response;
    }

    @Override
    public void delete(Long id) {
        appVersionRepository.deleteById(id);
    }

    private void assertVersionAvailable(String version) {
        if (appVersionRepository.existsByVersion(version)) {
            log.warn("版本号重复 [{}]", version);
            throw new BusinessException(ErrorCode.VERSION_REDO);
        }
    }
}
