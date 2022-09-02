package com.eghm.service.business;

import com.eghm.dao.model.LineDayConfig;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
public interface LineDaySnapshotService {

    /**
     * 插入线路每日线路配置快照信息
     * @param lineId 线路id
     * @param orderNo 订单编号
     * @param configList 每日线路配置信息
     */
    void insert(Long lineId, String orderNo, List<LineDayConfig>configList);
}
