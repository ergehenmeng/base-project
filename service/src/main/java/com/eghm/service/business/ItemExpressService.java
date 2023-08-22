package com.eghm.service.business;

import com.eghm.dto.business.item.express.ItemExpressAddRequest;

/**
 * <p>
 * 快递模板表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
public interface ItemExpressService {

    /**
     * 新增快递模板
     * @param request 模板信息
     */
    void create(ItemExpressAddRequest request);
}
