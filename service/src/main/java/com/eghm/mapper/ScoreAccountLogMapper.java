package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.score.ScoreAccountQueryRequest;
import com.eghm.model.ScoreAccountLog;
import com.eghm.vo.business.account.ScoreAccountLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户积分变动明细表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
public interface ScoreAccountLogMapper extends BaseMapper<ScoreAccountLog> {

    /**
     * 积分变动列表
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<ScoreAccountLogResponse> listPage(Page<ScoreAccountLogResponse> page, @Param("param") ScoreAccountQueryRequest request);
}
