package com.eghm.mapper;

import com.eghm.model.OrderEvaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.AvgScoreVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单评价 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-28
 */
public interface OrderEvaluationMapper extends BaseMapper<OrderEvaluation> {

    /**
     * 统计订单总分数
     * @param productId 商品信息
     * @return 总分数
     */
    AvgScoreVO getScore(@Param("productId") Long productId);
}
