package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.mapper.LineOrderMapper;
import com.eghm.model.LineOrder;
import com.eghm.service.business.LineOrderService;
import com.eghm.vo.business.order.ProductSnapshotVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
@Service("lineOrderService")
@AllArgsConstructor
@Slf4j
public class LineOrderServiceImpl implements LineOrderService {

    private final LineOrderMapper lineOrderMapper;

    @Override
    public void insert(LineOrder order) {
        lineOrderMapper.insert(order);
    }

    @Override
    public LineOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<LineOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LineOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return lineOrderMapper.selectOne(wrapper);
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        return lineOrderMapper.getSnapshot(orderId, orderNo);
    }
}
