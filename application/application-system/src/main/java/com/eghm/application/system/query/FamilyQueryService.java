package com.eghm.application.system.query;

import com.eghm.application.shared.vo.sys.family.FamilyResponse;

import java.util.List;

/**
 * 家谱查询网关
 *
 * @author 二哥很猛
 */
public interface FamilyQueryService {

    /**
     * 查询家族所有成员
     *
     * @return 成员列表
     */
    List<FamilyResponse> getList();
}
