package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.LotteryPrize;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 奖品信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryPrizeMapper extends BaseMapper<LotteryPrize> {

    /**
     * 更新抽奖中奖次数
     *
     * @param id id
     * @return 1
     */
    int accumulationLotteryNum(@Param("id") Long id);
}
