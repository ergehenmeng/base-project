package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.line.LineOrderQueryDTO;
import com.eghm.dto.business.order.line.LineOrderQueryRequest;
import com.eghm.model.LineOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.line.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
public interface LineOrderService {

    /**
     * 分页查询线路订单
     * @param request 查询条件
     * @return 列表
     */
    Page<LineOrderResponse> listPage(LineOrderQueryRequest request);

    /**
     * 查询线路订单
     * @param request 查询条件
     * @return 列表
     */
    List<LineOrderResponse> getList(LineOrderQueryRequest request);

    /**
     * 分页查询线路订单
     * @param dto 查询条件
     * @return 列表
     */
    List<LineOrderVO> getByPage(LineOrderQueryDTO dto);

    /**
     * 插入线路订单
     * @param order 订单信息
     */
    void insert(LineOrder order);

    /**
     * 根据订单编号查询线路订单(未删除的订单)
     * @param orderNo 订单编号
     * @return 线路订单
     */
    LineOrder getByOrderNo(String orderNo);

    /**
     * 查询餐饮快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);

    /**
     * 查询线路订单详情
     * @param orderNo 订单编号
     * @param memberId 会员id
     * @return 订单详情
     */
    LineOrderDetailVO getDetail(String orderNo, Long memberId);

    /**
     * 查询线路订单详情
     * @param orderNo 订单编号
     * @return 订单详情
     */
    LineOrderDetailResponse detail(String orderNo);
}
