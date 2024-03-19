package com.eghm.mapper;

import com.eghm.model.MerchantAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.merchant.address.MerchantAddressVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户收货地址表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-18
 */
public interface MerchantAddressMapper extends BaseMapper<MerchantAddress> {

    /**
     * 获取商户店铺的收货地址 (零售)
     *
     * @param storeId 店铺id
     * @return 收货地址
     */
    MerchantAddress getStoreAddress(@Param("storeId") Long storeId);
}
