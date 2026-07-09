package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.ManageQueryRequest;
import com.eghm.application.shared.vo.operate.log.ManageLogResponse;

/**
 * 管理端操作日志查询端口
 *
 * @author 二哥很猛
 * @since 2019/1/15 17:55
 */
public interface ManageLogQueryService {

    Page<ManageLogResponse> getByPage(Page<ManageLogResponse> page, ManageQueryRequest request);
}
