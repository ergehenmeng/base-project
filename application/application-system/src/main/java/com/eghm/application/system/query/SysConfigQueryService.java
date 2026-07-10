package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.config.ConfigQueryRequest;
import com.eghm.application.shared.vo.sys.ext.SysConfigResponse;

/**
 * 系统配置查询服务
 *
 * @author 二哥很猛
 */
public interface SysConfigQueryService {

    /**
     * 分页查询
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SysConfigResponse> getByPage(ConfigQueryRequest request);
}
