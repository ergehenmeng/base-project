package com.eghm.service.business;

import com.eghm.dao.model.OrderRefundLog;

/**
 * @author 二哥很猛
 * @date 2022/8/1
 */
public interface OrderRefundLogService {

    /**
     * 新增退款记录
     * @param log 退款记录
     */
    void insert(OrderRefundLog log);

    /**
     * 根据id查询退款记录
     * @param id id
     * @return 退款记录
     */
    OrderRefundLog selectById(Long id);

    /**
     * 主键查询退款记录
     * @param id id
     * @return 退款记录
     */
    OrderRefundLog selectByIdRequired(Long id);

    /**
     * 根据id更新退款记录
     * @param log 退款记录
     * @return 1条
     */
    int updateById(OrderRefundLog log);

    /**
     * 统计订单总退款金额 包含退款处理中的
     * @param orderId 订单id
     * @return 退款金额
     */
    int getTotalRefundAmount(Long orderId);

    /**
     * 根据退款流水或查询退款记录
     * @param outRefundNo 退款流水或
     * @return 退款信息 可能为空
     */
    OrderRefundLog selectByOutRefundNo(String outRefundNo);
}
