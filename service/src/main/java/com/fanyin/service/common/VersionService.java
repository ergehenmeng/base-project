package com.fanyin.service.common;

import com.fanyin.dao.model.business.Version;
import com.fanyin.model.dto.business.version.VersionAddRequest;
import com.fanyin.model.dto.business.version.VersionEditRequest;
import com.fanyin.model.dto.business.version.VersionQueryRequest;
import com.fanyin.model.vo.version.VersionVO;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:38
 */
public interface VersionService {

    /**
     * 分页查询app管理列表
     * @param request 查询条件
     * @return 管理列表
     */
    PageInfo<Version> getByPage(VersionQueryRequest request);

    /**
     * 添加app版本管理信息
     * @param request 前台参数
     */
    void addAppVersion(VersionAddRequest request);

    /**
     * 编辑保存app版本管理信息
     * @param request 前台参数
     */
    void editAppVersion(VersionEditRequest request);

    /**
     * 上架版本
     * @param id 主键id
     */
    void putAwayVersion(Integer id);

    /**
     * 下架版本
     * @param id 主键id
     */
    void soleOutVersion(Integer id);

    /**
     * 获取最新可用的版本 用于检测是否有新版本
     * @return 版本信息
     */
    VersionVO getLatestVersion();

    /**
     * 获取最新app下载地址
     * @param channel app类型
     * @return 下载地址
     */
    String getLatestVersionUrl(String channel);

    /**
     * 删除版本信息
     * @param id 主键
     */
    void deleteVersion(Integer id);
}
