package com.eghm.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.version.VersionAddRequest;
import com.eghm.dto.version.VersionEditRequest;
import com.eghm.dto.version.VersionQueryRequest;
import com.eghm.enums.Channel;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.AppVersionMapper;
import com.eghm.model.AppVersion;
import com.eghm.service.common.AppVersionService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.utils.VersionUtil;
import com.eghm.vo.version.AppVersionVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
@Service("versionService")
@AllArgsConstructor
@Slf4j
public class AppVersionServiceImpl implements AppVersionService {

    private final AppVersionMapper appVersionMapper;

    private final SysConfigApi sysConfigApi;

    @Override
    public Page<AppVersion> getByPage(VersionQueryRequest request) {
        LambdaQueryWrapper<AppVersion> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), AppVersion::getVersion, request.getQueryName());
        wrapper.eq(StrUtil.isNotBlank(request.getClassify()), AppVersion::getClassify, request.getClassify());
        return appVersionMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(VersionAddRequest request) {
        this.redoVersion(request.getVersion());
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.setVersionNo(VersionUtil.parseInt(request.getVersion()));
        appVersionMapper.insert(version);
    }

    @Override
    public void update(VersionEditRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        appVersionMapper.updateById(version);
    }

    @Override
    public AppVersionVO getLatestVersion() {
        String channel = ApiHolder.getChannel();
        String latestVersion = this.getLatestVersion(channel);

        String version = ApiHolder.getVersion();
        // 未找到最新版本,或者用户版本大于等于已上架版本
        if (VersionUtil.gte(version, latestVersion)) {
            return AppVersionVO.builder().latest(true).build();
        }
        AppVersion mapperVersion = appVersionMapper.getVersion(channel, latestVersion);

        AppVersionVO response = DataUtil.copy(mapperVersion, AppVersionVO.class);
        // 最新版本是强制更新版本
        if (Boolean.TRUE.equals(response.getForceUpdate())) {
            return response;
        }

        // 用户自己当前的软件版本信息
        AppVersion appVersion = appVersionMapper.getVersion(channel, version);
        //未找到用户安装的版本信息,默认不强更
        if (appVersion == null) {
            return response;
        }
        // 如果用户版本非常老,最新版本不是强制更新版本,但中间某个版本是强制更新,用户一样需要强制更新
        Integer startVersion = appVersion.getVersionNo();
        // 查询用户版本与最新版本之间的版本
        List<AppVersion> versionList = appVersionMapper.getForceUpdateVersion(channel, startVersion, VersionUtil.parseInt(latestVersion));
        response.setForceUpdate(CollUtil.isNotEmpty(versionList));
        return response;
    }

    @Override
    public void delete(Long id) {
        AppVersion appVersion = appVersionMapper.selectById(id);
        if (appVersion == null) {
            log.info("该版本可能已被删除 [{}]", id);
            return;
        }
        String version = this.getLatestVersion(appVersion.getClassify());

        if (appVersion.getVersion().equals(version)) {
            log.error("当前版本无法被删除 [{}] [{}]", id, version);
            throw new BusinessException(ErrorCode.CURRENT_VERSION_DELETE);
        }
        appVersionMapper.deleteById(id);
    }

    /**
     * 校验版本号是否重复
     * @param version 版本号
     */
    private void redoVersion(String version) {
        LambdaQueryWrapper<AppVersion> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppVersion::getVersion, version);
        Long count = appVersionMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("版本号重复 [{}]", version);
            throw new BusinessException(ErrorCode.VERSION_REDO);
        }
    }

    /**
     * 获取服务端最新版本号
     * @param channel IOS ANDROID
     * @return 版本号
     */
    private String getLatestVersion(String channel) {
        if (Channel.IOS.name().equals(channel)) {
            return sysConfigApi.getString(ConfigConstant.IOS_LATEST_VERSION);
        }
        return sysConfigApi.getString(ConfigConstant.ANDROID_LATEST_VERSION);
    }
}
