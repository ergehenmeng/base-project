package com.eghm.service.business;

import com.eghm.dto.business.pay.PayConfigEditRequest;
import com.eghm.model.PayConfig;

import java.util.List;


/**
* @author 二哥很猛
* @since 2024-04-15
*/
public interface PayConfigService {

    /**
     * 获取支付配置列表
     *
     * @return 配置信息
     */
    List<PayConfig> getList(String queryName);

    /**
    * 更新
    *
    * @param request 商品信息
    */
    void update(PayConfigEditRequest request);

}