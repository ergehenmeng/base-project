package com.eghm.service.business;

import com.eghm.enums.BookingState;
import com.eghm.model.ItemGroupOrder;
import com.eghm.model.Order;
import com.eghm.state.machine.context.ItemOrderCreateContext;
import com.eghm.vo.business.group.GroupOrderCancelVO;
import com.eghm.vo.business.group.GroupOrderDetailVO;

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
    void updateState(String bookingNo, BookingState state);

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
    List<ItemGroupOrder> getGroupList(String bookingNo, BookingState state);

    /**
     * 用户在同一个活动下只能有一个订单(如果拼团失败则允许再次下单)
     *
     * @param bookingId 拼团活动id
     * @param memberId 会员id
     */
    void checkGroupOrderOnce(Long bookingId, Long memberId);

    /**
     * 获取拼团详情
     *
     * @param bookingNo 拼团订单编号
     * @return 拼团详细信息
     */
    GroupOrderDetailVO getGroupDetail(String bookingNo);

    /**
     * 拼团订单退款
     * 1: 该方法只是修改拼团订单状态等,不做真实退款操作
     * 2: 如果是团长退款,且未成团, 该方法会将团员下所有订单都退款
     * @param order 订单信息
     */
    void refundGroupOrder(Order order);

    /**
     * 取消拼团订单 (全部) 拼团过期自动取消
     *
     * @param vo 拼团信息
     */
    void cancelGroupOrder(GroupOrderCancelVO vo);

    /**
     * 取消拼团订单 (单个拼团)
     *
     * @param bookingNo 拼团订单号
     */
    void cancelGroupOrder(String bookingNo);

    /**
     * 拼团活动下架取消
     *
     * @param bookingId 拼团活动id
     */
    void cancelGroupOrder(Long bookingId);
}
