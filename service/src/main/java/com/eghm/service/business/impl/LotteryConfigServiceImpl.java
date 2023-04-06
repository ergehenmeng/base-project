package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.DecimalUtil;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.mapper.LotteryConfigMapper;
import com.eghm.model.LotteryConfig;
import com.eghm.dto.business.lottery.LotteryConfigRequest;
import com.eghm.service.business.LotteryConfigService;
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
public class LotteryConfigServiceImpl implements LotteryConfigService {

    private final LotteryConfigMapper lotteryConfigMapper;
    
    @Override
    public void insert(Long lotteryId, List<LotteryConfigRequest> positionList, List<Long> prizeIds) {
        int weight = 0;
        for (int i = 0; i < positionList.size(); i++) {
            LotteryConfigRequest request = positionList.get(i);
            if (request.getLocation() != i + 1 ) {
                throw new BusinessException(ErrorCode.LOTTERY_POSITION);
            }
            int ratio = DecimalUtil.yuanToCent(request.getRatio().doubleValue());
            LotteryConfig config = new LotteryConfig();
            config.setStartRange(weight);
            config.setEndRange(weight + ratio);
            config.setPrizeType(request.getPrizeType());
            config.setPrizeId(prizeIds.get(request.getPrizeIndex()));
            config.setLocation(request.getLocation());
            config.setLotteryId(lotteryId);
            config.setWeight(ratio);
            config.setMerchantId(SecurityHolder.getMerchantId());
            lotteryConfigMapper.insert(config);
            weight += ratio;
        }
    }

    @Override
    public void update(Long lotteryId, List<LotteryConfigRequest> positionList, List<Long> prizeIds) {
        LambdaUpdateWrapper<LotteryConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LotteryConfig::getLotteryId, lotteryId);
        lotteryConfigMapper.delete(wrapper);
        this.insert(lotteryId, positionList, prizeIds);
    }

    @Override
    public List<LotteryConfig> getList(Long lotteryId) {
        LambdaUpdateWrapper<LotteryConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LotteryConfig::getLotteryId, lotteryId);
        wrapper.orderByAsc(LotteryConfig::getLocation);
        return lotteryConfigMapper.selectList(wrapper);
    }
}
