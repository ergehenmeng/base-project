package com.eghm.service.business;

import com.eghm.model.dto.business.specialty.SpecialtyShopAddRequest;
import com.eghm.model.dto.business.specialty.SpecialtyShopEditRequest;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
public interface SpecialtyShopService {

    /**
     * 创建特产店铺
     * @param request 店铺信息
     */
    void create(SpecialtyShopAddRequest request);

    /**
     * 更新特产店铺
     * @param request 店铺信息
     */
    void update(SpecialtyShopEditRequest request);
}
