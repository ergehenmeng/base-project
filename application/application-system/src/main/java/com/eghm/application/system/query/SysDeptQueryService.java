package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.vo.sys.ext.SysDeptResponse;

import java.util.List;

/**
 * 部门查询服务
 *
 * @author 二哥很猛
 */
public interface SysDeptQueryService {

    /**
     * 获取部门列表
     *
     * @param query 查询条件
     * @return 列表
     */
    List<SysDeptResponse> getList(PagingQuery query);
}
