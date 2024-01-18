package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.item.ItemOrderQueryDTO;
import com.eghm.dto.business.order.item.ItemOrderQueryRequest;
import com.eghm.model.ItemOrder;
import com.eghm.vo.business.order.item.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-05
 */
public interface ItemOrderMapper extends BaseMapper<ItemOrder> {

    /**
     * 查询用户零售订单列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 列表
     */
    Page<ItemOrderResponse> listPage(Page<ItemOrderResponse> page, @Param("param") ItemOrderQueryRequest dto);

    /**
     * 查询订单下所有商品的总数量
     *
     * @param orderNo 订单编号
     * @return 总数量
     */
    int getProductNum(@Param("orderNo") String orderNo);

    /**
     * 查询用户零售订单列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 列表
     */
    Page<ItemOrderVO> getList(Page<ItemOrderVO> page, @Param("param") ItemOrderQueryDTO dto);

    /**
     * 查询订单详情
     *
     * @param orderNo  订单编号
     * @param memberId 用户id
     * @return 订单详情
     */
    ItemOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);

    /**
     * 查询订单商品列表
     *
     * @param orderNo 订单编号
     * @return 商品信息
     */
    List<ItemOrderListVO> getItemList(@Param("orderNo") String orderNo);

    /**
     * 查询订单详情
     *
     * @param orderNo    订单编号
     * @param merchantId 商户ID
     * @return 订单详情
     */
    ItemOrderDetailResponse detail(@Param("orderNo") String orderNo, @Param("merchantId") Long merchantId);

    /**
     * 查询订单中已发货的商品及物流信息
     *
     * @param orderNo 订单号
     * @return 物流及包裹商品信息
     */
    List<ItemShippedResponse> getShippedList(@Param("orderNo") String orderNo);

    /**
     * 获取快照
     *
     * @param orderId 订单id
     * @param memberId 用户id
     * @return 快照信息
     */
    ItemOrderSnapshotVO getSnapshot(@Param("orderId") Long orderId, @Param("memberId") Long memberId);
}
