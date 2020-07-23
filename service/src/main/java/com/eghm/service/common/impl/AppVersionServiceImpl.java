package com.eghm.service.common.impl;

import com.eghm.common.enums.Channel;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.VersionUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.business.AppVersionMapper;
import com.eghm.dao.model.business.AppVersion;
import com.eghm.model.dto.business.version.VersionAddRequest;
import com.eghm.model.dto.business.version.VersionEditRequest;
import com.eghm.model.dto.business.version.VersionQueryRequest;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.vo.version.AppVersionVO;
import com.eghm.service.common.AppVersionService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
@Service("versionService")
@Transactional(rollbackFor = RuntimeException.class)
public class AppVersionServiceImpl implements AppVersionService {

    private AppVersionMapper appVersionMapper;

    private SystemConfigApi systemConfigApi;

    @Autowired
    public void setAppVersionMapper(AppVersionMapper appVersionMapper) {
        this.appVersionMapper = appVersionMapper;
    }

    @Autowired
    public void setSystemConfigApi(SystemConfigApi systemConfigApi) {
        this.systemConfigApi = systemConfigApi;
    }

    @Override
    public PageInfo<AppVersion> getByPage(VersionQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<AppVersion> list = appVersionMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addAppVersion(VersionAddRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.setVersionNo(VersionUtil.parseInt(request.getVersion()));
        appVersionMapper.insertSelective(version);
    }

    @Override
    public void editAppVersion(VersionEditRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        version.setVersionNo(VersionUtil.parseInt(request.getVersion()));
        appVersionMapper.updateByPrimaryKeySelective(version);
    }

    @Override
    public void putAwayVersion(Integer id) {
        AppVersion appVersion = appVersionMapper.selectByPrimaryKey(id);
        AppVersion version = appVersionMapper.getVersion(appVersion.getClassify(), appVersion.getVersion());
        if (version != null) {
            throw new BusinessException(ErrorCode.VERSION_REDO);
        }
        appVersion.setState((byte) 1);
        appVersionMapper.updateByPrimaryKeySelective(appVersion);
    }

    @Override
    public void soldOutVersion(Integer id) {
        AppVersion version = new AppVersion();
        version.setId(id);
        version.setState((byte) 0);
        appVersionMapper.updateByPrimaryKeySelective(version);
    }

    @Override
    public AppVersionVO getLatestVersion() {
        String channel = RequestThreadLocal.getChannel();
        AppVersion latestVersion = appVersionMapper.getLatestVersion(channel);
        String version = RequestThreadLocal.getVersion();
        //未找到最新版本,或者用户版本大于等于已上架版本
        if (latestVersion == null || VersionUtil.gte(version, latestVersion.getVersion())) {
            return AppVersionVO.builder().latest(true).build();
        }
        AppVersionVO response = DataUtil.copy(latestVersion, AppVersionVO.class);
        //最新版本是强制更新版本
        if (Boolean.TRUE.equals(latestVersion.getForceUpdate())) {
            return response;
        }
        // 用户自己当前的软件版本信息
        AppVersion appVersion = appVersionMapper.getVersion(channel, version);
        //未找到用户安装的版本信息,默认不强更
        if (appVersion == null) {
            return response;
        }
        //如果用户版本非常老,最新版本不是强制更新版本,但中间某个版本是强制更新,用户一样需要强制更新
        Integer startVersion = appVersion.getVersionNo();
        //查询用户版本与最新版本之间的版本
        List<AppVersion> versionList = appVersionMapper.getForceUpdateVersion(channel, startVersion, latestVersion.getVersionNo());
        boolean forceUpdate = !CollectionUtils.isEmpty(versionList);
        response.setForceUpdate(forceUpdate);
        return response;
    }

    @Override
    public String getLatestVersionUrl(String channel) {
        if (Channel.IOS.name().equals(channel)) {
            return systemConfigApi.getString(ConfigConstant.APP_STORE_URL);
        }
        AppVersion latestVersion = appVersionMapper.getLatestVersion(channel);
        return latestVersion.getUrl();
    }

    @Override
    public void deleteVersion(Integer id) {
        AppVersion version = new AppVersion();
        version.setId(id);
        version.setDeleted(true);
        appVersionMapper.updateByPrimaryKeySelective(version);
    }
}
