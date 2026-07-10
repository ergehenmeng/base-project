package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.version.VersionAddRequest;
import com.eghm.application.shared.dto.operate.version.VersionEditRequest;

/**
 * @author 二哥很猛
 * @since 2019/8/22 14:38
 */
public interface AppVersionApplicationService {

    /**
     * 添加app版本管理信息
     *
     * @param request 前台参数
     */
    void create(VersionAddRequest request);

    /**
     * 编辑保存app版本管理信息
     *
     * @param request 前台参数
     */
    void update(VersionEditRequest request);

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
    void delete(Long id);
}
