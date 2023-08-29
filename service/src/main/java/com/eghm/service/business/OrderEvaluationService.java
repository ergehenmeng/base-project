package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.evaluation.OrderEvaluationAuditDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryRequest;
import com.eghm.vo.business.evaluation.ApplauseRateVO;
import com.eghm.vo.business.evaluation.EvaluationGroupVO;
import com.eghm.vo.business.evaluation.OrderEvaluationResponse;
import com.eghm.vo.business.evaluation.OrderEvaluationVO;

/**
 * <p>
 * 订单评价 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-28
 */
public interface OrderEvaluationService {

    /**
     * 查询订单评价信息
     * @param request 查询条件
     * @return 列表
     */
    Page<OrderEvaluationResponse> listPage(OrderEvaluationQueryRequest request);

    /**
     * 添加订单评价记录
     * 1. 一个订单下可能多个商品, 订单进行评价时需要对订单下所有商品评价,且必须同时评价才能更新订单状态
     * 2. 为了防止订单评价中包含特殊字符和图片,评论默认需要审核,审核通过后方可进行展示, 即使审核不通过也不影响订单状态
     * @param dto 评价信息
     */
    void create(OrderEvaluationDTO dto);

    /**
     * 审核评论信息
     * @param dto 审核信息
     */
    void audit(OrderEvaluationAuditDTO dto);

    /**
     * 移动端查询商品评论信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<OrderEvaluationVO> getByPage(OrderEvaluationQueryDTO dto);

    /**
     * 分组统计商品评论 好评 中评 差评 有图数量
     * @param productId 商品id
     * @return 统计数量
     */
    EvaluationGroupVO groupEvaluation(Long productId);

    /**
     * 计算商品好评率
     * @param productId 商品id
     * @return 商品好评率
     */
    ApplauseRateVO calcApplauseRate(Long productId);
}
