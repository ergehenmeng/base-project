package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.vo.sys.roster.BlackRosterResponse;

/**
 * 黑名单查询服务
 *
 * @author 二哥很猛
 */
public interface BlackRosterQueryService {

    /**
     * 分页查询黑名单列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BlackRosterResponse> getByPage(PagingQuery request);
}
