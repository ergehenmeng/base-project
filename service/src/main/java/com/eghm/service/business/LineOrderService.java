package com.eghm.service.business;

import com.eghm.model.LineOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
public interface LineOrderService {

    /**
     * 插入线路订单
     * @param order 订单信息
     */
    void insert(LineOrder order);

    /**
     * 根据订单编号查询线路订单(未删除的订单)
     * @param orderNo 订单编号
     * @return 线路订单
     */
    LineOrder getByOrderNo(String orderNo);

    /**
     * 查询餐饮快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);
}
