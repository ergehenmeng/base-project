package com.eghm.service.business.impl;

import com.eghm.dao.mapper.ShippingAddressMapper;
import com.eghm.dao.model.ShippingAddress;
import com.eghm.service.business.ShippingAddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/6
 */
@Service("shippingAddressService")
@AllArgsConstructor
@Slf4j
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressMapper shippingAddressMapper;

    @Override
    public void insert(ShippingAddress address) {
        shippingAddressMapper.insert(address);
    }
}
