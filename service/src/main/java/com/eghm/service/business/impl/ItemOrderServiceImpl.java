package com.eghm.service.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.item.ItemOrderQueryDTO;
import com.eghm.dto.business.order.item.ItemOrderQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemOrderMapper;
import com.eghm.model.ItemOrder;
import com.eghm.model.ItemSku;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.item.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Service("itemOrderService")
@Slf4j
@AllArgsConstructor
public class ItemOrderServiceImpl implements ItemOrderService {

    private final ItemOrderMapper itemOrderMapper;

    @Override
    public Page<ItemOrderResponse> listPage(ItemOrderQueryRequest request) {
        return itemOrderMapper.listPage(request.createPage(), request);
    }

    @Override
    public void insert(ItemOrder order) {
        itemOrderMapper.insert(order);
    }

    @Override
    public List<ItemOrder> getByOrderNo(String orderNo) {
        LambdaQueryWrapper<ItemOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemOrder::getOrderNo, orderNo);
        return itemOrderMapper.selectList(wrapper);
    }

    @Override
    public void insert(String orderNo, List<OrderPackage> packageList, Map<Long, Integer> skuExpressMap) {
        for (OrderPackage aPackage : packageList) {
            ItemOrder order = DataUtil.copy(aPackage.getItem(), ItemOrder.class, "id");
            BeanUtil.copyProperties(aPackage.getSku(), order, "id");
            order.setSkuTitle(this.getSkuTitle(aPackage.getSku()));
            order.setCoverUrl(aPackage.getItem().getCoverUrl());
            order.setOrderNo(orderNo);
            order.setSkuId(aPackage.getSkuId());
            String skuPic = aPackage.getSku().getSkuPic();
            if (skuPic != null) {
                order.setCoverUrl(skuPic);
            } else {
                order.setCoverUrl(aPackage.getItem().getCoverUrl());
            }
            order.setNum(aPackage.getNum());
            order.setDeliveryType(aPackage.getItem().getDeliveryType());
            order.setExpressFee(skuExpressMap.get(order.getSkuId()));
            itemOrderMapper.insert(order);
        }
    }

    @Override
    public ItemOrder selectById(Long id) {
        return itemOrderMapper.selectById(id);
    }

    @Override
    public ItemOrder selectByIdRequired(Long id) {
        ItemOrder order = itemOrderMapper.selectById(id);
        if (order == null) {
            log.error("商品订单信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.ITEM_ORDER_NULL);
        }
        return order;
    }

    @Override
    public void updateById(ItemOrder itemOrder) {
        itemOrderMapper.updateById(itemOrder);
    }

    @Override
    public int getProductNum(String orderNo) {
        return itemOrderMapper.getProductNum(orderNo);
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        ItemOrder selected = this.selectByIdRequired(orderId);
        if (!selected.getOrderNo().equals(orderNo)) {
            log.error("订单号和与订单id不匹配 [{}] [{}]", orderId, orderNo);
            throw new BusinessException(ErrorCode.ORDER_NOT_MATCH);
        }
        ProductSnapshotVO vo = new ProductSnapshotVO();
        vo.setSkuTitle(selected.getSkuTitle());
        vo.setStoreId(selected.getStoreId());
        vo.setProductId(selected.getItemId());
        vo.setProductTitle(selected.getTitle());
        vo.setProductCover(selected.getCoverUrl());
        return vo;
    }

    @Override
    public List<ItemOrderVO> getByPage(ItemOrderQueryDTO dto) {
        Page<ItemOrderVO> voPage = itemOrderMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public ItemOrderDetailVO getDetail(String orderNo, Long memberId) {
        ItemOrderDetailVO detail = itemOrderMapper.getDetail(orderNo, memberId);
        if (detail == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        List<ItemOrderListVO> itemList = itemOrderMapper.getItemList(orderNo);
        detail.setItemList(itemList);
        return detail;
    }

    @Override
    public ItemOrderDetailResponse detail(String orderNo) {
        ItemOrderDetailResponse detail = itemOrderMapper.detail(orderNo, SecurityHolder.getMerchantId());
        if (detail == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        List<ItemOrderListVO> itemList = itemOrderMapper.getItemList(orderNo);
        detail.setItemList(itemList);
        return detail;
    }

    /**
     * 拼接sku名称
     * @param sku sku信息
     * @return sku名称
     */
    private String getSkuTitle(ItemSku sku) {
        if (StrUtil.isBlank(sku.getSecondSpecValue())) {
            return sku.getPrimarySpecValue();
        }
        return sku.getPrimarySpecValue() + "/" + sku.getSecondSpecValue();
    }
}
