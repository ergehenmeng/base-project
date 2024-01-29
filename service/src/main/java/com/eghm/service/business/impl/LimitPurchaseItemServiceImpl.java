package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.purchase.PurchaseItemRequest;
import com.eghm.dto.business.purchase.PurchaseSkuRequest;
import com.eghm.mapper.LimitPurchaseItemMapper;
import com.eghm.model.LimitPurchase;
import com.eghm.model.LimitPurchaseItem;
import com.eghm.service.business.LimitPurchaseItemService;
import com.eghm.service.common.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 限时购商品表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */

@Slf4j
@AllArgsConstructor
@Service("limitPurchaseItemService")
public class LimitPurchaseItemServiceImpl implements LimitPurchaseItemService {

    private final LimitPurchaseItemMapper limitPurchaseItemMapper;

    private final JsonService jsonService;
    @Override
    public void insertOrUpdate(List<PurchaseItemRequest> itemList, LimitPurchase limitPurchase) {
        LambdaUpdateWrapper<LimitPurchaseItem> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LimitPurchaseItem::getLimitPurchaseId, limitPurchase.getId());
        limitPurchaseItemMapper.delete(wrapper);
        LocalDateTime advanceTime = limitPurchase.getStartTime().minusHours(limitPurchase.getAdvanceHour());
        LimitPurchaseItem item;
        for (PurchaseItemRequest itemRequest : itemList) {
            item = new LimitPurchaseItem();
            item.setItemId(itemRequest.getItemId());
            item.setLimitPurchaseId(limitPurchase.getId());
            item.setMerchantId(limitPurchase.getMerchantId());
            item.setStartTime(limitPurchase.getStartTime());
            item.setEndTime(limitPurchase.getEndTime());
            item.setAdvanceTime(advanceTime);
            item.setMaxDiscountAmount(this.getMaxDiscountPrice(itemRequest.getSkuList()));
            item.setSkuValue(jsonService.toJson(itemRequest.getSkuList()));
            item.setCreateTime(limitPurchase.getCreateTime());
            limitPurchaseItemMapper.insert(item);
        }
    }

    @Override
    public void delete(Long limitPurchaseId) {
        LambdaUpdateWrapper<LimitPurchaseItem> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LimitPurchaseItem::getLimitPurchaseId, limitPurchaseId);
        wrapper.eq(LimitPurchaseItem::getMerchantId, SecurityHolder.getMerchantId());
        limitPurchaseItemMapper.delete(wrapper);
    }

    /**
     * 获取最大优惠价格
     *
     * @param skuList sku列表
     * @return 最大优惠金额
     */
    private Integer getMaxDiscountPrice(List<PurchaseSkuRequest> skuList) {
        return skuList.stream().mapToInt(request -> request.getSalePrice() - request.getLimitPrice()).max().orElse(0);
    }

}
