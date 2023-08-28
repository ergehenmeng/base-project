package com.eghm.mapper;

import com.eghm.model.HomestayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.order.ProductSnapshotVO;
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
     * 查询餐饮快照
     * @param orderId 订单id
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId);

}
