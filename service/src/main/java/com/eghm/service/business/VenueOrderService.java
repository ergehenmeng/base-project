package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.venue.VenueOrderQueryDTO;
import com.eghm.dto.business.order.venue.VenueOrderQueryRequest;
import com.eghm.model.VenueOrder;
import com.eghm.vo.business.order.venue.VenueOrderDetailResponse;
import com.eghm.vo.business.order.venue.VenueOrderDetailVO;
import com.eghm.vo.business.order.venue.VenueOrderResponse;
import com.eghm.vo.business.order.venue.VenueOrderVO;

import java.util.List;

/**
 * <p>
 * 场馆预约订单表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-04
 */
public interface VenueOrderService {

    /**
     * 分页查询场馆预约订单 (管理后台)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<VenueOrderResponse> listPage(VenueOrderQueryRequest request);

    /**
     * 分页查询场馆预约订单 (移动端)
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<VenueOrderVO> getByPage(VenueOrderQueryDTO dto);

    /**
     * 新增场馆预约订单
     *
     * @param venueOrder 订单信息
     */
    void insert(VenueOrder venueOrder);

    /**
     * 查询场馆预约订单
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    VenueOrder getByOrderNo(String orderNo);

    /**
     * 更新预约订单库存
     *
     * @param orderNo 订单编号
     * @param num +:增加库存 -:减少库存
     */
    void updateStock(String orderNo, Integer num);

    /**
     * 获取订单详情 (管理后台)
     *
     * @param orderNo 订单信息
     * @return 订单信息
     */
    VenueOrderDetailResponse detail(String orderNo);

    /**
     * 获取订单详情 (移动端)
     *
     * @param orderNo 订单号
     * @param memberId 用户id
     * @return 订单详情
     */
    VenueOrderDetailVO getDetail(String orderNo, Long memberId);
}
