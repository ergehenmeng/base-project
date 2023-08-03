package com.eghm.service.business.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CacheConstant;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.mapper.LotteryPrizeMapper;
import com.eghm.model.LotteryPrize;
import com.eghm.dto.business.lottery.LotteryPrizeRequest;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
@Slf4j
public class LotteryPrizeServiceImpl implements LotteryPrizeService {

    private final LotteryPrizeMapper lotteryPrizeMapper;

    private final RedissonClient redissonClient;

    @Override
    public List<LotteryPrize> insert(Long lotteryId, List<LotteryPrizeRequest> prizeList) {
        List<LotteryPrize> prizeIds = new ArrayList<>(12);
        for (LotteryPrizeRequest request : prizeList) {
            LotteryPrize prize = DataUtil.copy(request, LotteryPrize.class);
            prize.setLotteryId(lotteryId);
            prize.setMerchantId(SecurityHolder.getMerchantId());
            lotteryPrizeMapper.insert(prize);
            this.setSemaphore(prize);
            prizeIds.add(prize);
        }
        return prizeIds;
    }

    @Override
    public List<LotteryPrize> update(Long lotteryId, List<LotteryPrizeRequest> prizeList) {
        LambdaQueryWrapper<LotteryPrize> wrapper = Wrappers.lambdaQuery();
        wrapper.select(LotteryPrize::getId);
        wrapper.eq(LotteryPrize::getLotteryId, lotteryId);
        List<LotteryPrize> selectList = lotteryPrizeMapper.selectList(wrapper);
        List<Long> prizeIds = selectList.stream().map(LotteryPrize::getId).collect(Collectors.toList());
        this.clearSemaphore(prizeIds);
        lotteryPrizeMapper.deleteBatchIds(prizeIds);
        return this.insert(lotteryId, prizeList);
    }

    @Override
    public List<LotteryPrize> getList(Long lotteryId) {
        LambdaQueryWrapper<LotteryPrize> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LotteryPrize::getLotteryId, lotteryId);
        return lotteryPrizeMapper.selectList(wrapper);
    }

    @Override
    public LotteryPrize selectById(Long id) {
        return lotteryPrizeMapper.selectById(id);
    }

    /**
     * 设置奖品数量新号量
     * @param prize 奖品信息
     */
    private void setSemaphore(LotteryPrize prize) {
        RSemaphore semaphore = redissonClient.getSemaphore(CacheConstant.LOTTERY_PRIZE_NUM + prize.getId());
        semaphore.trySetPermits(prize.getTotalNum());
    }

    /**
     * 删除奖品数量信号量
     * @param prizeIds 奖品id
     */
    private void clearSemaphore(List<Long> prizeIds) {
        for (Long prizeId : prizeIds) {
            boolean delete = redissonClient.getSemaphore(CacheConstant.LOTTERY_PRIZE_NUM + prizeId).delete();
            if (!delete) {
                log.error("删除奖品数量信号量失败 [{}]", prizeId);
            }
        }
    }
}
