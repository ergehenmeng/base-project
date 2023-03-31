package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.model.SysDict;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.handler.PayNotifyHandler;
import com.eghm.service.business.handler.impl.DefaultPayNotifyHandler;
import com.eghm.service.business.handler.impl.item.ItemPayNotifyHandler;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.common.utils.SpringContextUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        if (orderNo.startsWith(ProductType.ITEM.getPrefix())) {
            return SpringContextUtil.getBean(ItemPayNotifyHandler.class);
        }
        return SpringContextUtil.getBean(DefaultPayNotifyHandler.class);
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
    
    @Override
    public void checkIllegal(Long merchantId) {
        Long loginMerchantId = SecurityHolder.getMerchantId();
        // 该接口是商家和平台共用, 商家只能查看自己的商户信息
        if (merchantId != null && !merchantId.equals(loginMerchantId)) {
            log.error("商户访问了非自己的数据 [{}] [{}]", merchantId, loginMerchantId);
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
    }

}
