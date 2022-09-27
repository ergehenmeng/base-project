package com.eghm.service.business;

import com.eghm.model.LineDayConfig;
import com.eghm.model.dto.business.line.LineDayConfigRequest;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
public interface LineDayConfigService {

    /**
     * 新增或编辑线路日配置信息
     * @param lineId 线路id
     * @param configList 配置信息
     */
    void insertOrUpdate(Long lineId, List<LineDayConfigRequest> configList);

    /**
     * 查询线路的每日行程配置信息
     * @param lineId 线路id
     * @return 行程配置
     */
    List<LineDayConfig> getByLineId(Long lineId);
}
