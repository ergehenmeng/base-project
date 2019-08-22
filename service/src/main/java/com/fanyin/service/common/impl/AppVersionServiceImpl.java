package com.fanyin.service.common.impl;

import com.fanyin.common.enums.Channel;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.VersionUtil;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.dao.mapper.business.AppVersionMapper;
import com.fanyin.dao.model.business.AppVersion;
import com.fanyin.model.dto.business.version.VersionAddRequest;
import com.fanyin.model.dto.business.version.VersionQueryRequest;
import com.fanyin.model.ext.RequestThreadLocal;
import com.fanyin.model.vo.version.Version;
import com.fanyin.service.common.AppVersionService;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public PageInfo<AppVersion> getByPage(VersionQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<AppVersion> list = appVersionMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addAppVersion(VersionAddRequest request) {
        AppVersion version = DataUtil.copy(request, AppVersion.class);
        appVersionMapper.insertSelective(version);
    }

    @Override
    public void putAwayVersion(Integer id) {
        AppVersion appVersion = appVersionMapper.selectByPrimaryKey(id);
        AppVersion version = appVersionMapper.getVersion(appVersion.getClassify(), appVersion.getVersion());
        if(version != null){
            throw new BusinessException(ErrorCodeEnum.VERSION_REDO);
        }
        appVersion.setState((byte)1);
        appVersionMapper.updateByPrimaryKeySelective(appVersion);
    }

    @Override
    public void soleOutVersion(Integer id) {
        AppVersion appVersion = new AppVersion();
        appVersion.setId(id);
        appVersion.setState((byte)0);
        appVersionMapper.updateByPrimaryKeySelective(appVersion);
    }

    @Override
    public Version getLatestVersion() {
        String channel = RequestThreadLocal.getChannel();
        AppVersion latestVersion = appVersionMapper.getLatestVersion(channel);
        String version = RequestThreadLocal.getVersion();
        //未找到最新版本,或者用户版本大于等于已上架版本
        if(latestVersion == null || VersionUtil.gte(version,latestVersion.getVersion())){
            return Version.builder().latest(true).build();
        }
        Version response = DataUtil.copy(latestVersion, Version.class);
        //最新版本是强制更新版本
        if(latestVersion.getForceUpdate()){
            return response;
        }
        AppVersion appVersion = appVersionMapper.getVersion(channel, version);
        //未找到用户安装的版本信息,默认不强更
        if(appVersion == null){
            return response;
        }
        //如果用户版本非常老,最新版本不是强制更新版本,但中间某个版本是强制更新,用户一样需要强制更新
        Date addTime = appVersion.getAddTime();
        //查询开始时间和结束时间为了尽可能查询更广的范围
        List<AppVersion> versionList = appVersionMapper.getForceUpdateVersion(channel, addTime, latestVersion.getUpdateTime());
        boolean forceUpdate = !CollectionUtils.isEmpty(versionList);
        response.setForceUpdate(forceUpdate);
        return response;
    }

    @Override
    public String getLatestVersionUrl(String channel) {
        if(Channel.IOS.name().equals(channel)){
            return systemConfigApi.getString(ConfigConstant.APP_STORE_URL);
        }
        AppVersion latestVersion = appVersionMapper.getLatestVersion(channel);
        return latestVersion.getUrl();
    }
}
