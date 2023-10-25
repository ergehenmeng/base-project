package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.model.RestaurantOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.line.LineOrderDetailVO;
import com.eghm.vo.business.order.restaurant.RestaurantOrderDetailVO;
import com.eghm.vo.business.order.restaurant.RestaurantOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 餐饮券订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-23
 */
public interface RestaurantOrderMapper extends BaseMapper<RestaurantOrder> {

    /**
     * 查询餐饮快照
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
    Page<RestaurantOrderVO> getList(Page<RestaurantOrderVO> page, @Param("param") VoucherOrderQueryDTO dto);

    /**
     * 查询餐饮订单详情
     * @param orderNo 订单编号
     * @param memberId 会员id
     * @return 订单信息
     */
    RestaurantOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);
}
