package com.eghm.application.operate.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.vo.operate.auth.AuthConfigResponse;

/**
 * 第三方授权配置查询端口
 *
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigQueryGateway {

    Page<AuthConfigResponse> getByPage(PagingQuery request);
}
