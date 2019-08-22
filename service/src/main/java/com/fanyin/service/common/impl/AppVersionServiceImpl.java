package com.fanyin.service.common.impl;

import com.fanyin.dao.mapper.business.AppVersionMapper;
import com.fanyin.dao.model.business.AppVersion;
import com.fanyin.model.dto.business.version.VersionQueryRequest;
import com.fanyin.service.common.AppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public PageInfo<AppVersion> getByPage(VersionQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<AppVersion> list = appVersionMapper.getList(request);
        return new PageInfo<>(list);
    }
}
