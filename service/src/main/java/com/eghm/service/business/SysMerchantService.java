package com.eghm.service.business;

import com.eghm.model.dto.merchant.MerchantAddRequest;
import com.eghm.model.dto.merchant.MerchantEditRequest;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
public interface SysMerchantService {
    
    /**
     * 创建系统商户账号
     * @param request  商户信息
     */
    void create(MerchantAddRequest request);
    
    /**
     * 更细商户信息
     * @param request 账户信息
     */
    void update(MerchantEditRequest request);
}
