package com.eghm.service.business;

import com.eghm.dto.business.order.item.ItemExpressRequest;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.model.ItemExpress;
import com.eghm.vo.business.order.item.FirstExpressVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
public interface ItemExpressService {

    /**
     * 插入订单物流信息
     *
     * @param request request
     */
    void insert(ItemSippingRequest request);

    /**
     * 更新物流单号
     *
     * @param request 物流信息
     */
    void update(ItemExpressRequest request);

    /**
     * 获取订单物流信息
     *
     * @param orderNo orderNo
     * @return 物流信息
     */
    List<FirstExpressVO> getFirstExpressList(String orderNo);

    /**
     * 快递查询
     *
     * @param id id
     * @return 物流信息
     */
    ItemExpress selectById(Long id);
}
