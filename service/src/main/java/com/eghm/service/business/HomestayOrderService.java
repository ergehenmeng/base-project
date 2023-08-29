package com.eghm.service.business;

import com.eghm.model.HomestayOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
public interface HomestayOrderService {

    /**
     * 插入民宿订单
     * @param order 订单信息
     */
    void insert(HomestayOrder order);

    /**
     * 根据订单编号查询查询民宿订单
     * @param orderNo 订单编号
     * @return 民宿订单
     */
    HomestayOrder getByOrderNo(String orderNo);

    /**
     * 查询餐饮快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);
}
