package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.item.ItemOrderQueryDTO;
import com.eghm.dto.business.order.item.ItemOrderQueryRequest;
import com.eghm.dto.business.order.item.ItemRefundDTO;
import com.eghm.model.ItemOrder;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.item.ItemOrderDetailResponse;
import com.eghm.vo.business.order.item.ItemOrderDetailVO;
import com.eghm.vo.business.order.item.ItemOrderResponse;
import com.eghm.vo.business.order.item.ItemOrderVO;

import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
public interface ItemOrderService {

    /**
     * 分页查询用户订单列表
     * @param request 查询条件
     * @return 列表
     */
    Page<ItemOrderResponse> listPage(ItemOrderQueryRequest request);

    /**
     * 订单插入
     * @param order 商品订单
     */
    void insert(ItemOrder order);

    /**
     * 根据订单号查询商品订单
     * @param orderNo 订单号
     * @return 商品订单(普通商品一个订单存在多商品)
     */
    List<ItemOrder> getByOrderNo(String orderNo);

    /**
     * 统计待发货的零售订单数量
     * @param orderNo 订单号
     * @return 数量
     */
    Long countWaitDelivery(String orderNo);

    /**
     * 零售退款
     * @param orderNo 订单号
     * @param itemList 退款商品及数量
     * @return 零售信息
     */
    List<ItemOrder> refund(String orderNo, List<ItemRefundDTO> itemList);

    /**
     * 批量添加子订单信息
     * @param orderNo 订单号
     * @param packageList 商品信息
     * @param skuExpressMap 快递费
     */
    void insert(String orderNo, List<OrderPackage> packageList, Map<Long, Integer> skuExpressMap);

    /**
     * 查询订单信息
     * @param id id
     * @return 商品订单信息
     */
    ItemOrder selectById(Long id);

    /**
     * 查询商品订单,如果为空则抛异常
     * @param id id
     * @return 商品订单信息
     */
    ItemOrder selectByIdRequired(Long id);

    /**
     * 根据主键更新商品订单信息
     * @param itemOrder 商品订单
     */
    void updateById(ItemOrder itemOrder);

    /**
     * 获取订单下所有商品的数量
     * @param orderNo 订单编号
     * @return 总数量
     */
    int getProductNum(String orderNo);

    /**
     * 查询订单商品快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品基础信息
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);

    /**
     * 分页查询用户订单列表
     * @param dto 查询条件
     * @return 列表
     */
    List<ItemOrderVO> getByPage(ItemOrderQueryDTO dto);

    /**
     * 查询订单详情
     * @param orderNo 订单号
     * @param memberId 订单id
     * @return 订单信息
     */
    ItemOrderDetailVO getDetail(String orderNo, Long memberId);

    /**
     * 查询订单详情
     * @param orderNo 订单号
     * @return 订单信息
     */
    ItemOrderDetailResponse detail(String orderNo);

    /**
     * 根据主键查询零售订单信息
     * @param ids 订单id
     * @return list
     */
    List<ItemOrder> getByIds(List<Long> ids);

    /**
     * 批量更新商品订单信息
     *
     * @param list list
     */
    void updateBatchById(List<ItemOrder> list);
}
