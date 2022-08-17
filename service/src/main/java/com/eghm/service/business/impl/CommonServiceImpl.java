package com.eghm.service.business.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.PayOrderService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Service("commonService")
@AllArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final SysConfigApi sysConfigApi;

    @Override
    public void checkMaxDay(String configNid, long maxValue) {
        long apiLong = sysConfigApi.getLong(configNid);
        if (maxValue > apiLong) {
            log.error("设置时间间隔超过[{}]天", apiLong);
            throw new BusinessException(ErrorCode.MAX_DAY.getCode(), String.format(ErrorCode.MAX_DAY.getMsg(), apiLong));
        }
    }

    @Override
    public PayOrderService getOrderService(String orderNo) {
        String beanName = Arrays.stream(ProductType.values())
                .filter(productType -> orderNo.startsWith(productType.getPrefix()))
                .map(ProductType::getBeanName)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("该订单类型不匹配 [{}]", orderNo);
                    return new BusinessException(ErrorCode.ORDER_TYPE_MATCH);
                });
        return SpringContextUtil.getBean(beanName, PayOrderService.class);
    }
}
