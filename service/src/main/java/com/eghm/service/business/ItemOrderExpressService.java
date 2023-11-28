package com.eghm.service.business;

import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.vo.business.order.item.FirstExpressVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
public interface ItemOrderExpressService {

    /**
     * 插入订单物流信息
     *
     * @param request request
     */
    void insert(ItemSippingRequest request);

    /**
     * 获取订单物流信息
     *
     * @param orderNo orderNo
     * @return 物流信息
     */
    List<FirstExpressVO> getFirstExpressList(String orderNo);
}
