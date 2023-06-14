package com.eghm.service.business;

import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.VisitorState;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.handler.dto.VisitorDTO;

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
    void addVisitor(ProductType productType, String orderNo, List<VisitorDTO> voList);

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

    /**
     * 更新订单游客使用状态
     * @param orderNo 订单号
     * @param state 状态
     */
    void updateVisitor(String orderNo, VisitorState state);

    /**
     * 游客核销
     * @param orderNo  订单编号
     * @param visitorList 核销的人 如果为空则默认核销全部可以核销的
     * @param visitorId  核销记录id
     * @return 核销的数量
     */
    int visitorVerify(String orderNo, List<Long> visitorList, long visitorId);

    /**
     * 根据订单号统计未核销的游客数量
     * @param orderNo 订单编号
     * @return 数量
     */
    long getUnVerify(String orderNo);

    /**
     * 根据id列表查询游客信息
     * @param ids ids
     * @param orderNo 额外过滤条件
     * @return 游客列表
     */
    List<OrderVisitor> getByIds(List<Long> ids, String orderNo);
}
