package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.JsonService;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.purchase.LimitPurchaseQueryDTO;
import com.eghm.dto.business.purchase.LimitSkuRequest;
import com.eghm.dto.ext.DiscountJson;
import com.eghm.mapper.LimitPurchaseItemMapper;
import com.eghm.model.LimitPurchase;
import com.eghm.model.LimitPurchaseItem;
import com.eghm.service.business.LimitPurchaseItemService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.limit.LimitItemResponse;
import com.eghm.vo.business.limit.LimitItemVO;
import com.eghm.vo.business.limit.LimitSkuResponse;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private final JsonService jsonService;

    private final LimitPurchaseItemMapper limitPurchaseItemMapper;

    @Override
    public List<LimitItemVO> getByPage(LimitPurchaseQueryDTO dto) {
        Page<LimitItemVO> byPage = limitPurchaseItemMapper.getByPage(dto.createPage(false), dto);
        return byPage.getRecords();
    }

    @Override
    public void insertOrUpdate(List<LimitSkuRequest> skuList, LimitPurchase limitPurchase) {
        LambdaUpdateWrapper<LimitPurchaseItem> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LimitPurchaseItem::getLimitPurchaseId, limitPurchase.getId());
        limitPurchaseItemMapper.delete(wrapper);
        HashMap<Long, List<LimitSkuRequest>> hashMap = skuList.stream().collect(Collectors.groupingBy(LimitSkuRequest::getItemId, LinkedHashMap::new, Collectors.toList()));
        LocalDateTime advanceTime = limitPurchase.getStartTime().minusHours(limitPurchase.getAdvanceHour());
        LimitPurchaseItem item;
        for (Map.Entry<Long, List<LimitSkuRequest>> entry : hashMap.entrySet()) {
            item = new LimitPurchaseItem();
            item.setItemId(entry.getKey());
            item.setLimitPurchaseId(limitPurchase.getId());
            item.setMerchantId(limitPurchase.getMerchantId());
            item.setStartTime(limitPurchase.getStartTime());
            item.setEndTime(limitPurchase.getEndTime());
            item.setAdvanceTime(advanceTime);
            item.setMaxDiscountAmount(this.getMaxDiscountPrice(entry.getValue()));
            item.setMinPrice(this.getMinPrice(entry.getValue()));
            item.setSkuValue(jsonService.toJson(entry.getValue()));
            item.setCreateTime(limitPurchase.getCreateTime());
            item.setState(1);
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
    public void updateState(Long id, Integer state) {
        LambdaUpdateWrapper<LimitPurchaseItem> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LimitPurchaseItem::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, LimitPurchaseItem::getMerchantId, merchantId);
        wrapper.set(LimitPurchaseItem::getState, state);
        limitPurchaseItemMapper.update(null, wrapper);
    }

    @Override
    public List<LimitSkuResponse> getLimitList(Long limitId) {
        List<LimitItemResponse> responseList = limitPurchaseItemMapper.getLimitList(limitId);
        List<LimitSkuResponse> skuList = Lists.newArrayListWithExpectedSize(32);
        responseList.forEach(item -> {
            List<DiscountJson> list = jsonService.fromJsonList(item.getSkuValue(), DiscountJson.class);
            for (DiscountJson sku : list) {
                // 因为jsonService使用的是jackson, json格式价格字段是int
                LimitSkuResponse copy = DataUtil.copy(sku, LimitSkuResponse.class);
                copy.setTitle(item.getTitle());
                copy.setItemId(item.getItemId());
                copy.setSkuSize(list.size());
                skuList.add(copy);
            }
        });
        return skuList;
    }

    @Override
    public LimitPurchaseItem getByItemId(Long itemId, Long merchantId) {
        return limitPurchaseItemMapper.getByItemId(itemId, merchantId);
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

    /**
     * 获取最低价格
     *
     * @param skuList sku列表
     * @return 最低价格
     */
    private Integer getMinPrice(List<LimitSkuRequest> skuList) {
        return skuList.stream().mapToInt(LimitSkuRequest::getDiscountPrice).min().orElse(0);
    }
}
