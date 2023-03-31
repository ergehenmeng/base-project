package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.LotteryState;
import com.eghm.exception.BusinessException;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.mapper.LotteryMapper;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryPrize;
import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.dto.business.lottery.LotteryPrizeConfigRequest;
import com.eghm.service.business.LotteryPrizeConfigService;
import com.eghm.service.business.LotteryPrizeService;
import com.eghm.service.business.LotteryService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 抽奖活动表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Service
@AllArgsConstructor
@Slf4j
public class LotteryServiceImpl implements LotteryService {

    private final LotteryMapper lotteryMapper;
    
    private final LotteryPrizeService lotteryPrizeService;
    
    private final LotteryPrizeConfigService lotteryPrizeConfigService;
    
    @Override
    public void create(LotteryAddRequest request) {
        this.checkConfig(request.getConfigList());
        this.redoTitle(request.getTitle(), null, SecurityHolder.getMerchantId());
        Lottery lottery = DataUtil.copy(request, Lottery.class);
        lotteryMapper.insert(lottery);
        List<LotteryPrize> prizeList = lotteryPrizeService.insert(lottery.getId(), request.getPrizeList());
        List<Long> prizeIds = prizeList.stream().map(LotteryPrize::getId).collect(Collectors.toList());
        lotteryPrizeConfigService.insert(lottery.getId(), request.getConfigList(), prizeIds);
    }

    @Override
    public void update(LotteryEditRequest request) {
        this.checkConfig(request.getConfigList());
        this.redoTitle(request.getTitle(), request.getId(), SecurityHolder.getMerchantId());
        Lottery select = lotteryMapper.selectById(request.getId());
        if (select.getState() != LotteryState.INIT) {
            throw new BusinessException(ErrorCode.LOTTERY_NOT_INIT);
        }
        Lottery lottery = DataUtil.copy(request, Lottery.class);
        lotteryMapper.updateById(lottery);
        List<LotteryPrize> prizeList = lotteryPrizeService.update(lottery.getId(), request.getPrizeList());
        List<Long> prizeIds = prizeList.stream().map(LotteryPrize::getId).collect(Collectors.toList());
        lotteryPrizeConfigService.update(lottery.getId(), request.getConfigList(), prizeIds);
    }

    /**
     * 校验中奖配置信息
     * @param configList 配置信息
     */
    private void checkConfig(List<LotteryPrizeConfigRequest> configList) {
        Optional<BigDecimal> optional = configList.stream().map(LotteryPrizeConfigRequest::getRatio).reduce(BigDecimal::add);
        if (!optional.isPresent() || optional.get().compareTo(BigDecimal.valueOf(100L)) != 0) {
            throw new BusinessException(ErrorCode.LOTTERY_RATIO);
        }
    }
    
    /**
     * 校验抽奖活动名称是否重复, 主要:同一个商户下不能重复
     * @param title 活动名称
     * @param id 活动id
     * @param merchantId 商户id
     */
    private void redoTitle(String title, Long id, Long merchantId) {
        LambdaQueryWrapper<Lottery> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Lottery::getMerchantId, merchantId);
        wrapper.eq(Lottery::getTitle, title);
        wrapper.ne(id != null, Lottery::getId, id);
        Long count = lotteryMapper.selectCount(wrapper);
        if (count > 0) {
            log.warn("抽奖活动名称重复 [{}] [{}] [{}]", title, id, merchantId);
            throw new BusinessException(ErrorCode.LOTTERY_REDO);
        }
    }
    
}
