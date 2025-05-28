package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ExpressLogistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/19
 */
public interface ExpressLogisticsMapper extends BaseMapper<ExpressLogistics> {

    /**
     * 根据订单查询物流信息
     * @param orderNo 订单编号
     * @return 物流信息
     */
    List<ExpressLogistics> getExpress(@Param("orderNo") String orderNo);

}
