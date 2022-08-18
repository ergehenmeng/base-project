package com.eghm.service.business;

import com.eghm.common.enums.ref.ProductType;
import com.eghm.model.dto.business.order.VisitorVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
public interface OrderVisitorService {

    /**
     * 添加订单关联的游客信息
     * @param productType 订单类型
     * @param orderNo 订单编号
     * @param voList 游客信息
     */
    void addVisitor(ProductType productType, String orderNo, List<VisitorVO> voList);

    /**
     * 锁定游客信息,防止在退款中进行核销
     * @param productType 商品类型
     * @param orderNo 订单编号
     * @param refundId 退款id
     * @param visitorList 游客id列表
     */
    void lockVisitor(ProductType productType, String orderNo, Long refundId, List<Long> visitorList);

    /**
     * 解锁游客信息,退款拒绝使其可以继续使用
     * @param orderNo 订单编号
     * @param refundId 退款id
     */
    void unlockVisitor(String orderNo, Long refundId);

}
