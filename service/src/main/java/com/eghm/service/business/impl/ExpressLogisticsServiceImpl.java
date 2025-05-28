package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.ExpressLogisticsMapper;
import com.eghm.model.ExpressLogistics;
import com.eghm.service.business.ExpressLogisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/5/28
 */
@Slf4j
@AllArgsConstructor
@Service("expressLogisticsService")
public class ExpressLogisticsServiceImpl implements ExpressLogisticsService {

    private final ExpressLogisticsMapper expressLogisticsMapper;

    @Override
    public void insertOrUpdate(String expressNo, String expressCode) {
        LambdaQueryWrapper<ExpressLogistics> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ExpressLogistics::getExpressNo, expressNo);
        Long count = expressLogisticsMapper.selectCount(wrapper);
        if (count <= 0) {
            ExpressLogistics expressLogistics = new ExpressLogistics();
            expressLogistics.setExpressNo(expressNo);
            expressLogistics.setExpressCode(expressCode);
            expressLogisticsMapper.insert(expressLogistics);
        }
    }

    @Override
    public List<ExpressLogistics> getExpress(String orderNo) {
        return expressLogisticsMapper.getExpress(orderNo);
    }

}
