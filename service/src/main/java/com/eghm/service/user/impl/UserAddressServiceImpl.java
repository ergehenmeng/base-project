package com.eghm.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.exception.ParameterException;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.UserAddressMapper;
import com.eghm.model.SysArea;
import com.eghm.model.UserAddress;
import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.model.dto.address.AddressEditDTO;
import com.eghm.model.vo.user.AddressVO;
import com.eghm.service.sys.SysAreaService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.user.UserAddressService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@Service("userAddressService")
@Slf4j
@AllArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressMapper userAddressMapper;

    private final SysAreaService sysAreaService;

    private final SysConfigApi sysConfigApi;

    @Override
    public void addUserAddress(AddressAddDTO dto) {
        this.checkMaxAddress(dto.getUserId());
        UserAddress address = DataUtil.copy(dto, UserAddress.class);
        this.fillAreaName(address);
        userAddressMapper.insert(address);
    }

    @Override
    public void setDefault(Long id, Long userId) {
        UserAddress userAddress = userAddressMapper.selectById(id);
        if (userAddress == null || !userAddress.getUserId().equals(userId)) {
            log.warn("设置默认地址非当前用户 id:[{}], userId:[{}]", id, userId);
            throw new BusinessException(ErrorCode.USER_ADDRESS_NULL);
        }
        // 将所有先更新为普通地址,再更新当前地址未默认地址
        userAddressMapper.updateState(userId, UserAddress.STATE_COMMON);
        userAddress.setState(UserAddress.STATE_DEFAULT);
        userAddressMapper.updateById(userAddress);
    }

    @Override
    public void deleteAddress(Long id, Long userId) {
        LambdaUpdateWrapper<UserAddress> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(UserAddress::getId, id);
        wrapper.eq(UserAddress::getUserId, userId);
        userAddressMapper.delete(wrapper);
    }

    @Override
    public void updateAddress(AddressEditDTO dto) {
        // 表示当前地址为默认地址,需要将其他设置为非默认地址
        if (Boolean.TRUE.equals(dto.getState())) {
            userAddressMapper.updateState(dto.getUserId(), UserAddress.STATE_COMMON);
        }
        UserAddress address = DataUtil.copy(dto, UserAddress.class);
        userAddressMapper.updateByUserId(address);
    }

    @Override
    public List<AddressVO> getByUserId(Long userId) {
        List<UserAddress> addressList = userAddressMapper.getByUserId(userId);
        return DataUtil.copy(addressList, address -> DataUtil.copy(address, AddressVO.class));
    }

    /**
     * 校验用户录入的最大收货地址数
     * @param userId 用户id
     */
    private void checkMaxAddress(Long userId) {
        LambdaQueryWrapper<UserAddress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserAddress::getUserId, userId);
        Integer count = userAddressMapper.selectCount(wrapper);
        int max = sysConfigApi.getInt(ConfigConstant.USER_ADDRESS_MAX);
        if (count >= max) {
            log.error("收货地址添加数量上限 [{}] [{}] [{}]", userId, max, count);
            throw new BusinessException(ErrorCode.ADDRESS_MAX);
        }
    }

    /**
     * 填充省市县地址名称
     * @param address 地址
     */
    private void fillAreaName(UserAddress address) {
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
