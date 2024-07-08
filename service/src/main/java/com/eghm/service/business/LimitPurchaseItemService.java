package com.eghm.service.business;

import com.eghm.dto.business.purchase.LimitPurchaseQueryDTO;
import com.eghm.dto.business.purchase.LimitItemRequest;
import com.eghm.dto.business.purchase.LimitSkuRequest;
import com.eghm.model.LimitPurchase;
import com.eghm.model.LimitPurchaseItem;
import com.eghm.vo.business.limit.LimitItemResponse;
import com.eghm.vo.business.limit.LimitItemVO;
import com.eghm.vo.business.limit.LimitSkuResponse;

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
     * 查询限时购活动配置的商品列表
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<LimitItemVO> getByPage(LimitPurchaseQueryDTO dto);

    /**
     * 新增或修改限时购商品信息
     *
     * @param skuList 商品规格列表
     * @param limitPurchase 限时购活动信息
     */
    void insertOrUpdate(List<LimitSkuRequest> skuList, LimitPurchase limitPurchase);

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
    List<LimitSkuResponse> getLimitList(Long limitId);

    /**
     * 查询限时购活动配置的商品信息
     *
     * @param limitId 限时购活动id
     * @param itemId 商品ID
     * @return 商品信息
     */
    LimitPurchaseItem getLimitItem(Long limitId, Long itemId);
}
