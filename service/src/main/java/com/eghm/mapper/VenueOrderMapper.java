package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.venue.VenueOrderQueryDTO;
import com.eghm.dto.business.order.venue.VenueOrderQueryRequest;
import com.eghm.model.VenueOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.venue.VenueOrderDetailResponse;
import com.eghm.vo.business.order.venue.VenueOrderDetailVO;
import com.eghm.vo.business.order.venue.VenueOrderResponse;
import com.eghm.vo.business.order.venue.VenueOrderVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 场馆预约订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-04
 */
public interface VenueOrderMapper extends BaseMapper<VenueOrder> {

    /**
     * 分页查询 (管理后台)
     *
     * @param page  分页
     * @param request 查询条件
     * @return 列表
     */
    Page<VenueOrderResponse> listPage(Page<VenueOrderResponse> page, @Param("param") VenueOrderQueryRequest request);

    /**
     * 分页查询 (移动端)
     *
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<VenueOrderVO> getList(Page<VenueOrderVO> page, @Param("param") VenueOrderQueryDTO dto);

    /**
     * 获取订单详情 (管理后台)
     *
     * @param orderNo 订单号
     * @param merchantId 会员id
     * @return 订单详情
     */
    VenueOrderDetailResponse detail(@Param("orderNo") String orderNo, @Param("merchantId") Long merchantId);

    /**
     * 获取订单详情 (移动端)
     *
     * @param orderNo 订单号
     * @param memberId 会员id
     * @return 订单详情
     */
    VenueOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);

    /**
     * 查询场馆快照
     *
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 场馆快照
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId, @Param("orderNo") String orderNo);
}
