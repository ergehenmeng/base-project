package com.eghm.service.business.impl;

import com.eghm.dto.business.order.OrderEvaluationDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.model.OrderEvaluation;
import com.eghm.service.business.*;
import com.eghm.service.mq.service.MessageService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.AvgScoreVO;
import com.eghm.vo.business.ProductScoreVO;
import com.eghm.vo.business.order.ProductSnapshotVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>
 * 订单评价 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-28
 */
@Slf4j
@AllArgsConstructor
@Service("orderEvaluationService")
public class OrderEvaluationServiceImpl implements OrderEvaluationService {

    private final OrderEvaluationMapper orderEvaluationMapper;

    private final ItemOrderService itemOrderService;

    private final TicketOrderService ticketOrderService;

    private final HomestayOrderService homestayOrderService;

    private final LineOrderService lineOrderService;

    private final RestaurantOrderService restaurantOrderService;

    private final MessageService messageService;

    @Override
    public void evaluate(OrderEvaluationDTO dto) {
        OrderEvaluation evaluation = DataUtil.copy(dto, OrderEvaluation.class);
        evaluation.setState(0);
        ProductSnapshotVO snapshot = this.getSnapshot(dto.getOrderId(), dto.getProductType());
        evaluation.setProductId(snapshot.getProductId());
        evaluation.setProductTitle(snapshot.getProductTitle());
        evaluation.setProductCover(snapshot.getProductCover());
        orderEvaluationMapper.insert(evaluation);

        AvgScoreVO score = orderEvaluationMapper.getScore(snapshot.getProductId());
        ProductScoreVO vo = new ProductScoreVO(snapshot.getProductId(), dto.getProductType(), this.calcAvgScore(score.getNum(), score.getTotalScore()));
        messageService.send(ExchangeQueue.PRODUCT_SCORE, vo);
    }

    /**
     * 计算商品评价分数,保留一位小数
     * @param num 评价数
     * @param totalScore 总分数
     * @return 分数
     */
    private BigDecimal calcAvgScore(Integer num, Integer totalScore) {
        return BigDecimal.valueOf(totalScore).divide(BigDecimal.valueOf(num), 1, RoundingMode.HALF_UP);
    }

    /**
     * 查询订单对应商品快照信息
     * @param orderId 订单id
     * @param productType 商品类型
     * @return 商品信息
     */
    private ProductSnapshotVO getSnapshot(Long orderId, ProductType productType) {
        ProductSnapshotVO vo;
        switch (productType) {
            case TICKET:
                vo = ticketOrderService.getSnapshot(orderId);
                break;
            case ITEM:
                vo = itemOrderService.getSnapshot(orderId);
                break;
            case LINE:
                vo = lineOrderService.getSnapshot(orderId);
                break;
            case HOMESTAY:
                vo = homestayOrderService.getSnapshot(orderId);
                break;
            case RESTAURANT:
                vo = restaurantOrderService.getSnapshot(orderId);
                break;
            default:
                vo = null;
                break;
        }
        if (vo == null) {
            log.error("订单信息未查询,无法评价 [{}] [{}]", orderId, productType);
            throw new BusinessException(ErrorCode.PRODUCT_SNAPSHOT_NULL);
        }
        return vo;
    }
}
