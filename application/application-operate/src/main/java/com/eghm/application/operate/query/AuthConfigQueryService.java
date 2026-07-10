package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.vo.operate.auth.AuthConfigResponse;

/**
 * 第三方授权配置查询服务
 *
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigQueryService {

    Page<AuthConfigResponse> getByPage(PagingQuery request);
}
