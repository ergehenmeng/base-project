package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.lottery.LotteryLotQueryRequest;
import com.eghm.dto.business.lottery.LotteryQueryDTO;
import com.eghm.model.LotteryLog;
import com.eghm.vo.business.lottery.LotteryLogResponse;
import com.eghm.vo.business.lottery.LotteryLogVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 抽奖记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-04-03
 */
public interface LotteryLogMapper extends BaseMapper<LotteryLog> {

    /**
     * 抽奖记录(移动端)
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 分页数据
     */
    Page<LotteryLogVO> listPage(Page<LotteryLogVO> page, @Param("param") LotteryQueryDTO dto);

    /**
     * 抽奖记录(管理后台)
     *
     * @param page 分页信息
     * @param request  查询条件
     * @return 分页数据
     */
    Page<LotteryLogResponse> getByPage(Page<LotteryLogResponse> page, @Param("param") LotteryLotQueryRequest request);
}
