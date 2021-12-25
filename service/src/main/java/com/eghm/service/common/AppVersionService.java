package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.AppVersion;
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
    void putAwayVersion(Long id);

    /**
     * 下架版本
     * @param id 主键id
     */
    void soldOutVersion(Long id);

    /**
     * 获取最新可用的版本 用于检测是否有新版本
     * 如果下载地址永久不变,则该方法返回的url可以作废,
     * 同时在固定下载地址时,保证每次更新的版本apk需要上传到该路径下
     * @return 版本信息
     */
    AppVersionVO getLatestVersion();


    /**
     * 删除版本信息
     * @param id 主键
     */
    void deleteVersion(Long id);
}
