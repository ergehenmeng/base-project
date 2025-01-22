package com.eghm.service.business.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.dto.business.lottery.LotteryPrizeRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.PrizeType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LotteryPrizeMapper;
import com.eghm.model.LotteryPrize;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.lottery.LotteryPrizeVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    private final RedissonClient redissonClient;

    private final LotteryPrizeMapper lotteryPrizeMapper;

    @Override
    public Map<Integer, LotteryPrize> insert(Long lotteryId, List<LotteryPrizeRequest> prizeList) {
        Map<Integer, LotteryPrize> prizeMap = Maps.newLinkedHashMapWithExpectedSize(8);
        int index = 0;
        for (LotteryPrizeRequest request : prizeList) {
            LotteryPrize prize = DataUtil.copy(request, LotteryPrize.class);
            prize.setLotteryId(lotteryId);
            prize.setMerchantId(SecurityHolder.getMerchantId());
            prize.setRelationId(request.getRelationId());
            lotteryPrizeMapper.insert(prize);
            this.setSemaphore(prize);
            prizeMap.put(index++, prize);
        }
        return prizeMap;
    }

    @Override
    public Map<Integer, LotteryPrize> update(Long lotteryId, List<LotteryPrizeRequest> prizeList) {
        this.delete(lotteryId, SecurityHolder.getMerchantId());
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

    @Override
    public List<LotteryPrizeVO> getPrizeList(Long lotteryId) {
        return lotteryPrizeMapper.getPrizeList(lotteryId);
    }

    @Override
    public void accumulationLotteryNum(Long id) {
        int lotteryNum = lotteryPrizeMapper.accumulationLotteryNum(id);
        if (lotteryNum != 1) {
            log.error("中奖奖品数量更新失败，奖品数量不足 [{}]", id);
            throw new BusinessException(ErrorCode.PRIZE_WIN_ERROR);
        }
    }

    @Override
    public void delete(Long lotteryId, Long merchantId) {
        this.releaseSemaphore(lotteryId, merchantId);
        LambdaUpdateWrapper<LotteryPrize> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LotteryPrize::getLotteryId, lotteryId);
        wrapper.eq(LotteryPrize::getMerchantId, merchantId);
        lotteryPrizeMapper.delete(wrapper);
    }

    @Override
    public void releaseSemaphore(Long lotteryId, Long merchantId) {
        LambdaQueryWrapper<LotteryPrize> wrapper = Wrappers.lambdaQuery();
        wrapper.select(LotteryPrize::getId);
        wrapper.eq(LotteryPrize::getLotteryId, lotteryId);
        wrapper.eq(merchantId != null, LotteryPrize::getMerchantId, merchantId);
        List<LotteryPrize> selectList = lotteryPrizeMapper.selectList(wrapper);
        if (selectList.isEmpty()) {
            return;
        }
        List<Long> prizeIds = selectList.stream().map(LotteryPrize::getId).toList();
        this.clearSemaphore(prizeIds);
    }

    /**
     * 设置奖品数量新号量
     *
     * @param prize 奖品信息
     */
    private void setSemaphore(LotteryPrize prize) {
        if (prize.getPrizeType() != PrizeType.NONE) {
            RSemaphore semaphore = redissonClient.getSemaphore(CacheConstant.LOTTERY_PRIZE_NUM + prize.getId());
            semaphore.trySetPermits(prize.getTotalNum());
        }
    }

    /**
     * 删除奖品数量信号量
     *
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
