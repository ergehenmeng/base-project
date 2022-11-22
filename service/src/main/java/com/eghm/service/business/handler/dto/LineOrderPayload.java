package com.eghm.service.business.handler.dto;

import com.eghm.model.Line;
import com.eghm.model.LineConfig;
import com.eghm.model.LineDayConfig;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/2
 */
@Data
public class LineOrderPayload {

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
