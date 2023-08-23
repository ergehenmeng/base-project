package com.eghm.service.business;

import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ItemExpressAddRequest;
import com.eghm.dto.business.item.express.ItemExpressEditRequest;

import java.math.BigDecimal;

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

    /**
     * 更新快递模板
     * @param request 模板信息
     */
    void update(ItemExpressEditRequest request);

    /**
     * 计算单店铺的快递费
     * @param dto 店铺商品信息
     * @return 费用 单位:分
     */
    Integer calcFee(ExpressFeeCalcDTO dto);
}
