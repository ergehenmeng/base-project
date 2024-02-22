package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.lottery.LotteryQueryRequest;
import com.eghm.model.Lottery;
import com.eghm.vo.business.lottery.LotteryResponse;
import com.eghm.vo.business.lottery.LotteryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 抽奖活动表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryMapper extends BaseMapper<Lottery> {

    /**
     * 查询列表
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<LotteryResponse> getByPage(Page<LotteryResponse> page, @Param("param") LotteryQueryRequest request);

    /**
     * 获取列表
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     * @return 抽奖列表
     */
    List<LotteryVO> getList(@Param("merchantId") Long merchantId, @Param("storeId") Long storeId);
}
