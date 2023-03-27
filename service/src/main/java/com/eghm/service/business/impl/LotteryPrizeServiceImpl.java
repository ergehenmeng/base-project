package com.eghm.service.business.impl;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.mapper.LotteryPrizeMapper;
import com.eghm.model.LotteryPrize;
import com.eghm.model.dto.business.lottery.LotteryPrizeRequest;
import com.eghm.service.business.LotteryPrizeService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 奖品信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Service
@AllArgsConstructor
public class LotteryPrizeServiceImpl implements LotteryPrizeService {

    private final LotteryPrizeMapper lotteryPrizeMapper;
    
    @Override
    public List<Long> insert(Long lotteryId, List<LotteryPrizeRequest> prizeList) {
        List<Long> prizeIds = new ArrayList<>(12);
        for (LotteryPrizeRequest request : prizeList) {
            LotteryPrize prize = DataUtil.copy(request, LotteryPrize.class);
            prize.setLotteryId(lotteryId);
            prize.setMerchantId(SecurityHolder.getMerchantId());
            lotteryPrizeMapper.insert(prize);
            prizeIds.add(prize.getId());
        }
        return prizeIds;
    }
}
