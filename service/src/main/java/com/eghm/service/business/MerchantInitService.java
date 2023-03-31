package com.eghm.service.business;

import com.eghm.enums.ref.RoleType;
import com.eghm.model.Merchant;

import java.util.List;

/**
 * @author 殿小二
 * @date 2023/2/20
 */
public interface MerchantInitService {
    
    /**
     * 是否支持该角色类型的店铺初始化
     * @param roleTypes 角色类型
     * @return true: 可以初始化店铺, false:不可以初始化
     */
    default boolean support(List<RoleType> roleTypes) {
        return false;
    }
    
    /**
     * 根据商户初始化店铺信息
     * @param merchant 商户信息
     */
    void init(Merchant merchant);
}
