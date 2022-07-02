package com.eghm.service.business;

import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
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

    /**
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 更新审核状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateAuditState(Long id, AuditState state);
}
