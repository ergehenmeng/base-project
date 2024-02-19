package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.StoreScope;
import com.eghm.mapper.RedeemCodeScopeMapper;
import com.eghm.model.RedeemCodeScope;
import com.eghm.service.business.RedeemCodeScopeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Slf4j
@Service("redeemCodeScopeService")
@AllArgsConstructor
public class RedeemCodeScopeServiceImpl implements RedeemCodeScopeService {

    private final RedeemCodeScopeMapper redeemCodeScopeMapper;

    @Override
    public void insertOrUpdate(Long redeemCodeId, List<StoreScope> scopeList) {
        LambdaUpdateWrapper<RedeemCodeScope> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(RedeemCodeScope::getRedeemCodeId, redeemCodeId);
        redeemCodeScopeMapper.delete(wrapper);
        if (CollUtil.isNotEmpty(scopeList)) {
            scopeList.forEach(scope -> {
                RedeemCodeScope redeemCodeScope = new RedeemCodeScope();
                redeemCodeScope.setRedeemCodeId(redeemCodeId);
                redeemCodeScope.setStoreId(scope.getStoreId());
                redeemCodeScope.setProductType(scope.getProductType());
                redeemCodeScopeMapper.insert(redeemCodeScope);
            });
        }
    }

    @Override
    public List<StoreScope> getScopeList(Long redeemCodeId) {
        return redeemCodeScopeMapper.getScopeList(redeemCodeId);
    }
}
