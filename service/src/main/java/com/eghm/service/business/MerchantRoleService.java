package com.eghm.service.business;

import com.eghm.common.enums.ref.RoleType;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/24 18:24
 */
public interface MerchantRoleService {

    /**
     * 商户角色授权
     * @param merchantId 商户ID
     * @param roleList 商户角色code
     */
    void authRole(Long merchantId, List<RoleType> roleList);
}
