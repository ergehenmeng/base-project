package com.eghm.service.business;

import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
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
