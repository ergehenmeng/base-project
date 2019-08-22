package com.fanyin.service.common;

import com.fanyin.dao.model.business.AppVersion;
import com.fanyin.model.dto.business.version.VersionAddRequest;
import com.fanyin.model.dto.business.version.VersionQueryRequest;
import com.fanyin.model.vo.version.Version;
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

    /**
     * 添加app版本管理信息
     * @param request 前台参数
     */
    void addAppVersion(VersionAddRequest request);

    /**
     * 上架版本
     * @param id 主键id
     */
    void putAwayVersion(Integer id);

    /**
     * 获取最新可用的版本
     * @return 版本信息
     */
    Version getLatestVersion();
}
