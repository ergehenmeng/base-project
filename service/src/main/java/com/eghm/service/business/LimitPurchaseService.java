package com.eghm.service.business;

import com.eghm.dto.business.purchase.LimitPurchaseAddRequest;
import com.eghm.dto.business.purchase.LimitPurchaseEditRequest;

/**
 * <p>
 * 限时购活动表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */
public interface LimitPurchaseService {

    /**
     * 新增限时购活动
     *
     * @param request 活动信息
     */
    void create(LimitPurchaseAddRequest request);

    /**
     * 修改限时购活动
     *
     * @param request 活动信息
     */
    void update(LimitPurchaseEditRequest request);

    /**
     * 删除限时购活动
     *
     * @param id id
     */
    void delete(Long id);
}
