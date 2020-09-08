package com.eghm.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dao.mapper.UserAddressMapper;
import com.eghm.dao.model.UserAddress;
import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.service.user.UserAddressService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

    private UserAddressMapper userAddressMapper;

    @Autowired
    public void setUserAddressMapper(UserAddressMapper userAddressMapper) {
        this.userAddressMapper = userAddressMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addUserAddress(AddressAddDTO dto) {
        List<UserAddress> addressList = userAddressMapper.getByUserId(dto.getUserId());
        UserAddress address = DataUtil.copy(dto, UserAddress.class);
        if (CollUtil.isEmpty(addressList)) {
            address.setState(UserAddress.STATE_DEFAULT);
        } else {
            address.setState(UserAddress.STATE_COMMON);
        }
        userAddressMapper.insertSelective(address);
    }
}
