package com.eghm.service.business;

import com.eghm.dto.business.order.OrderEvaluationDTO;

/**
 * <p>
 * 订单评价 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-28
 */
public interface OrderEvaluationService {

    /**
     * 添加订单评价记录
     * @param dto 评价信息
     */
    void evaluate(OrderEvaluationDTO dto);
}
