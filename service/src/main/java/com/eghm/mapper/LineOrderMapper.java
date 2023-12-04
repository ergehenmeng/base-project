package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.line.LineOrderQueryDTO;
import com.eghm.dto.business.order.line.LineOrderQueryRequest;
import com.eghm.model.LineOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.line.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 线路订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-01
 */
public interface LineOrderMapper extends BaseMapper<LineOrder> {

    /**
     * 分页查询门票订单
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<LineOrderResponse> listPage(Page<LineOrderResponse> page, @Param("param") LineOrderQueryRequest request);

    /**
     * 查询线路快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品线路快照
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId, @Param("orderNo") String orderNo);

    /**
     * 查询用户线路订单列表
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<LineOrderVO> getList(Page<LineOrderVO> page, @Param("param") LineOrderQueryDTO dto);

    /**
     * 查询线路订单详情
     * @param orderNo 订单编号
     * @param memberId 会员id
     * @return 订单信息
     */
    LineOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);

    /**
     * 查询线路订单详情
     * @param orderNo 订单编号
     * @param merchantId 商户id
     * @return 订单信息
     */
    LineOrderDetailResponse detail(@Param("orderNo") String orderNo, @Param("merchantId") Long merchantId);
}
