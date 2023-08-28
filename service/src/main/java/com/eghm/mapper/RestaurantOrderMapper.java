package com.eghm.mapper;

import com.eghm.model.RestaurantOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.order.ProductSnapshotVO;
import org.apache.ibatis.annotations.Param;

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
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId);
}
