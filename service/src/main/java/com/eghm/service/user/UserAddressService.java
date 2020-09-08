package com.eghm.service.user;

import com.eghm.model.dto.address.AddressAddDTO;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
public interface UserAddressService {

    /**
     * 添加用户地址
     * @param dto dto
     */
    void addUserAddress(AddressAddDTO dto);

}
