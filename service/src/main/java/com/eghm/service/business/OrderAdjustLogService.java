package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eghm.dto.business.order.adjust.OrderAdjustRequest;
import com.eghm.model.OrderAdjustLog;

/**
 * <p>
 * 订单改价记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-25
 */
public interface OrderAdjustLogService extends IService<OrderAdjustLog> {

    /**
     * 创建零售改价记录信息
     *
     * @param request 改价信息
     */
    void itemAdjust(OrderAdjustRequest request);
}
