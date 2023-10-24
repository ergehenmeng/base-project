package com.eghm.service.business;

import com.eghm.dto.business.order.homestay.HomestayOrderQueryDTO;
import com.eghm.model.HomestayOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailVO;
import com.eghm.vo.business.order.homestay.HomestayOrderVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
public interface HomestayOrderService {

    /**
     * 分页查询用户民宿订单
     * @param dto 查询条件
     * @return 列表
     */
    List<HomestayOrderVO> getByPage(HomestayOrderQueryDTO dto);

    /**
     * 插入民宿订单
     * @param order 订单信息
     */
    void insert(HomestayOrder order);

    /**
     * 根据订单编号查询查询民宿订单
     * @param orderNo 订单编号
     * @return 民宿订单
     */
    HomestayOrder getByOrderNo(String orderNo);

    /**
     * 查询餐饮快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);

    /**
     * 查询用户自己的民宿详情
     *
     * @param orderNo 订单编号
     * @param memberId 会员id
     * @return 民宿详情
     */
    HomestayOrderDetailVO getDetail(String orderNo, Long memberId);
}
