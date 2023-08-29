package com.eghm.mapper;

import com.eghm.model.LineOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.order.ProductSnapshotVO;
import org.apache.ibatis.annotations.Param;

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
     * 查询线路快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品线路快照
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId, @Param("orderNo") String orderNo);
}
