package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.pay.PayConfigEditRequest;
import com.eghm.mapper.PayConfigMapper;
import com.eghm.model.PayConfig;
import com.eghm.service.business.PayConfigService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 二哥很猛
* @since 2024-04-15
*/
@Slf4j
@Service("payConfigService")
@AllArgsConstructor
public class PayConfigServiceImpl implements PayConfigService {

    private final PayConfigMapper payConfigMapper;

    @Override
    public List<PayConfig> getList(String queryName) {
        LambdaQueryWrapper<PayConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.like(PayConfig::getRemark, queryName);
        wrapper.orderByDesc(PayConfig::getId);
        return payConfigMapper.selectList(wrapper);
    }

    @Override
    public void update(PayConfigEditRequest request) {
        PayConfig data = DataUtil.copy(request, PayConfig.class);
        payConfigMapper.updateById(data);
    }

}