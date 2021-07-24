package com.eghm.service.pay;

import com.eghm.service.pay.enums.MerchantType;
import com.eghm.dao.model.AppletPayConfig;

public interface AppletPayConfigService {

    /**
     * 根据商户code查询小程序配置消息
     * @param type type
     * @return 配置信息
     */
    AppletPayConfig getByNid(MerchantType type);
}
