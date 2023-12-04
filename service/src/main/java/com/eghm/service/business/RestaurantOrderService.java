package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.model.RestaurantOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.restaurant.RestaurantOrderDetailResponse;
import com.eghm.vo.business.order.restaurant.RestaurantOrderDetailVO;
import com.eghm.vo.business.order.restaurant.RestaurantOrderResponse;
import com.eghm.vo.business.order.restaurant.RestaurantOrderVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
public interface RestaurantOrderService {

    /**
     * 分页查询餐饮订单列表 (管理后台)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<RestaurantOrderResponse> listPage(VoucherOrderQueryRequest request);

    /**
     * 分页查询餐饮订单列表 导出使用 (管理后台)
     *
     * @param request 查询条件
     * @return 列表
     */
    List<RestaurantOrderResponse> getList(VoucherOrderQueryRequest request);

    /**
     * 分页查询餐饮订单列表 移动端(自己)
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<RestaurantOrderVO> getByPage(VoucherOrderQueryDTO dto);

    /**
     * 插入餐饮订单
     * @param order 订单信息
     */
    void insert(RestaurantOrder order);

    /**
     * 根据订单编号查询餐饮订单
     * @param orderNo 订单编号
     * @return 餐饮订单
     */
    RestaurantOrder getByOrderNo(String orderNo);

    /**
     * 主键查询
     * @param id id
     * @return 餐饮订单
     */
    RestaurantOrder selectById(Long id);

    /**
     * 查询餐饮快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);

    /**
     * 查询订单详情
     *
     * @param orderNo 订单编号
     * @param memberId 用户id
     * @return 订单详情
     */
    RestaurantOrderDetailVO getDetail(String orderNo, Long memberId);

    /**
     * 查询订单详情
     *
     * @param orderNo 订单编号
     * @return 订单详情
     */
    RestaurantOrderDetailResponse detail(String orderNo);
}
