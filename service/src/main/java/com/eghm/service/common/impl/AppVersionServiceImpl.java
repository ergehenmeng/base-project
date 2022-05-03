package com.eghm.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.VersionUtil;
import com.eghm.dao.mapper.AppVersionMapper;
import com.eghm.dao.model.AppVersion;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.version.VersionAddRequest;
import com.eghm.model.dto.version.VersionEditRequest;
import com.eghm.model.dto.version.VersionQueryRequest;
import com.eghm.model.vo.version.AppVersionVO;
import com.eghm.service.common.AppVersionService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
@Service("versionService")
@AllArgsConstructor
public class AppVersionServiceImpl implements AppVersionService {

    private final AppVersionMapper appVersionMapper;

    @Override
    public Page<AppVersion> getByPage(VersionQueryRequest request) {
        LambdaQueryWrapper<AppVersion> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), AppVersion::getVersion, request.getQueryName());
        wrapper.eq(request.getState() != null, AppVersion::getState, request.getState());
        wrapper.eq(StrUtil.isNotBlank(request.getClassify()), AppVersion::getClassify, request.getClassify());
        return appVersionMapper.selectPage(PageUtil.createPage(request), wrapper);
    }

    @Override
    public void addAppVersion(VersionAddRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.setVersionNo(VersionUtil.parseInt(request.getVersion()));
        appVersionMapper.insert(version);
    }

    @Override
    public void editAppVersion(VersionEditRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.setVersionNo(VersionUtil.parseInt(request.getVersion()));
        appVersionMapper.updateById(version);
    }

    @Override
    public void putAwayVersion(Long id) {
        AppVersion appVersion = appVersionMapper.selectById(id);
        AppVersion version = appVersionMapper.getVersion(appVersion.getClassify(), appVersion.getVersion());
        if (version != null) {
            throw new BusinessException(ErrorCode.VERSION_REDO);
        }
        appVersion.setState((byte) 1);
        appVersionMapper.updateById(appVersion);
        // 如果下载地址是在第三方服务器,则需要将最新版本推送到相关文件服务器
    }

    @Override
    public void soldOutVersion(Long id) {
        AppVersion version = new AppVersion();
        version.setId(id);
        version.setState((byte) 0);
        appVersionMapper.updateById(version);
    }

    @Override
    public AppVersionVO getLatestVersion() {
        String channel = ApiHolder.getChannel();
        AppVersion latestVersion = appVersionMapper.getLatestVersion(channel);
        String version = ApiHolder.getVersion();
        // 未找到最新版本,或者用户版本大于等于已上架版本
        if (latestVersion == null || VersionUtil.gte(version, latestVersion.getVersion())) {
            return AppVersionVO.builder().latest(true).build();
        }
        AppVersionVO response = DataUtil.copy(latestVersion, AppVersionVO.class);
        // 最新版本是强制更新版本
        if (Boolean.TRUE.equals(latestVersion.getForceUpdate())) {
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
        List<AppVersion> versionList = appVersionMapper.getForceUpdateVersion(channel, startVersion, latestVersion.getVersionNo());
        response.setForceUpdate(CollUtil.isNotEmpty(versionList));
        return response;
    }

    @Override
    public void deleteVersion(Long id) {
        AppVersion version = new AppVersion();
        version.setId(id);
        version.setDeleted(true);
        appVersionMapper.updateById(version);
    }
}
