package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.model.ProductShop;
import com.eghm.model.dto.business.product.shop.ProductShopAddRequest;
import com.eghm.model.dto.business.product.shop.ProductShopEditRequest;
import com.eghm.model.dto.business.product.shop.ProductShopQueryRequest;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
public interface ProductShopService {

    /**
     * 分页查询商铺信息
     * @param request 查询条案件
     * @return 列表
     */
    Page<ProductShop> getByPage(ProductShopQueryRequest request);

    /**
     * 创建特产店铺
     * @param request 店铺信息
     */
    void create(ProductShopAddRequest request);

    /**
     * 更新特产店铺
     * @param request 店铺信息
     */
    void update(ProductShopEditRequest request);

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
