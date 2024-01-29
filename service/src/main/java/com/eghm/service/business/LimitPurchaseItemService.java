package com.eghm.service.business;

import com.eghm.dto.business.purchase.LimitItemRequest;
import com.eghm.model.LimitPurchase;
import com.eghm.vo.business.limit.LimitItemResponse;

import java.util.List;

/**
 * <p>
 * 限时购商品表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */
public interface LimitPurchaseItemService {

    /**
     * 新增或修改限时购商品信息
     *
     * @param itemList 商品列表
     * @param limitPurchase 限时购活动信息
     */
    void insertOrUpdate(List<LimitItemRequest> itemList, LimitPurchase limitPurchase);

    /**
     * 删除限时购商品信息
     *
     * @param limitPurchaseId 限时购活动id
     */
    void delete(Long limitPurchaseId);

    /**
     * 查询限时购活动配置的商品列表
     *
     * @param limitId 活动id
     * @return 商品列表
     */
    List<LimitItemResponse> getLimitList(Long limitId);
}
