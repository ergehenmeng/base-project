package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryDTO;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryRequest;
import com.eghm.model.HomestayOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailResponse;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailVO;
import com.eghm.vo.business.order.homestay.HomestayOrderResponse;
import com.eghm.vo.business.order.homestay.HomestayOrderVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 民宿订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
public interface HomestayOrderMapper extends BaseMapper<HomestayOrder> {

    /**
     * 分页查询门票订单
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<HomestayOrderResponse> listPage(Page<HomestayOrderResponse> page, @Param("param") HomestayOrderQueryRequest request);

    /**
     * 查询用户民宿订单
     *
     * @param page 分页
     * @param dto 查询条件
     * @return 列表, 不查询总页数
     */
    Page<HomestayOrderVO> getList(Page<HomestayOrderVO> page, @Param("param") HomestayOrderQueryDTO dto);

    /**
     * 查询民宿快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId, @Param("orderNo") String orderNo);

    /**
     * 查询民宿订单
     * @param orderNo 订单编号
     * @param memberId 会员id
     * @return 订单详情
     */
    HomestayOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);

    /**
     * 查询民宿订单
     * @param orderNo 订单编号
     * @return 订单详情
     */
    HomestayOrderDetailResponse detail(@Param("orderNo") String orderNo);
}
