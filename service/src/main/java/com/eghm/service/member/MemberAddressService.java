package com.eghm.service.member;

import com.eghm.dto.address.AddressAddDTO;
import com.eghm.dto.address.AddressEditDTO;
import com.eghm.model.MemberAddress;
import com.eghm.vo.member.AddressVO;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
public interface MemberAddressService {

    /**
     * 添加用户地址
     *
     * @param dto dto
     */
    void addAddress(AddressAddDTO dto);

    /**
     * 设置默认收货地址
     *
     * @param id       收货地址id
     * @param memberId 用户id
     */
    void setDefault(Long id, Long memberId);

    /**
     * 删除地址
     *
     * @param id       id
     * @param memberId memberId
     */
    void deleteAddress(Long id, Long memberId);

    /**
     * 更新收货地址
     *
     * @param dto dto
     */
    void updateAddress(AddressEditDTO dto);

    /**
     * 用户地址列表
     *
     * @param memberId memberId
     * @return 列表
     */
    List<AddressVO> getByMemberId(Long memberId);

    /**
     * 查询用户自己的收货地址
     *
     * @param id       收货地址id
     * @param memberId 用户id
     * @return 收货地址
     */
    MemberAddress getById(Long id, Long memberId);
}
