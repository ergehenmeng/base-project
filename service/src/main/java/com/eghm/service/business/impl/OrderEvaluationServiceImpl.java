package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.order.evaluation.*;
import com.eghm.dto.ext.SendNotice;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.NoticeType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.model.Order;
import com.eghm.model.OrderEvaluation;
import com.eghm.service.business.*;
import com.eghm.service.member.MemberNoticeService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.evaluation.*;
import com.eghm.vo.business.order.ProductSnapshotVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final VoucherOrderService voucherOrderService;

    private final OrderService orderService;

    private final SysConfigApi sysConfigApi;

    private final VenueOrderService venueOrderService;

    private final MemberNoticeService memberNoticeService;

    @Override
    public Page<OrderEvaluationResponse> listPage(OrderEvaluationQueryRequest request) {
        return orderEvaluationMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(OrderEvaluationDTO dto) {
        Order order = orderService.getByOrderNo(dto.getOrderNo());
        if (!order.getMemberId().equals(dto.getMemberId())) {
            log.error("订单评价,操作了不属于自己的订单 [{}] [{}]", dto.getMemberId(), dto.getOrderNo());
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        if (Boolean.TRUE.equals(order.getEvaluateState())) {
            log.error("订单重复评价 [{}] [{}]", dto.getMemberId(), dto.getOrderNo());
            throw new BusinessException(ErrorCode.EVALUATION_REDO);
        }
        if (order.getState() != OrderState.COMPLETE) {
            log.error("订单状态已完成, 无法操作 [{}] [{}]", dto.getMemberId(), dto.getOrderNo());
            throw new BusinessException(ErrorCode.ORDER_COMPLETE);
        }
        for (EvaluationDTO eval : dto.getCommentList()) {
            ProductSnapshotVO snapshot = this.getSnapshot(eval.getOrderId(), dto.getOrderNo(), ProductType.prefix(dto.getOrderNo()));
            OrderEvaluation evaluation = DataUtil.copy(eval, OrderEvaluation.class);
            evaluation.setState(1);
            evaluation.setOrderNo(dto.getOrderNo());
            evaluation.setStoreId(snapshot.getStoreId());
            evaluation.setProductId(snapshot.getProductId());
            evaluation.setProductTitle(snapshot.getProductTitle());
            evaluation.setSkuTitle(snapshot.getSkuTitle());
            evaluation.setProductCover(snapshot.getProductCover());
            evaluation.setProductType(snapshot.getProductType());
            evaluation.setAnonymity(dto.getAnonymity());
            orderEvaluationMapper.insert(evaluation);
        }
        order.setEvaluateState(true);
        orderService.updateById(order);
    }

    @Override
    public void createDefault(String orderNo) {
        log.info("开始创建默认评价 [{}]", orderNo);
        Order order = orderService.getByOrderNo(orderNo);
        if (Boolean.TRUE.equals(order.getEvaluateState())) {
            return;
        }
        List<ProductSnapshotVO> voList = orderService.getProductList(orderNo);
        if (CollUtil.isEmpty(voList)) {
            return;
        }
        for (ProductSnapshotVO vo : voList) {
            OrderEvaluation evaluation = DataUtil.copy(vo, OrderEvaluation.class);
            evaluation.setScore(5);
            evaluation.setLogisticsScore(5);
            evaluation.setComment("用户未填写评价内容");
            evaluation.setState(1);
            evaluation.setAnonymity(true);
            evaluation.setSystemEvaluate(true);
            orderEvaluationMapper.insert(evaluation);
        }

        order.setEvaluateState(true);
        orderService.updateById(order);
    }

    @Override
    public void shield(OrderEvaluationShieldDTO dto) {
        OrderEvaluation evaluation = orderEvaluationMapper.selectById(dto.getId());
        if (evaluation == null) {
            log.error("订单评论信息未查询到 [{}]", dto.getId());
            throw new BusinessException(ErrorCode.EVALUATION_NULL);
        }
        evaluation.setState(2);
        evaluation.setUserId(dto.getUserId());
        evaluation.setAuditRemark(dto.getRemark());
        orderEvaluationMapper.updateById(evaluation);
        // 屏蔽后会发送站内信
        SendNotice notice = new SendNotice();
        notice.setNoticeType(NoticeType.EVALUATION_REFUSE);
        Map<String, Object> params = new HashMap<>(4);
        params.put("orderNo", evaluation.getOrderNo());
        params.put("productTitle", evaluation.getProductTitle());
        params.put("remark", evaluation.getAuditRemark());
        notice.setParams(params);
        memberNoticeService.sendNotice(evaluation.getMemberId(), notice);
    }

    @Override
    public List<OrderEvaluationVO> getByPage(OrderEvaluationQueryDTO dto) {
        Page<OrderEvaluationVO> byPage = orderEvaluationMapper.getByPage(dto.createPage(false), dto);
        if (CollUtil.isNotEmpty(byPage.getRecords())) {
            this.hiddenAnonymity(byPage.getRecords());
        }
        return byPage.getRecords();
    }

    @Override
    public EvaluationGroupVO groupEvaluation(Long productId) {
        EvaluationGroupVO vo = new EvaluationGroupVO();
        vo.setHigh(orderEvaluationMapper.evaluationCount(productId, 2));
        vo.setMedium(orderEvaluationMapper.evaluationCount(productId, 3));
        vo.setBad(orderEvaluationMapper.evaluationCount(productId, 4));
        vo.setImg(orderEvaluationMapper.evaluationCount(productId, 5));
        return vo;
    }

    @Override
    public ApplauseRateVO calcApplauseRate(Long productId) {
        ApplauseRateVO vo = new ApplauseRateVO();
        long total = orderEvaluationMapper.evaluationCount(productId, null);
        long bad = orderEvaluationMapper.badCount(productId);
        vo.setCommentNum(total);
        vo.setRate(this.getApplauseRate(total, bad));
        return vo;
    }

    @Override
    public AvgScoreVO getProductScore(Long productId) {
        return orderEvaluationMapper.getProductScore(productId);
    }

    @Override
    public AvgScoreVO getStoreScore(Long storeId) {
        return orderEvaluationMapper.getStoreScore(storeId);
    }

    /**
     * 计算好评率
     *
     * @param total 总评价数
     * @param bad   差评数
     * @return 好评率
     */
    private int getApplauseRate(long total, long bad) {
        if (total == 0) {
            return 0;
        }
        return BigDecimal.valueOf((total - bad) * 100L).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP).intValue();
    }

    /**
     * 隐藏昵称和头像
     *
     * @param voList 评论信息
     */
    private void hiddenAnonymity(List<OrderEvaluationVO> voList) {
        voList.forEach(vo -> {
            if (Boolean.TRUE.equals(vo.getAnonymity())) {
                vo.setAvatar(sysConfigApi.getString(ConfigConstant.ANONYMITY_AVATAR));
                vo.setNickName("匿名");
            }
        });
    }

    /**
     * 查询订单对应商品快照信息
     *
     * @param orderId     订单id
     * @param productType 商品类型
     * @return 商品信息
     */
    private ProductSnapshotVO getSnapshot(Long orderId, String orderNo, ProductType productType) {
        ProductSnapshotVO vo;
        switch (productType) {
            case TICKET:
                vo = ticketOrderService.getSnapshot(orderId, orderNo);
                break;
            case ITEM:
                vo = itemOrderService.getSnapshot(orderId, orderNo);
                break;
            case LINE:
                vo = lineOrderService.getSnapshot(orderId, orderNo);
                break;
            case HOMESTAY:
                vo = homestayOrderService.getSnapshot(orderId, orderNo);
                break;
            case VOUCHER:
                vo = voucherOrderService.getSnapshot(orderId, orderNo);
                break;
            case VENUE:
                vo = venueOrderService.getSnapshot(orderId, orderNo);
                break;
            default:
                vo = null;
                break;
        }
        if (vo == null) {
            log.error("订单信息未查询,无法评价 [{}] [{}] [{}]", orderId, orderNo, productType);
            throw new BusinessException(ErrorCode.PRODUCT_SNAPSHOT_NULL);
        }
        vo.setProductType(productType);
        return vo;
    }
}
