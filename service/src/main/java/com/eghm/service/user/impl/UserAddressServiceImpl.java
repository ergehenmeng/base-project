package com.eghm.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.exception.ParameterException;
import com.eghm.dao.mapper.UserAddressMapper;
import com.eghm.dao.model.SysArea;
import com.eghm.dao.model.UserAddress;
import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.model.dto.address.AddressEditDTO;
import com.eghm.model.vo.user.AddressVO;
import com.eghm.service.sys.SysAreaService;
import com.eghm.service.user.UserAddressService;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@Service("userAddressService")
@Slf4j
public class UserAddressServiceImpl implements UserAddressService {

    private UserAddressMapper userAddressMapper;

    private SysAreaService sysAreaService;

    @Autowired
    public void setSysAreaService(SysAreaService sysAreaService) {
        this.sysAreaService = sysAreaService;
    }

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
        this.fillAreaName(address);
        userAddressMapper.insertSelective(address);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void setDefault(Integer id, Integer userId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(id);
        if (userAddress == null || !userAddress.getUserId().equals(userId)) {
            log.warn("设置默认地址非当前用户 id:[{}], userId:[{}]", id, userId);
            throw new BusinessException(ErrorCode.USER_ADDRESS_NULL);
        }
        // 将所有先更新为普通地址,再更新当前地址未默认地址
        userAddressMapper.updateState(userId, UserAddress.STATE_COMMON);
        userAddress.setState(UserAddress.STATE_DEFAULT);
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteAddress(Integer id, Integer userId) {
        userAddressMapper.deleteById(id, userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateAddress(AddressEditDTO dto) {
        // 表示当前地址为默认地址,需要将其他设置为非默认地址
        if (Boolean.TRUE.equals(dto.getState())) {
            userAddressMapper.updateState(dto.getUserId(), UserAddress.STATE_COMMON);
        }
        UserAddress address = DataUtil.copy(dto, UserAddress.class);
        userAddressMapper.updateByUserId(address);
    }

    @Override
    public List<AddressVO> getByUserId(Integer userId) {
        List<UserAddress> addressList = userAddressMapper.getByUserId(userId);
        return DataUtil.convert(addressList, address -> DataUtil.copy(address, AddressVO.class));
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
