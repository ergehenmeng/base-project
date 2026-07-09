package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.version.VersionQueryRequest;
import com.eghm.application.shared.vo.operate.version.AppVersionResponse;

/**
 * 手机版本查询端口
 *
 * @author 二哥很猛
 */
public interface AppVersionQueryService {

    /**
     * 分页查询
     *
     * @param page    分页
     * @param request 查询条件
     * @return 分页结果
     */
    Page<AppVersionResponse> getByPage(Page<AppVersionResponse> page, VersionQueryRequest request);
}
