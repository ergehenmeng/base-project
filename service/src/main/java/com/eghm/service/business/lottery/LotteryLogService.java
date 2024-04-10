package com.eghm.service.business.lottery;

import com.eghm.dto.business.lottery.LotteryQueryDTO;
import com.eghm.model.LotteryLog;
import com.eghm.vo.business.lottery.LotteryLogVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wyb
 * @since 2023/4/3
 */
public interface LotteryLogService {

    /**
     * 查询抽奖记录(分页)
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<LotteryLogVO> getList(LotteryQueryDTO dto);

    /**
     * 添加抽奖日志信息
     *
     * @param lotteryLog 抽奖信息
     */
    void insert(LotteryLog lotteryLog);

    /**
     * 统计用户在某个抽奖活动的次数
     *
     * @param lotteryId 活动id
     * @param memberId  用户id
     * @return 抽奖次数
     */
    long countLottery(Long lotteryId, Long memberId);

    /**
     * 统计用户在某个抽奖活动中一段时间内的次数
     *
     * @param lotteryId 活动id
     * @param memberId  用户id
     * @param startTime 开始时间
     * @param endTime   截止时间
     * @return 抽奖次数
     */
    long countLottery(Long lotteryId, Long memberId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计用户在某个抽奖活动的中奖次数
     *
     * @param lotteryId 活动id
     * @param memberId  用户id
     * @return 中奖次数
     */
    long countLotteryWin(Long lotteryId, Long memberId);
}
