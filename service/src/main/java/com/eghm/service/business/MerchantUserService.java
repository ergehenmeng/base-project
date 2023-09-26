package com.eghm.service.business;

import com.eghm.dto.business.merchant.MerchantUserAddRequest;
import com.eghm.dto.business.merchant.MerchantUserEditRequest;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
public interface MerchantUserService {

    /**
     * 创建商户用户
     * @param request 用户信息
     */
    void create(MerchantUserAddRequest request);

    /**
     * 更新商户用户信息
     * @param request 用户信息
     */
    void update(MerchantUserEditRequest request);
}
