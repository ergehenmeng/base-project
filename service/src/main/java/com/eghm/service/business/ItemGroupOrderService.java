package com.eghm.service.business;

import com.eghm.model.ItemGroupOrder;
import com.eghm.model.Order;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.vo.business.group.GroupOrderVO;

import java.util.List;

/**
 * <p>
 * 拼团订单表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-24
 */
public interface ItemGroupOrderService {

    /**
     * 新增拼团订单
     *
     * @param context 上下文
     * @param order 订单信息
     * @param itemId 商品id
     */
    void save(ItemOrderCreateContext context, Order order, Long itemId);

    /**
     * 更新拼团订单状态
     *
     * @param bookingNo 拼团订单编号
     * @param state 状态
     */
    void updateState(String bookingNo, Integer state);

    /**
     * 删除拼团订单
     *
     * @param bookingNo 拼团订单编号
     * @param orderNo 状态
     */
    void delete(String bookingNo, String orderNo);

    /**
     * 获取拼团订单
     *
     * @param bookingNo 拼团订单编号
     * @param state  状态
     * @return 列表
     */
    List<ItemGroupOrder> getGroupList(String bookingNo, Integer state);

    /**
     * 获取拼团信息
     *
     * @param bookingNo 拼团订单编号
     * @param state 状态
     * @return 列表
     */
    GroupOrderVO getGroupOrder(String bookingNo, Integer state);
}
