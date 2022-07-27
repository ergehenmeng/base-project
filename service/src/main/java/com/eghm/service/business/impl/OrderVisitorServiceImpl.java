package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.dao.mapper.OrderVisitorMapper;
import com.eghm.dao.model.OrderVisitor;
import com.eghm.model.dto.business.order.VisitorVO;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Service("orderVisitorService")
@AllArgsConstructor
@Slf4j
public class OrderVisitorServiceImpl implements OrderVisitorService {

    private final OrderVisitorMapper orderVisitorMapper;

    @Override
    public void addVisitor(ProductType productType, Long orderId, List<VisitorVO> voList) {
        if (CollUtil.isEmpty(voList)) {
            log.info("该订单没有游客信息 [{}] [{}]", orderId, productType);
            return;
        }
        for (VisitorVO vo : voList) {
            OrderVisitor visitor = DataUtil.copy(vo, OrderVisitor.class);
            visitor.setOrderId(orderId);
            visitor.setProductType(productType);
            orderVisitorMapper.insert(visitor);
        }
    }
}
