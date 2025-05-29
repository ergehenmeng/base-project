package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.logistics.service.ExpressService;
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

    private final ExpressService expressService;

    private final ExpressLogisticsMapper expressLogisticsMapper;

    @Override
    public void insert(String expressNo, String expressCode, String phone) {
        long count = this.countByExpressNo(expressNo);
        if (count <= 0) {
            ExpressLogistics expressLogistics = new ExpressLogistics();
            expressLogistics.setExpressNo(expressNo);
            expressLogistics.setPhone(phone);
            expressLogistics.setExpressCode(expressCode);
            expressLogisticsMapper.insert(expressLogistics);
            expressService.subscribe(expressNo, expressCode, phone);
        }
    }

    @Override
    public List<ExpressLogistics> getExpress(String orderNo) {
        return expressLogisticsMapper.getExpress(orderNo);
    }

    /**
     * 查询指定快递号的数量
     *
     * @param expressNo 快递单号
     * @return 1
     */
    private long countByExpressNo(String expressNo) {
        LambdaQueryWrapper<ExpressLogistics> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ExpressLogistics::getExpressNo, expressNo);
        return expressLogisticsMapper.selectCount(wrapper);
    }

}
