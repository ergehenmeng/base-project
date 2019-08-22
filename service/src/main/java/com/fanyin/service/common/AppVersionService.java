package com.fanyin.service.common;

import com.fanyin.dao.model.business.AppVersion;
import com.fanyin.model.dto.business.version.VersionQueryRequest;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
public interface AppVersionService {

    /**
     * 分页查询app管理列表
     * @param request 查询条件
     * @return 管理列表
     */
    PageInfo<AppVersion> getByPage(VersionQueryRequest request);
}
