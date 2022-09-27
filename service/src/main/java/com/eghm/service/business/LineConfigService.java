package com.eghm.service.business;

import com.eghm.model.LineConfig;
import com.eghm.model.dto.business.line.config.LineConfigQueryRequest;
import com.eghm.model.dto.business.line.config.LineConfigRequest;
import com.eghm.model.vo.business.line.config.LineConfigResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
public interface LineConfigService {

    /**
     * 设置线路日态价格
     * @param request 价格信息
     */
    void setup(LineConfigRequest request);

    /**
     * 获取线路价格配置信息
     * @param request 月份
     * @return 列表
     */
    List<LineConfigResponse> getList(LineConfigQueryRequest request);

    /**
     * 查询某一天线路的价格
     * @param lineId 线路id
     * @param localDate 日期
     * @return 价格配置
     */
    LineConfig getConfig(Long lineId, LocalDate localDate);
    
    /**
     * 更新库存信息
     * @param id id
     * @param num 正数+库存 负数-库存
     */
    void updateStock(Long id, Integer num);
}
