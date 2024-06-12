package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.merchant.address.AddressAddRequest;
import com.eghm.dto.business.merchant.address.AddressEditRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemStoreMapper;
import com.eghm.mapper.MerchantAddressMapper;
import com.eghm.model.ItemStore;
import com.eghm.model.MerchantAddress;
import com.eghm.service.business.MerchantAddressService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.merchant.address.MerchantAddressResponse;
import com.eghm.vo.business.merchant.address.MerchantAddressVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.enums.ErrorCode.ADDRESS_OCCUPIED;

/**
 * <p>
 * 商户收货地址表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-18
 */

@Slf4j
@AllArgsConstructor
@Service("merchantAddressService")
public class MerchantAddressServiceImpl implements MerchantAddressService {

    private final MerchantAddressMapper merchantAddressMapper;

    private final ItemStoreMapper itemStoreMapper;

    private final SysAreaService sysAreaService;

    @Override
    public PageData<MerchantAddressResponse> getByPage(PagingQuery query) {
        LambdaQueryWrapper<MerchantAddress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MerchantAddress::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.like(query.getQueryName() != null, MerchantAddress::getNickName, query.getQueryName());
        wrapper.orderByDesc(MerchantAddress::getId);
        Page<MerchantAddress> page = merchantAddressMapper.selectPage(query.createPage(), wrapper);
        return DataUtil.copy(page, this::transfer);
    }

    @Override
    public List<MerchantAddressResponse> getList(Long merchantId) {
        LambdaQueryWrapper<MerchantAddress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MerchantAddress::getMerchantId, merchantId);
        wrapper.orderByDesc(MerchantAddress::getId);
        List<MerchantAddress> selectList = merchantAddressMapper.selectList(wrapper);
        return DataUtil.copy(selectList, this::transfer);
    }

    @Override
    public void create(AddressAddRequest request) {
        DataUtil.copy(request, MerchantAddress.class, merchantAddressMapper::insert);
    }

    @Override
    public void update(AddressEditRequest request) {
        DataUtil.copy(request, MerchantAddress.class, merchantAddressMapper::updateById);
    }

    @Override
    public void delete(Long id) {
        Long merchantId = SecurityHolder.getMerchantId();
        LambdaQueryWrapper<ItemStore> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemStore::getDepotAddressId, id);
        wrapper.eq(ItemStore::getMerchantId, merchantId);
        Long count = itemStoreMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("仓库收货地址被使用,无法删除 [{}]", id);
            throw new BusinessException(ADDRESS_OCCUPIED);
        }
        LambdaUpdateWrapper<MerchantAddress> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(MerchantAddress::getId, id);
        updateWrapper.eq(MerchantAddress::getMerchantId, merchantId);
        merchantAddressMapper.delete(updateWrapper);
    }

    @Override
    public MerchantAddressVO getAddress(Long storeId) {
        MerchantAddress address = merchantAddressMapper.getStoreAddress(storeId);
        if (address == null) {
            return null;
        }
        MerchantAddressVO vo = new MerchantAddressVO();
        vo.setNickName(address.getNickName());
        vo.setMobile(address.getMobile());
        vo.setDetailAddress(sysAreaService.parseArea(address.getProvinceId(), address.getCityId(), address.getCountyId(), address.getDetailAddress()));
        return vo;
    }

    /**
     * 转换,将市区详细信息拼接
     *
     * @param address 原地址
     * @return 格式化后的地址
     */
    private MerchantAddressResponse transfer(MerchantAddress address) {
        MerchantAddressResponse response = DataUtil.copy(address, MerchantAddressResponse.class);
        response.setDetailAddress(sysAreaService.parseArea(address.getCityId(), address.getCountyId(), address.getDetailAddress()));
        return response;
    }
}
