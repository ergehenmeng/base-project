package com.eghm.service.business;

import com.eghm.dto.ext.StoreScope;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
public interface RedeemCodeScopeService {

    /**
     * 新增或更新兑换码使用范围
     * @param redeemCodeId 兑换码配置id
     * @param scopeList 使用范围
     */
    void insertOrUpdate(Long redeemCodeId, List<StoreScope> scopeList);

    /**
     * 获取兑换码使用范围
     *
     * @param redeemCodeId 配置id
     * @return 使用范围
     */
    List<StoreScope> getScopeList(Long redeemCodeId);

    /**
     * 删除兑换码使用范围
     *
     * @param redeemCodeId 配置id
     */
    void delete(Long redeemCodeId);
}
