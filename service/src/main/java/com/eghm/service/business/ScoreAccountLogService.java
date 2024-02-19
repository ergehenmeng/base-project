package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.score.ScoreAccountQueryRequest;
import com.eghm.vo.business.account.ScoreAccountLogResponse;

import java.util.List;

/**
 * <p>
 * 商户积分变动明细表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
public interface ScoreAccountLogService {

    /**
     * 分页查询积分记录
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<ScoreAccountLogResponse> listPage(ScoreAccountQueryRequest request);

    /**
     * 查询积分记录(不分页)
     *
     * @param request 列表
     * @return 列表
     */
    List<ScoreAccountLogResponse> getList(ScoreAccountQueryRequest request);
}
