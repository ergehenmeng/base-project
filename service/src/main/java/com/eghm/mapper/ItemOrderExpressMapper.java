package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ItemOrderExpress;
import com.eghm.vo.business.order.item.ExpressLogisticsVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
public interface ItemOrderExpressMapper extends BaseMapper<ItemOrderExpress> {

    /**
     * 根据id查询物流信息
     * @param id 发货物流id
     * @return 物流信息
     */
    ExpressLogisticsVO getById(@Param("id") Long id);
}
