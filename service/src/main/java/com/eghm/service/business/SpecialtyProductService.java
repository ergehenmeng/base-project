package com.eghm.service.business;

import com.eghm.model.dto.business.specialty.product.SpecialtyProductAddRequest;
import com.eghm.model.dto.business.specialty.product.SpecialtyProductEditRequest;

/**
 * @author wyb
 * @date 2022/7/1 18:18
 */
public interface SpecialtyProductService {

    /**
     * 增加商品信息
     * @param request 特产商品
     */
    void create(SpecialtyProductAddRequest request);

    /**
     * 更新商品信息
     * @param request 特产商品
     */
    void update(SpecialtyProductEditRequest request);
}
