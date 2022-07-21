package com.eghm.service.business;

import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.dto.business.product.ProductAddRequest;
import com.eghm.model.dto.business.product.ProductEditRequest;

/**
 * @author wyb
 * @date 2022/7/1 18:18
 */
public interface ProductService {

    /**
     * 增加商品信息
     * @param request 商品信息
     */
    void create(ProductAddRequest request);

    /**
     * 更新商品信息
     * @param request 特产商品
     */
    void update(ProductEditRequest request);

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
