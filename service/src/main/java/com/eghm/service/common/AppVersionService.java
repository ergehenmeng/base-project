package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.AppVersion;
import com.eghm.model.dto.version.VersionAddRequest;
import com.eghm.model.dto.version.VersionEditRequest;
import com.eghm.model.dto.version.VersionQueryRequest;
import com.eghm.model.vo.version.AppVersionVO;

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
    Page<AppVersion> getByPage(VersionQueryRequest request);

    /**
     * 添加app版本管理信息
     * @param request 前台参数
     */
    void create(VersionAddRequest request);

    /**
     * 编辑保存app版本管理信息
     * @param request 前台参数
     */
    void update(VersionEditRequest request);

    /**
     * 1.获取系统配置的最新版本
     * 2.如果用户版
     *
     * 如果下载地址永久不变,则该方法返回的url可以作废,
     * 同时在固定下载地址时,保证每次更新的版本apk需要上传到该路径下
     * @return 版本信息
     */
    AppVersionVO getLatestVersion();

    /**
     * 删除版本信息
     * @param id 主键
     */
    void delete(Long id);
}
