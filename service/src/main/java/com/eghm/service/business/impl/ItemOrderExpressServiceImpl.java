package com.eghm.service.business.impl;

import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.mapper.ItemOrderExpressMapper;
import com.eghm.model.ItemOrderExpress;
import com.eghm.service.business.ItemOrderExpressService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.item.FirstExpressVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */

@Slf4j
@Service("itemOrderExpressService")
@AllArgsConstructor
public class ItemOrderExpressServiceImpl implements ItemOrderExpressService {

    private final ItemOrderExpressMapper itemOrderExpressMapper;

    @Override
    public void insert(ItemSippingRequest request) {
        List<Long> orderIds = request.getOrderIds();
        for (Long orderId : orderIds) {
            ItemOrderExpress express = DataUtil.copy(request, ItemOrderExpress.class);
            express.setItemOrderId(orderId);
            itemOrderExpressMapper.insert(express);
        }
    }

    @Override
    public List<FirstExpressVO> getFirstExpressList(String orderNo) {
        // TODO 待完成
        return null;
    }
}
