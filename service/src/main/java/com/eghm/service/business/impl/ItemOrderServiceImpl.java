package com.eghm.service.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.JsonService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.order.item.ItemOrderQueryDTO;
import com.eghm.dto.business.order.item.ItemOrderQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.DeliveryState;
import com.eghm.enums.ref.ItemRefundState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemOrderMapper;
import com.eghm.mapper.OrderAdjustLogMapper;
import com.eghm.mapper.OrderRefundLogMapper;
import com.eghm.model.ItemOrder;
import com.eghm.model.ItemSku;
import com.eghm.model.ItemSpec;
import com.eghm.service.business.ExpressService;
import com.eghm.service.business.ItemExpressService;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.AssertUtil;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.adjust.OrderAdjustResponse;
import com.eghm.vo.business.order.item.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Service("itemOrderService")
@Slf4j
@AllArgsConstructor
public class ItemOrderServiceImpl implements ItemOrderService {

    private final ItemOrderMapper itemOrderMapper;

    private final SysAreaService sysAreaService;

    private final ItemExpressService itemExpressService;

    private final JsonService jsonService;

    private final OrderRefundLogMapper orderRefundLogMapper;

    private final ExpressService expressService;

    private final SysConfigApi sysConfigApi;

    private final OrderAdjustLogMapper orderAdjustLogMapper;

    @Override
    public Page<ItemOrderResponse> listPage(ItemOrderQueryRequest request) {
        return itemOrderMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<ItemOrderResponse> getList(ItemOrderQueryRequest request) {
        Page<ItemOrderResponse> listPage = itemOrderMapper.listPage(request.createNullPage(), request);
        return listPage.getRecords();
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
    public Long countWaitDelivery(String orderNo) {
        LambdaQueryWrapper<ItemOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemOrder::getOrderNo, orderNo);
        wrapper.eq(ItemOrder::getDeliveryState, DeliveryState.WAIT_DELIVERY);
        wrapper.ne(ItemOrder::getRefundState, ItemRefundState.REFUND);
        return itemOrderMapper.selectCount(wrapper);
    }

    @Override
    public void insert(String orderNo, Long memberId, List<OrderPackage> packageList, Map<Long, Integer> skuExpressMap) {
        for (OrderPackage aPackage : packageList) {
            ItemOrder order = DataUtil.copy(aPackage.getItem(), ItemOrder.class, "id");
            BeanUtil.copyProperties(aPackage.getSku(), order, "id");
            order.setSkuTitle(this.getSkuTitle(aPackage.getSku()));
            order.setCoverUrl(aPackage.getItem().getCoverUrl());
            order.setOrderNo(orderNo);
            order.setMemberId(memberId);
            order.setSkuId(aPackage.getSkuId());
            // 不以sku价格为准,以真实销售单价为准
            order.setSalePrice(aPackage.getFinalPrice());
            String skuPic = aPackage.getSku().getSkuPic();
            // 规格图片优先sku,其次spu,最后item
            if (skuPic != null) {
                order.setSkuCoverUrl(skuPic);
            } else {
                ItemSpec spec = aPackage.getSpec();
                if (spec != null && StrUtil.isNotBlank(spec.getSpecPic())) {
                    order.setSkuCoverUrl(spec.getSpecPic());
                } else {
                    order.setSkuCoverUrl(aPackage.getItem().getCoverUrl());
                }
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
        // 如果选择售后订单, 需要判断是否支持售后退款
        if (CollUtil.isNotEmpty(voPage.getRecords()) && dto.getTabState() != null && dto.getTabState() == 4) {
            int afterSaleTime = sysConfigApi.getInt(ConfigConstant.SUPPORT_AFTER_SALE_TIME, 604800);
            LocalDateTime now = LocalDateTime.now();
            voPage.getRecords().forEach(item -> item.setSupportRefund(item.getCompleteTime() != null && item.getCompleteTime().plusSeconds(afterSaleTime).isBefore(now)));
        }
        return voPage.getRecords();
    }

    @Override
    public List<ItemOrderListVO> getItemList(String orderNo) {
        return itemOrderMapper.getItemList(orderNo);
    }

    @Override
    public ItemOrderDetailVO getDetail(String orderNo, Long memberId) {
        ItemOrderDetailVO detail = itemOrderMapper.getDetail(orderNo, memberId);
        AssertUtil.assertOrderNotNull(detail, orderNo, memberId);
        List<ItemOrderListVO> itemList = itemOrderMapper.getItemList(orderNo);
        detail.setDetailAddress(sysAreaService.parseArea(detail.getProvinceId(), detail.getCityId(), detail.getCountyId()) + detail.getDetailAddress());
        detail.setItemList(itemList);
        List<FirstExpressVO> expressList = itemExpressService.getFirstExpressList(orderNo);
        detail.setExpressList(expressList);
        return detail;
    }

    @Override
    public ItemOrderDetailResponse detail(String orderNo) {
        Long merchantId = SecurityHolder.getMerchantId();
        // 订单信息
        ItemOrderDetailResponse detail = itemOrderMapper.detail(orderNo, merchantId);
        AssertUtil.assertOrderNotNull(detail, orderNo, merchantId);
        // 商品信息
        List<ItemOrderListVO> itemList = itemOrderMapper.getItemList(orderNo);
        detail.setDetailAddress(sysAreaService.parseArea(detail.getProvinceId(), detail.getCityId(), detail.getCountyId()) + detail.getDetailAddress());
        detail.setItemList(itemList);
        // 发货信息
        List<ItemShippedResponse> shippedList = itemOrderMapper.getShippedList(orderNo);
        shippedList.forEach(response -> response.setExpressList(jsonService.fromJsonList(response.getContent(), ExpressVO.class)));
        detail.setShippedList(shippedList);
        List<OrderAdjustResponse> mapperList = orderAdjustLogMapper.getList(orderNo);
        detail.setAdjustList(mapperList);
        return detail;
    }

    @Override
    public ItemOrderRefundDetailResponse refundDetail(String orderNo) {
        // 订单+商品信息+发货信息
        ItemOrderDetailResponse detail = this.detail(orderNo);
        ItemOrderRefundDetailResponse response = DataUtil.copy(detail, ItemOrderRefundDetailResponse.class);
        // 退款申请记录
        List<ItemRefundResponse> refundLog = orderRefundLogMapper.getItemRefundLog(orderNo);
        // 退款物流信息
        refundLog.forEach(itemRefundResponse -> itemRefundResponse.setExpressList(expressService.getExpressList(itemRefundResponse.getExpressNo(), itemRefundResponse.getExpressCode())));
        response.setRefundList(refundLog);
        return response;
    }

    @Override
    public List<ItemOrder> getByIds(List<Long> ids) {
        LambdaQueryWrapper<ItemOrder> wrapper = Wrappers.lambdaQuery();

        wrapper.select(ItemOrder::getItemId, ItemOrder::getOrderNo, ItemOrder::getId,
                ItemOrder::getNum, ItemOrder::getRefundState,
                ItemOrder::getDeliveryType, ItemOrder::getDeliveryState);

        wrapper.in(ItemOrder::getId, ids);
        return itemOrderMapper.selectList(wrapper);
    }

    @Override
    public void updateBatchById(List<ItemOrder> list) {
        list.forEach(itemOrderMapper::updateById);
    }

    @Override
    public ItemOrderSnapshotVO getSnapshot(Long orderId, Long memberId) {
        ItemOrderSnapshotVO snapshot = itemOrderMapper.getSnapshot(orderId, memberId);
        AssertUtil.assertOrderNotNull(snapshot, orderId, memberId);
        return snapshot;
    }

    @Override
    public ItemOrderRefundVO getRefund(Long orderId, Long memberId) {
        return itemOrderMapper.getRefund(orderId, memberId);
    }

    /**
     * 拼接sku名称
     *
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
