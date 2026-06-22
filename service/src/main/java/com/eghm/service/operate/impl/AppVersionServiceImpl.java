package com.eghm.service.operate.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.AlarmService;
import com.eghm.configuration.authentication.ApiHolder;
import com.eghm.dto.operate.version.VersionAddRequest;
import com.eghm.dto.operate.version.VersionEditRequest;
import com.eghm.dto.operate.version.VersionQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.mapper.AppVersionMapper;
import com.eghm.model.AppVersion;
import com.eghm.service.operate.AppVersionService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.ValidationUtil;
import com.eghm.utils.VersionUtil;
import com.eghm.vo.operate.version.AppVersionResponse;
import com.eghm.vo.operate.version.AppVersionVO;
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
public class AppVersionServiceImpl implements AppVersionService {

    private final AlarmService alarmService;

    private final AppVersionMapper appVersionMapper;

    @Override
    public Page<AppVersionResponse> getByPage(VersionQueryRequest request) {
        return appVersionMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(VersionAddRequest request) {
        ValidationUtil.redoCheck(appVersionMapper, AppVersion::getVersion, request.getVersion(), null, null, ErrorCode.VERSION_REDO, "版本号重复 [{}] [{}]");
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.setVersionNo(VersionUtil.parseInt(request.getVersion()));
        version.setState(false);
        appVersionMapper.insert(version);
    }

    @Override
    public void update(VersionEditRequest request) {
        DataUtil.copy(request, AppVersion.class, appVersionMapper::updateById);
    }

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<AppVersion> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(AppVersion::getId, id);
        wrapper.set(AppVersion::getState, state);
        appVersionMapper.update(null, wrapper);
    }

    @Override
    public AppVersionVO getLatestVersion() {
        String channel = ApiHolder.getChannel();
        String version = ApiHolder.getVersion();
        AppVersion latestVersion = appVersionMapper.getVersion(channel);
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
        List<AppVersion> versionList = appVersionMapper.getForceUpdateVersion(channel, startVersion, latestVersion.getVersionNo());
        response.setForceUpdate(CollUtil.isNotEmpty(versionList));
        return response;
    }

    @Override
    public void delete(Long id) {
        appVersionMapper.deleteById(id);
    }

}
