package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryRequest;
import com.eghm.model.VoucherOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.restaurant.*;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 餐饮券订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-23
 */
public interface VoucherOrderMapper extends BaseMapper<VoucherOrder> {

    /**
     * 分页查询门票订单
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<VoucherOrderResponse> listPage(Page<VoucherOrderResponse> page, @Param("param") VoucherOrderQueryRequest request);

    /**
     * 查询餐饮快照
     *
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId, @Param("orderNo") String orderNo);

    /**
     * 分页查询餐饮订单列表
     *
     * @param dto 查询条件
     * @return 列表
     */
    Page<VoucherOrderVO> getList(Page<VoucherOrderVO> page, @Param("param") VoucherOrderQueryDTO dto);

    /**
     * 查询餐饮订单详情
     *
     * @param orderNo  订单编号
     * @param memberId 会员id
     * @return 订单信息
     */
    VoucherOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);

    /**
     * 查询餐饮订单详情
     *
     * @param orderNo    订单编号
     * @param merchantId 商户ID
     * @return 订单信息
     */
    VoucherOrderDetailResponse detail(@Param("orderNo") String orderNo, @Param("merchantId") Long merchantId);

    /**
     * 查询餐饮订单快照详情
     *
     * @param orderNo 订单编号
     * @param memberId 会员id
     * @return 订单快照信息
     */
    VoucherOrderSnapshotVO snapshotDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);
}
