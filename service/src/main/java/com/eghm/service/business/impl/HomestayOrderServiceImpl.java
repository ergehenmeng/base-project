package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.mapper.HomestayOrderMapper;
import com.eghm.model.HomestayOrder;
import com.eghm.service.business.HomestayOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Slf4j
@Service("homestayOrderService")
@AllArgsConstructor
public class HomestayOrderServiceImpl implements HomestayOrderService {

    private final HomestayOrderMapper homestayOrderMapper;

    @Override
    public void insert(HomestayOrder order) {
        homestayOrderMapper.insert(order);
    }

    @Override
    public HomestayOrder selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<HomestayOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return homestayOrderMapper.selectOne(wrapper);
    }
}
