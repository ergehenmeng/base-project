package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.venue.VenueOrderQueryDTO;
import com.eghm.model.VenueOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.order.venue.VenueOrderDetailVO;
import com.eghm.vo.business.order.venue.VenueOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 分页查询
     *
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<VenueOrderVO> getList(Page<VenueOrderVO> page, @Param("param") VenueOrderQueryDTO dto);

    /**
     * 获取订单详情
     *
     * @param orderNo 订单号
     * @param memberId 会员id
     * @return 订单详情
     */
    VenueOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);
}
