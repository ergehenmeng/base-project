package com.eghm.application.system.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.config.ConfigQueryRequest;
import com.eghm.application.shared.vo.sys.ext.SysConfigResponse;

/**
 * 系统配置查询网关
 *
 * @author 二哥很猛
 */
public interface SysConfigQueryGateway {

    /**
     * 分页查询
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SysConfigResponse> getByPage(ConfigQueryRequest request);
}
