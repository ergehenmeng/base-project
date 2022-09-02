package com.eghm.service.business.handler.dto;

import com.eghm.dao.model.Line;
import com.eghm.dao.model.LineConfig;
import com.eghm.dao.model.LineDayConfig;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/2
 */
@Data
public class LineOrderDTO {

    /**
     * 线路商品信息
     */
    private Line line;

    /**
     * 线路价格配置信息
     */
    private LineConfig config;

    /**
     * 线路每日行程配置
     */
    private List<LineDayConfig> dayList;
}
