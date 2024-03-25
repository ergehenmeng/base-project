package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.freeze.AccountFreezeDTO;
import com.eghm.dto.business.freeze.AccountFreezeQueryRequest;
import com.eghm.dto.business.freeze.RefundChangeDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ChangeType;
import com.eghm.enums.ref.FreezeState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.AccountFreezeLogMapper;
import com.eghm.model.AccountFreezeLog;
import com.eghm.service.business.AccountFreezeLogService;
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.AlarmService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.freeze.AccountFreezeLogResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商户资金冻结记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-28
 */
@Slf4j
@AllArgsConstructor
@Service("accountFreezeLogService")
public class AccountFreezeLogServiceImpl implements AccountFreezeLogService {

    private final JsonService jsonService;

    private final AlarmService alarmService;

    private final AccountFreezeLogMapper accountFreezeLogMapper;

    @Override
    public Page<AccountFreezeLogResponse> getByPage(AccountFreezeQueryRequest request) {
        return accountFreezeLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<AccountFreezeLogResponse> getList(AccountFreezeQueryRequest request) {
        return accountFreezeLogMapper.getByPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public void addFreezeLog(AccountFreezeDTO dto) {
        log.info("添加订单冻结记录 [{}]", jsonService.toJson(dto));
        if (dto.getAmount() <= 0) {
            // 金额小于0不做处理
            return;
        }
        AccountFreezeLog freezeLog = this.getFreezeLog(dto.getMerchantId(), dto.getOrderNo());
        if (freezeLog != null) {
            log.error("新增冻结记录发现已存在冻结信息:[{}] [{}]", dto.getMerchantId(), dto.getOrderNo());
            alarmService.sendMsg(String.format("新增冻结记录发现已存在冻结信息:[%s] [%s]", dto.getMerchantId(), dto.getOrderNo()));
            return;
        }
        AccountFreezeLog copy = DataUtil.copy(dto, AccountFreezeLog.class);
        copy.setState(FreezeState.FREEZE);
        accountFreezeLogMapper.insert(copy);
    }

    @Override
    public void refund(RefundChangeDTO dto) {
        log.info("退款更新订单冻结记录 [{}]", jsonService.toJson(dto));
        if (dto.getRefundAmount() <= 0) {
            // 金额小于0不做处理
            return;
        }
        AccountFreezeLog freezeLog = this.getCheckedLog(dto.getMerchantId(), dto.getOrderNo());
        Integer amount = freezeLog.getAmount();
        int surplusAmount = amount - dto.getRefundAmount();
        if (surplusAmount <= 0) {
            freezeLog.setAmount(surplusAmount);
            freezeLog.setChangeType(ChangeType.REFUND_UNFREEZE);
            freezeLog.setUnfreezeTime(LocalDateTime.now());
        } else {
            freezeLog.setAmount(surplusAmount);
        }
        accountFreezeLogMapper.updateById(freezeLog);
    }

    @Override
    public AccountFreezeLog complete(Long merchantId, String orderNo) {
        log.info("订单完成更新订单冻结记录 [{}] [{}]", merchantId, orderNo);
        AccountFreezeLog freezeLog = this.getCheckedLog(merchantId, orderNo);
        freezeLog.setState(FreezeState.UNFREEZE);
        freezeLog.setChangeType(ChangeType.COMPLETE_UNFREEZE);
        freezeLog.setUnfreezeTime(LocalDateTime.now());
        accountFreezeLogMapper.updateById(freezeLog);
        return freezeLog;
    }

    /**
     * 获取冻结记录, 如果不存在或不是冻结则抛异常
     *
     * @param merchantId 商户id
     * @param orderNo 订单号
     * @return 冻结记录
     */
    private AccountFreezeLog getCheckedLog(Long merchantId, String orderNo) {
        AccountFreezeLog freezeLog = this.getFreezeLogRequired(merchantId, orderNo);
        if (freezeLog.getState() == FreezeState.UNFREEZE) {
            log.error("订单冻结记录已解冻:无法更新冻结信息[{}] [{}]", merchantId, orderNo);
            alarmService.sendMsg(String.format("订单冻结记录已解冻,无法更新冻结信息:[%s] [%s]", merchantId, orderNo));
            throw new BusinessException(ErrorCode.FREEZE_LOG_UNFREEZE);
        }
        return freezeLog;
    }

    /**
     * 获取冻结记录
     *
     * @param merchantId 商户号
     * @param orderNo 订单号
     * @return 冻结记录
     */
    private AccountFreezeLog getFreezeLog(Long merchantId, String orderNo) {
        LambdaQueryWrapper<AccountFreezeLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AccountFreezeLog::getMerchantId, merchantId);
        wrapper.eq(AccountFreezeLog::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return accountFreezeLogMapper.selectOne(wrapper);
    }

    /**
     * 获取冻结记录
     *
     * @param merchantId 商户号
     * @param orderNo 订单号
     * @return 冻结记录 不存在抛异常
     */
    private AccountFreezeLog getFreezeLogRequired(Long merchantId, String orderNo) {
        AccountFreezeLog freezeLog = this.getFreezeLog(merchantId, orderNo);
        if (freezeLog == null) {
            log.error("订单冻结日志不存在 [{}] [{}]", merchantId, orderNo);
            alarmService.sendMsg(String.format("订单冻结日志不存在:%s", orderNo));
            throw new BusinessException(ErrorCode.FREEZE_LOG_NULL);
        }
        return freezeLog;
    }
}
