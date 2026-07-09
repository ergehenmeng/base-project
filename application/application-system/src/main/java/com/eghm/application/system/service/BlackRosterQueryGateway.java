package com.eghm.application.system.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.domain.system.model.BlackRoster;

/**
 * 黑名单查询网关
 *
 * @author 二哥很猛
 */
public interface BlackRosterQueryGateway {

    /**
     * 分页查询黑名单列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BlackRoster> getByPage(PagingQuery request);
}
