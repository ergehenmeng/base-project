package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.SysDict;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.handler.OrderExpireHandler;
import com.eghm.service.business.handler.PayNotifyHandler;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.SpringContextUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<String> parseTags(List<SysDict> dictList, String tagIds) {
        List<String> tagList = Lists.newArrayListWithCapacity(4);
        if (CollUtil.isEmpty(dictList)) {
            log.error("数据字典为空,不做解析 [{}]", tagIds);
            return tagList;
        }

        String[] split = tagIds.split(",");
        for (String tagId : split) {
            dictList.stream().filter(sysDict -> sysDict.getHiddenValue() == Byte.parseByte(tagId))
                    .map(SysDict::getShowValue)
                    .findFirst().ifPresent(tagList::add);
        }
        return tagList;
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
