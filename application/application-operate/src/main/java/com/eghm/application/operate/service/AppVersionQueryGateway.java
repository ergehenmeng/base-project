package com.eghm.application.operate.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.operate.version.VersionQueryRequest;
import com.eghm.vo.operate.version.AppVersionResponse;

/**
 * 手机版本查询端口
 *
 * @author 二哥很猛
 */
public interface AppVersionQueryGateway {

    /**
     * 分页查询
     *
     * @param page    分页
     * @param request 查询条件
     * @return 分页结果
     */
    Page<AppVersionResponse> getByPage(Page<AppVersionResponse> page, VersionQueryRequest request);
}
