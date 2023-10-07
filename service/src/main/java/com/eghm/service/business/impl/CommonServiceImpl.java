package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.SysDict;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.handler.state.RefundNotifyHandler;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.SpringContextUtil;
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
    public <T> T getHandler(String orderNo, Class<T> clsHandler) {
        return this.getHandler(ProductType.prefix(orderNo), clsHandler);
    }

    @Override
    public <T> T getHandler(ProductType productType, Class<T> clsHandler) {
        return SpringContextUtil.getBean(productType.getValue() + clsHandler.getSimpleName(), clsHandler);
    }

    @Override
    public RefundNotifyHandler getRefundHandler(String orderNo) {
        String prefix = ProductType.prefix(orderNo).getValue();
        return SpringContextUtil.getBean(prefix + RefundNotifyHandler.class.getSimpleName(), RefundNotifyHandler.class);
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
            dictList.stream().filter(sysDict -> sysDict.getHiddenValue() == Integer.parseInt(tagId))
                    .map(SysDict::getShowValue)
                    .findFirst().ifPresent(tagList::add);
        }
        return tagList;
    }

    @Override
    public void checkIllegal(Long merchantId) {
        if (this.checkIsIllegal(merchantId)) {
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
    }

    @Override
    public boolean checkIsIllegal(Long merchantId) {
        return this.checkIsIllegal(merchantId, SecurityHolder.getMerchantId());
    }

    @Override
    public boolean checkIsIllegal(Long merchantId, Long loginMerchantId) {
        if (loginMerchantId == null && merchantId == null) {
            return false;
        }
        if (merchantId == null || !merchantId.equals(loginMerchantId)) {
            // loginMerchantId或merchantId一定有一个是不为空或者两个都不为空的
            log.error("商户访问了非自己的数据 [{}] [{}]", loginMerchantId, merchantId);
            return true;
        }
        return false;
    }
}
