package com.eghm.service.business.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.handler.OrderExpireHandler;
import com.eghm.service.business.handler.PayNotifyHandler;
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
    public PayNotifyHandler getPayHandler(String orderNo) {
        return getHandlerBean(orderNo, PayNotifyHandler.class);
    }

    @Override
    public OrderExpireHandler getExpireHandler(String orderNo) {
        return getHandlerBean(orderNo, OrderExpireHandler.class);
    }

    /**
     * 根据订单前缀查询处理的bean
     * @param orderNo 订单编号
     * @param cls 处理类
     * @param <T> Type
     * @return bean
     */
    private static <T> T getHandlerBean(String orderNo, Class<T> cls) {
        String beanName = Arrays.stream(ProductType.values())
                .filter(productType -> orderNo.startsWith(productType.getPrefix()))
                .map(ProductType::getPayNotifyBean)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("该订单类型不匹配 [{}]", orderNo);
                    return new BusinessException(ErrorCode.ORDER_TYPE_MATCH);
                });
        return SpringContextUtil.getBean(beanName, cls);
    }
}
