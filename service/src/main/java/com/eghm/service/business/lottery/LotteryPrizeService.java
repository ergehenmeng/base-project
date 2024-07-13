package com.eghm.service.business.lottery;

import com.eghm.dto.business.lottery.LotteryPrizeRequest;
import com.eghm.model.LotteryPrize;
import com.eghm.vo.business.lottery.LotteryPrizeVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 奖品信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryPrizeService {

    /**
     * 添加抽奖活动奖品信息
     *
     * @param lotteryId 活动id
     * @param prizeList 奖品信息
     * @return 奖品信息
     */
    Map<Integer, LotteryPrize> insert(Long lotteryId, List<LotteryPrizeRequest> prizeList);

    /**
     * 更新抽奖活动奖品信息
     *
     * @param lotteryId 活动id
     * @param prizeList 奖品信息
     * @return 奖品信息
     */
    Map<Integer, LotteryPrize> update(Long lotteryId, List<LotteryPrizeRequest> prizeList);

    /**
     * 查询奖品信息
     *
     * @param lotteryId 配置id
     * @return 奖品配置列表
     */
    List<LotteryPrize> getList(Long lotteryId);

    /**
     * 主键查询奖品信息
     *
     * @param id id
     * @return 奖品信息
     */
    LotteryPrize selectById(Long id);

    /**
     * 查询奖品信息
     *
     * @param lotteryId 配置id
     * @return 奖品配置列表
     */
    List<LotteryPrizeVO> getPrizeList(Long lotteryId);

    /**
     * 更新抽奖中奖次数
     *
     * @param id id
     */
    void accumulationLotteryNum(Long id);

    /**
     * 删除奖品信息
     *
     * @param lotteryId 活动id
     * @param merchantId 商户id
     */
    void delete(Long lotteryId, Long merchantId);
}
