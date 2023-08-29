package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryRequest;
import com.eghm.model.OrderEvaluation;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.evaluation.OrderEvaluationResponse;
import com.eghm.vo.business.evaluation.OrderEvaluationVO;
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
     * 查询列表
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<OrderEvaluationResponse> listPage(Page<OrderEvaluationResponse> page, @Param("param") OrderEvaluationQueryRequest request);

    /**
     * 统计订单总分数
     * @param productId 商品信息
     * @return 总分数
     */
    AvgScoreVO getScore(@Param("productId") Long productId);

    /**
     * 查询商品评论信息
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<OrderEvaluationVO> getByPage(Page<OrderEvaluationVO> page, @Param("param") OrderEvaluationQueryDTO dto);

    /**
     * 统计分类数量
     * @param productId 商品id
     * @param queryType 2: 好评 3: 中评  4: 差评 5: 有图
     * @return 数量
     */
    int evaluationCount(@Param("productId") Long productId, @Param("queryType") Integer queryType);

    /**
     * 统计差评数量
     * @param productId 商品id
     * @return 数量
     */
    int badCount(@Param("productId") Long productId);
}
