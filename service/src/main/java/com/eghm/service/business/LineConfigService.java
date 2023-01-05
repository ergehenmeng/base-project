package com.eghm.service.business;

import com.eghm.model.LineConfig;
import com.eghm.model.dto.business.line.config.LineConfigOneRequest;
import com.eghm.model.dto.business.line.config.LineConfigQueryRequest;
import com.eghm.model.dto.business.line.config.LineConfigRequest;
import com.eghm.model.vo.business.line.config.LineConfigResponse;
import com.eghm.model.vo.business.line.config.LineConfigVO;

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
     * 设置某一天的线路价格
     * @param request 价格信息
     */
    void setDay(LineConfigOneRequest request);

    /**
     * 获取线路价格配置信息
     * @param request 月份
     * @return 列表
     */
    List<LineConfigResponse> getMonthList(LineConfigQueryRequest request);


    /**
     * 查询线路的价格列表, 从当前日期开始
     * @param lineId 线路id
     * @return 列表
     */
    List<LineConfigVO> getPriceList(Long lineId);

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

    /**
     * 从某一段时间开始查询最低的线路价格, 注意不含库存为0的价格
     * @param lineId 线路id
     * @param startDate 开始日期
     * @return 最低价 单位:分
     */
    Integer getMinPrice(Long lineId, LocalDate startDate);
}
