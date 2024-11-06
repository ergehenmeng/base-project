package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constants.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ScanRechargeLogMapper;
import com.eghm.model.ScanRechargeLog;
import com.eghm.pay.AggregatePayService;
import com.eghm.pay.enums.TradeState;
import com.eghm.pay.vo.PayOrderVO;
import com.eghm.service.business.ScanRechargeLogService;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.state.machine.context.PayNotifyContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.eghm.pay.enums.TradeState.PAY_ERROR;
import static com.eghm.pay.enums.TradeState.TRADE_CLOSED;

/**
 * <p>
 * 扫码支付记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-20
 */
@Slf4j
@AllArgsConstructor
@Service("scanRechargeLogService")
public class ScanRechargeLogServiceImpl implements ScanRechargeLogService {

    private final ScoreAccountService scoreAccountService;

    private final AggregatePayService aggregatePayService;

    private final ScanRechargeLogMapper scanRechargeLogMapper;

    @Override
    public void rechargeNotify(PayNotifyContext context) {
        ScanRechargeLog rechargeLog = this.getByTradeNo(context.getTradeNo());
        PayOrderVO orderVO = aggregatePayService.queryOrder(rechargeLog.getTradeType(), context.getTradeNo());
        if (orderVO.getTradeState() == TradeState.SUCCESS || orderVO.getTradeState() == TradeState.TRADE_SUCCESS || orderVO.getTradeState() == TradeState.TRADE_FINISHED) {
            log.info("扫码充值成功 [{}]", context.getOrderNo());
            scoreAccountService.rechargeScanSuccess(rechargeLog.getMerchantId(), rechargeLog.getAmount(), context.getTradeNo());
            rechargeLog.setState(1);
            rechargeLog.setPayTime(orderVO.getSuccessTime());
            scanRechargeLogMapper.updateById(rechargeLog);
        } else if (orderVO.getTradeState() == TradeState.CLOSED || orderVO.getTradeState() == TradeState.NOT_PAY || orderVO.getTradeState() == PAY_ERROR || orderVO.getTradeState() == TRADE_CLOSED) {
            log.info("扫码充值失败 [{}]", context.getOrderNo());
            rechargeLog.setState(3);
            scanRechargeLogMapper.updateById(rechargeLog);
        } else {
            log.info("扫码充值未知状态 [{}]", context.getOrderNo());
        }
    }

    /**
     * 根据交易号查询充值记录
     *
     * @param tradeNo 交易单号
     * @return 扫码充值记录
     */
    private ScanRechargeLog getByTradeNo(String tradeNo) {
        LambdaQueryWrapper<ScanRechargeLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScanRechargeLog::getTradeNo, tradeNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        ScanRechargeLog rechargeLog = scanRechargeLogMapper.selectOne(wrapper);
        if (rechargeLog == null) {
            log.error("未找到对应的扫码充值记录 [{}]", tradeNo);
            throw new BusinessException(ErrorCode.RECHARGE_LOG_NULL);
        }
        return rechargeLog;
    }
}
