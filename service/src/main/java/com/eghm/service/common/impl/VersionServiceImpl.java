package com.eghm.service.common.impl;

import com.eghm.common.enums.Channel;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.VersionUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.business.VersionMapper;
import com.eghm.dao.model.business.Version;
import com.eghm.model.dto.business.version.VersionAddRequest;
import com.eghm.model.dto.business.version.VersionEditRequest;
import com.eghm.model.dto.business.version.VersionQueryRequest;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.vo.version.VersionVO;
import com.eghm.service.common.VersionService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
@Service("versionService")
@Transactional(rollbackFor = RuntimeException.class)
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionMapper versionMapper;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public PageInfo<Version> getByPage(VersionQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<Version> list = versionMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addAppVersion(VersionAddRequest request) {
        Version version = DataUtil.copy(request, Version.class);
        versionMapper.insertSelective(version);
    }

    @Override
    public void editAppVersion(VersionEditRequest request) {
        Version version = DataUtil.copy(request, Version.class);
        versionMapper.updateByPrimaryKeySelective(version);
    }

    @Override
    public void putAwayVersion(Integer id) {
        Version appVersion = versionMapper.selectByPrimaryKey(id);
        Version version = versionMapper.getVersion(appVersion.getClassify(), appVersion.getVersion());
        if (version != null) {
            throw new BusinessException(ErrorCode.VERSION_REDO);
        }
        appVersion.setState((byte) 1);
        versionMapper.updateByPrimaryKeySelective(appVersion);
    }

    @Override
    public void soldOutVersion(Integer id) {
        Version version = new Version();
        version.setId(id);
        version.setState((byte) 0);
        versionMapper.updateByPrimaryKeySelective(version);
    }

    @Override
    public VersionVO getLatestVersion() {
        String channel = RequestThreadLocal.getChannel();
        Version latestVersion = versionMapper.getLatestVersion(channel);
        String version = RequestThreadLocal.getVersion();
        //未找到最新版本,或者用户版本大于等于已上架版本
        if (latestVersion == null || VersionUtil.gte(version, latestVersion.getVersion())) {
            return VersionVO.builder().latest(true).build();
        }
        VersionVO response = DataUtil.copy(latestVersion, VersionVO.class);
        //最新版本是强制更新版本
        if (latestVersion.getForceUpdate()) {
            return response;
        }
        Version appVersion = versionMapper.getVersion(channel, version);
        //未找到用户安装的版本信息,默认不强更
        if (appVersion == null) {
            return response;
        }
        //如果用户版本非常老,最新版本不是强制更新版本,但中间某个版本是强制更新,用户一样需要强制更新
        Date addTime = appVersion.getUpdateTime();
        //查询用户版本与最新版本之间的版本
        List<Version> versionList = versionMapper.getForceUpdateVersion(channel, addTime, latestVersion.getUpdateTime());
        boolean forceUpdate = !CollectionUtils.isEmpty(versionList);
        response.setForceUpdate(forceUpdate);
        return response;
    }

    @Override
    public String getLatestVersionUrl(String channel) {
        if (Channel.IOS.name().equals(channel)) {
            return systemConfigApi.getString(ConfigConstant.APP_STORE_URL);
        }
        Version latestVersion = versionMapper.getLatestVersion(channel);
        return latestVersion.getUrl();
    }

    @Override
    public void deleteVersion(Integer id) {
        Version version = new Version();
        version.setId(id);
        version.setDeleted(true);
        versionMapper.updateByPrimaryKeySelective(version);
    }
}
