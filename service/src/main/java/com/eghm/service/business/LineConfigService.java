package com.eghm.service.business;

import com.eghm.model.dto.business.line.config.LineConfigQueryRequest;
import com.eghm.model.dto.business.line.config.LineConfigRequest;
import com.eghm.model.vo.business.line.config.LineConfigResponse;

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
}
