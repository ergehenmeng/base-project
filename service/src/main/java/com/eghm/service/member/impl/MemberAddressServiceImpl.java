package com.eghm.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.address.AddressAddDTO;
import com.eghm.dto.address.AddressEditDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.exception.ParameterException;
import com.eghm.mapper.MemberAddressMapper;
import com.eghm.model.MemberAddress;
import com.eghm.model.SysArea;
import com.eghm.service.member.MemberAddressService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.vo.member.AddressVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@Service("memberAddressService")
@Slf4j
@AllArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {

    private final MemberAddressMapper memberAddressMapper;

    private final SysAreaService sysAreaService;

    private final SysConfigApi sysConfigApi;

    @Override
    public void addAddress(AddressAddDTO dto) {
        this.checkMaxAddress(dto.getMemberId());
        MemberAddress address = DataUtil.copy(dto, MemberAddress.class);
        this.fillAreaName(address);
        memberAddressMapper.insert(address);
    }

    @Override
    public void setDefault(Long id, Long memberId) {
        MemberAddress memberAddress = memberAddressMapper.selectById(id);
        if (memberAddress == null || !memberAddress.getMemberId().equals(memberId)) {
            log.warn("设置默认地址非当前用户 id:[{}], memberId:[{}]", id, memberId);
            throw new BusinessException(ErrorCode.MEMBER_ADDRESS_NULL);
        }
        // 将所有先更新为普通地址,再更新当前地址未默认地址
        memberAddressMapper.updateState(memberId, MemberAddress.STATE_COMMON);
        memberAddress.setState(MemberAddress.STATE_DEFAULT);
        memberAddressMapper.updateById(memberAddress);
    }

    @Override
    public void deleteAddress(Long id, Long memberId) {
        LambdaUpdateWrapper<MemberAddress> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MemberAddress::getId, id);
        wrapper.eq(MemberAddress::getMemberId, memberId);
        memberAddressMapper.delete(wrapper);
    }

    @Override
    public void updateAddress(AddressEditDTO dto) {
        // 表示当前地址为默认地址,需要将其他设置为非默认地址
        if (Boolean.TRUE.equals(dto.getState())) {
            memberAddressMapper.updateState(dto.getMemberId(), MemberAddress.STATE_COMMON);
        }
        MemberAddress address = DataUtil.copy(dto, MemberAddress.class);
        memberAddressMapper.updateByMemberId(address);
    }

    @Override
    public List<AddressVO> getByMemberId(Long memberId) {
        List<MemberAddress> addressList = memberAddressMapper.getByMemberId(memberId);
        return DataUtil.copy(addressList, address -> DataUtil.copy(address, AddressVO.class));
    }

    /**
     * 校验用户录入的最大收货地址数
     * @param memberId 用户id
     */
    private void checkMaxAddress(Long memberId) {
        LambdaQueryWrapper<MemberAddress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberAddress::getMemberId, memberId);
        Long count = memberAddressMapper.selectCount(wrapper);
        int max = sysConfigApi.getInt(ConfigConstant.MEMBER_ADDRESS_MAX);
        if (count >= max) {
            log.error("收货地址添加数量上限 [{}] [{}] [{}]", memberId, max, count);
            throw new BusinessException(ErrorCode.ADDRESS_MAX);
        }
    }

    /**
     * 填充省市县地址名称
     * @param address 地址
     */
    private void fillAreaName(MemberAddress address) {
        SysArea sysArea = sysAreaService.getById(address.getProvinceId());
        if (sysArea == null || sysArea.getClassify() != SysArea.CLASSIFY_PROVINCE) {
            throw new ParameterException(ErrorCode.PROVINCE_ERROR);
        }
        address.setProvinceName(sysArea.getTitle());
        sysArea = sysAreaService.getById(address.getCityId());
        if (sysArea == null || sysArea.getClassify() != SysArea.CLASSIFY_CITY) {
            throw new ParameterException(ErrorCode.CITY_ERROR);
        }
        address.setCityName(sysArea.getTitle());
        sysArea = sysAreaService.getById(address.getCountyId());
        if (sysArea == null || sysArea.getClassify() != SysArea.CLASSIFY_COUNTY) {
            throw new ParameterException(ErrorCode.COUNTY_ERROR);
        }
        address.setCityName(sysArea.getTitle());
    }

}
