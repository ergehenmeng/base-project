package com.eghm.application.system.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.log.ManageQueryRequest;
import com.eghm.vo.operate.log.ManageLogResponse;

/**
 * 管理端操作日志查询端口
 *
 * @author 二哥很猛
 * @since 2019/1/15 17:55
 */
public interface ManageLogQueryGateway {

    Page<ManageLogResponse> getByPage(Page<ManageLogResponse> page, ManageQueryRequest request);
}
