package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eghm.dto.business.order.adjust.OrderAdjustRequest;
import com.eghm.model.OrderAdjustLog;
import com.eghm.vo.business.order.adjust.OrderAdjustResponse;

import java.util.List;

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
     * 获取改价记录列表
     *
     * @param orderNo 订单编号
     * @return 列表
     */
    List<OrderAdjustResponse> getList(String orderNo);

    /**
     * 创建零售改价记录信息
     *
     * @param request 改价信息
     */
    void itemAdjust(OrderAdjustRequest request);
}
