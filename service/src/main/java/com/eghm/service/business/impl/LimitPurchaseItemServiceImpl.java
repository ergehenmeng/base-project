package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.purchase.LimitItemRequest;
import com.eghm.dto.business.purchase.LimitPurchaseQueryDTO;
import com.eghm.dto.business.purchase.LimitSkuRequest;
import com.eghm.dto.ext.DiscountItemSku;
import com.eghm.mapper.LimitPurchaseItemMapper;
import com.eghm.model.LimitPurchase;
import com.eghm.model.LimitPurchaseItem;
import com.eghm.service.business.LimitPurchaseItemService;
import com.eghm.common.JsonService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.limit.LimitItemResponse;
import com.eghm.vo.business.limit.LimitItemVO;
import com.eghm.vo.business.limit.LimitSkuResponse;
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
    public List<LimitItemVO> getByPage(LimitPurchaseQueryDTO dto) {
        Page<LimitItemVO> byPage = limitPurchaseItemMapper.getByPage(dto.createPage(false), dto);
        return byPage.getRecords();
    }

    @Override
    public void insertOrUpdate(List<LimitItemRequest> itemList, LimitPurchase limitPurchase) {
        LambdaUpdateWrapper<LimitPurchaseItem> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LimitPurchaseItem::getLimitPurchaseId, limitPurchase.getId());
        limitPurchaseItemMapper.delete(wrapper);
        LocalDateTime advanceTime = limitPurchase.getStartTime().minusHours(limitPurchase.getAdvanceHour());
        LimitPurchaseItem item;
        for (LimitItemRequest itemRequest : itemList) {
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

    @Override
    public List<LimitItemResponse> getLimitList(Long limitId) {
        List<LimitItemResponse> responseList = limitPurchaseItemMapper.getLimitList(limitId);
        responseList.forEach(item -> {
            List<DiscountItemSku> skuList = jsonService.fromJsonList(item.getSkuValue(), DiscountItemSku.class);
            item.setSkuList(DataUtil.copy(skuList, LimitSkuResponse.class));
        });
        return responseList;
    }

    @Override
    public LimitPurchaseItem getLimitItem(Long limitId, Long itemId) {
        LambdaQueryWrapper<LimitPurchaseItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LimitPurchaseItem::getLimitPurchaseId, limitId);
        wrapper.eq(LimitPurchaseItem::getItemId, itemId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return limitPurchaseItemMapper.selectOne(wrapper);
    }

    /**
     * 获取最大优惠价格
     *
     * @param skuList sku列表
     * @return 最大优惠金额
     */
    private Integer getMaxDiscountPrice(List<LimitSkuRequest> skuList) {
        return skuList.stream().mapToInt(request -> request.getSalePrice() - request.getDiscountPrice()).max().orElse(0);
    }

}
