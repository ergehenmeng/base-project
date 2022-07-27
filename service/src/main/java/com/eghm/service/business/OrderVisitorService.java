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
     * @param orderId 订单id
     * @param voList 游客信息
     */
    void addVisitor(ProductType productType, Long orderId, List<VisitorVO> voList);
}
