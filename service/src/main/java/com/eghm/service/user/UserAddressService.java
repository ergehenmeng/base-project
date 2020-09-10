package com.eghm.service.user;

import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.model.dto.address.AddressEditDTO;
import com.eghm.model.vo.user.AddressVO;

import java.util.List;

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

    /**
     * 设置默认收货地址
     * @param id  收货地址id
     * @param userId 用户id
     */
    void setDefault(Integer id, Integer userId);

    /**
     * 删除地址
     * @param id id
     * @param userId userId
     */
    void deleteAddress(Integer id, Integer userId);

    /**
     * 更新收货地址
     * @param dto dto
     */
    void updateAddress(AddressEditDTO dto);
    /**
     * 用户地址列表
     * @param userId userId
     * @return 列表
     */
    List<AddressVO> getByUserId(Integer userId);
}
