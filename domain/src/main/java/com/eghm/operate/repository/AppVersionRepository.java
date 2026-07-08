package com.eghm.operate.repository;

import com.eghm.operate.model.AppVersion;

import java.util.List;

/**
 * 手机版本仓储接口
 *
 * @author 二哥很猛
 */
public interface AppVersionRepository {

    /**
     * 保存版本信息
     *
     * @param appVersion 版本信息
     */
    void save(AppVersion appVersion);

    /**
     * 更新版本信息
     *
     * @param appVersion 版本信息
     */
    void update(AppVersion appVersion);

    /**
     * 更新状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, Boolean state);

    /**
     * 删除版本信息
     *
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 判断版本号是否重复
     *
     * @param version 版本号
     * @return true:重复
     */
    boolean existsByVersion(String version);

    /**
     * 获取已上架版本的信息说明
     *
     * @param channel app类型
     * @return 版本信息
     */
    AppVersion findLatestVersion(String channel);

    /**
     * 在指定时间段内获取强制更新的版本列表
     *
     * @param channel      app类型
     * @param startVersion 开始版本
     * @param endVersion   结束版本
     * @return 强制更新的版本列表
     */
    List<AppVersion> findForceUpdateVersions(String channel, Integer startVersion, Integer endVersion);
}
