package com.eghm.service.sys;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.vo.sys.ext.SysDeptResponse;

import java.util.List;

/**
 * 部门查询网关
 *
 * @author 二哥很猛
 */
public interface SysDeptQueryGateway {

    /**
     * 获取部门列表
     *
     * @param query 查询条件
     * @return 列表
     */
    List<SysDeptResponse> getList(PagingQuery query);
}
