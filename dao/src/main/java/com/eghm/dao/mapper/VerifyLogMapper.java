package com.eghm.dao.mapper;

import com.eghm.dao.model.VerifyLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单核销记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-06
 */
public interface VerifyLogMapper extends BaseMapper<VerifyLog> {

    /**
     * 统计某个订单被核销过的商品数量总数
     * @param orderId 订单id
     * @return 数量
     */
    int getVerifiedNum(@Param("orderId") Long orderId);
}
