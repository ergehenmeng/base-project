package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryRequest;
import com.eghm.model.VoucherOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.restaurant.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/23
 */
public interface VoucherOrderService {

    /**
     * 分页查询餐饮订单列表 (管理后台)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<VoucherOrderResponse> listPage(VoucherOrderQueryRequest request);

    /**
     * 分页查询餐饮订单列表 导出使用 (管理后台)
     *
     * @param request 查询条件
     * @return 列表
     */
    List<VoucherOrderResponse> getList(VoucherOrderQueryRequest request);

    /**
     * 分页查询餐饮订单列表 移动端(自己)
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<VoucherOrderVO> getByPage(VoucherOrderQueryDTO dto);

    /**
     * 插入餐饮订单
     *
     * @param order 订单信息
     */
    void insert(VoucherOrder order);

    /**
     * 根据订单编号查询餐饮订单
     *
     * @param orderNo 订单编号
     * @return 餐饮订单
     */
    VoucherOrder getByOrderNo(String orderNo);

    /**
     * 主键查询
     *
     * @param id id
     * @return 餐饮订单
     */
    VoucherOrder selectById(Long id);

    /**
     * 查询餐饮快照
     *
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);

    /**
     * 查询订单详情
     *
     * @param orderNo  订单编号
     * @param memberId 用户id
     * @return 订单详情
     */
    VoucherOrderDetailVO getDetail(String orderNo, Long memberId);

    /**
     * 查询订单详情
     *
     * @param orderNo 订单编号
     * @return 订单详情
     */
    VoucherOrderDetailResponse detail(String orderNo);

    /**
     * 查询订单快照详情
     *
     * @param orderNo 订单编号
     * @param memberId 用户id
     * @return 订单快照详情
     */
    VoucherOrderSnapshotVO snapshotDetail(String orderNo, Long memberId);
}
