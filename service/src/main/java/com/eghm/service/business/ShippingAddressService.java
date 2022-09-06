package com.eghm.service.business;

import com.eghm.dao.model.ShippingAddress;

/**
 * @author 二哥很猛
 * @date 2022/9/6
 */
public interface ShippingAddressService {

    /**
     * 添加配送地址
     * @param address 地址信息
     */
    void insert(ShippingAddress address);
}
