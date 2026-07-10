package com.eghm.infrastructure.persistence.mybatis.operate.query;

import cn.hutool.core.collection.CollUtil;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.operate.version.VersionQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.AppVersionMapper;
import com.eghm.application.operate.query.AppVersionQueryService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.VersionUtil;
import com.eghm.application.shared.vo.operate.version.AppVersionResponse;
import com.eghm.application.shared.vo.operate.version.AppVersionVO;
import com.eghm.domain.operate.model.AppVersion;
import com.eghm.domain.operate.repository.AppVersionRepository;
import com.eghm.domain.shared.service.AlarmService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 手机版本 MyBatis 查询适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisAppVersionQueryService implements AppVersionQueryService {

    private final AppVersionMapper appVersionMapper;

    private final AlarmService alarmService;

    private final AppVersionRepository appVersionRepository;

    @Override
    public Page<AppVersionResponse> getByPage(Page<AppVersionResponse> page, VersionQueryRequest request) {
        return MybatisPageUtil.fromMybatis(appVersionMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
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
        if (Boolean.TRUE.equals(response.getForceUpdate())) {
            return response;
        }
        Integer startVersion = VersionUtil.parseInt(version);
        List<AppVersion> versionList = appVersionRepository.findForceUpdateVersions(channel, startVersion, latestVersion.getVersionNo());
        response.setForceUpdate(CollUtil.isNotEmpty(versionList));
        return response;
    }
}





