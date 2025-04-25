package com.eghm.service.business;

import com.eghm.dto.business.line.LineConfigDayRequest;
import com.eghm.model.LineConfigDay;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/26
 */
public interface LineConfigDayService {

    /**
     * 新增或编辑线路日配置信息
     *
     * @param lineId     线路id
     * @param configList 配置信息
     */
    void insertOrUpdate(Long lineId, List<LineConfigDayRequest> configList);

    /**
     * 查询线路的每日行程配置信息
     *
     * @param lineId 线路id
     * @return 行程配置
     */
    List<LineConfigDay> getByLineId(Long lineId);
}
