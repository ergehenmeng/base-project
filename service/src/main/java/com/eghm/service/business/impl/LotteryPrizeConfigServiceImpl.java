package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DecimalUtil;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.mapper.LotteryPrizeConfigMapper;
import com.eghm.model.LotteryPrizeConfig;
import com.eghm.model.dto.business.lottery.LotteryPrizeConfigRequest;
import com.eghm.service.business.LotteryPrizeConfigService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 抽奖位置配置表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Service
@AllArgsConstructor
public class LotteryPrizeConfigServiceImpl implements LotteryPrizeConfigService {

    private final LotteryPrizeConfigMapper lotteryPrizeConfigMapper;
    
    @Override
    public void insert(Long lotteryId, List<LotteryPrizeConfigRequest> positionList, List<Long> prizeIds) {
        int weight = 0;
        for (int i = 0; i < positionList.size(); i++) {
            LotteryPrizeConfigRequest request = positionList.get(i);
            if (request.getLocation() != i + 1 ) {
                throw new BusinessException(ErrorCode.LOTTERY_POSITION);
            }
            int ratio = DecimalUtil.yuanToCent(request.getRatio().doubleValue());
            LotteryPrizeConfig config = new LotteryPrizeConfig();
            config.setStartRange(weight);
            config.setEndRange(weight + ratio);
            config.setPrizeType(request.getPrizeType());
            config.setPrizeId(prizeIds.get(request.getPrizeIndex()));
            config.setLocation(request.getLocation());
            config.setLotteryId(lotteryId);
            config.setWeight(ratio);
            config.setMerchantId(SecurityHolder.getMerchantId());
            lotteryPrizeConfigMapper.insert(config);
            weight += ratio;
        }
    }

    @Override
    public void update(Long lotteryId, List<LotteryPrizeConfigRequest> positionList, List<Long> prizeIds) {
        LambdaUpdateWrapper<LotteryPrizeConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LotteryPrizeConfig::getLotteryId, lotteryId);
        lotteryPrizeConfigMapper.delete(wrapper);
        this.insert(lotteryId, positionList, prizeIds);
    }
}
