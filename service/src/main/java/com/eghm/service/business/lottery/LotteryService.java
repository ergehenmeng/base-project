package com.eghm.service.business.lottery;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.dto.business.lottery.LotteryQueryRequest;
import com.eghm.model.Lottery;
import com.eghm.vo.business.lottery.LotteryDetailResponse;
import com.eghm.vo.business.lottery.LotteryResponse;
import com.eghm.vo.business.lottery.LotteryResultVO;

/**
 * <p>
 * 抽奖活动表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryService {

    /**
     * 查询抽奖配置列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<LotteryResponse> getByPage(LotteryQueryRequest request);

    /**
     * 新增抽奖活动
     *
     * @param request 抽奖配置信息
     */
    void create(LotteryAddRequest request);

    /**
     * 更新抽奖活动
     *
     * @param request 信息
     */
    void update(LotteryEditRequest request);

    /**
     * 抽奖
     *
     * @param lotteryId 活动id
     * @param memberId  用户id
     */
    LotteryResultVO lottery(Long lotteryId, Long memberId);

    /**
     * 查询抽奖活动
     *
     * @param lotteryId 活动id
     * @return 抽奖信息
     */
    Lottery selectById(Long lotteryId);

    /**
     * 查询抽奖活动
     *
     * @param lotteryId 活动id
     * @return 抽奖信息
     */
    Lottery selectByIdRequired(Long lotteryId);

    /**
     * 查询抽奖详情信息
     *
     * @param lotteryId 抽奖配置ID
     * @return response
     */
    LotteryDetailResponse getDetailById(Long lotteryId);
}
