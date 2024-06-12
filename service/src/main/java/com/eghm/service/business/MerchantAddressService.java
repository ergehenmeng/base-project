package com.eghm.service.business;

import com.eghm.dto.business.merchant.address.AddressAddRequest;
import com.eghm.dto.business.merchant.address.AddressEditRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.model.MerchantAddress;
import com.eghm.vo.business.merchant.address.MerchantAddressResponse;
import com.eghm.vo.business.merchant.address.MerchantAddressVO;

import java.util.List;

/**
 * <p>
 * 商户收货地址表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-18
 */
public interface MerchantAddressService {

    /**
     * 分页查询商户收货地址
     *
     * @param query 分页信息
     * @return 地址信息
     */
    PageData<MerchantAddressResponse> getByPage(PagingQuery query);

    /**
     * 获取商户收货地址(全部)
     *
     * @param merchantId 商户id
     * @return 收货地址
     */
    List<MerchantAddressResponse> getList(Long merchantId);

    /**
     * 添加收货地址
     *
     * @param request 地址信息
     */
    void create(AddressAddRequest request);

    /**
     * 更新收货地址
     *
     * @param request 地址信息
     */
    void update(AddressEditRequest request);

    /**
     * 删除地址
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 根据id查询
     *
     * @param id id
     * @return 地址
     */
    MerchantAddress selectByIdRequired(Long id);

    /**
     * 获取商户店铺收货地址
     *
     * @param storeId 店铺id
     * @return 收货地址
     */
    MerchantAddressVO getAddress(Long storeId);
}
