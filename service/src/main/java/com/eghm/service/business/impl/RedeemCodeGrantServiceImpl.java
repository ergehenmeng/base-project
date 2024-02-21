package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.redeem.RedeemCodeGrantQueryRequest;
import com.eghm.dto.ext.RedeemScope;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.RedeemCodeGrantMapper;
import com.eghm.mapper.RedeemCodeScopeMapper;
import com.eghm.model.RedeemCodeGrant;
import com.eghm.model.RedeemCodeScope;
import com.eghm.service.business.RedeemCodeGrantService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.vo.business.redeem.RedeemCodeGrantResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.eghm.constants.ConfigConstant.REDEEM_CODE_SCOPE;

/**
 * <p>
 * 兑换码发放表 服务实现类<br>
 * 兑换码目前只针对于: 民宿, 线路, 场馆
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Slf4j
@AllArgsConstructor
@Service("redeemCodeGrantService")
public class RedeemCodeGrantServiceImpl extends ServiceImpl<RedeemCodeGrantMapper, RedeemCodeGrant> implements RedeemCodeGrantService {

    private final SysConfigApi sysConfigApi;

    private final RedeemCodeScopeMapper redeemCodeScopeMapper;

    @Override
    public Page<RedeemCodeGrantResponse> listPage(RedeemCodeGrantQueryRequest request) {
        return baseMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<RedeemCodeGrantResponse> getList(RedeemCodeGrantQueryRequest request) {
        return baseMapper.listPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public Integer getRedeemAmount(String cdKey, Long storeId, Long productId) {
        if (StrUtil.isBlank(cdKey)) {
            return 0;
        }
        LambdaQueryWrapper<RedeemCodeGrant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RedeemCodeGrant::getCdKey, cdKey);
        wrapper.last(CommonConstant.LIMIT_ONE);
        RedeemCodeGrant selected = baseMapper.selectOne(wrapper);
        if (selected == null) {
            log.error("兑换码无效 [{}]", cdKey);
            throw new BusinessException(ErrorCode.CD_KEY_INVALID);
        }
        if (selected.getState() == 1) {
            log.error("兑换码已兑换完 [{}]", cdKey);
            throw new BusinessException(ErrorCode.CD_KEY_USED);
        }
        if (selected.getState() == 2) {
            log.error("兑换码已过期 [{}]", cdKey);
            throw new BusinessException(ErrorCode.CD_KEY_EXPIRE);
        }
        LocalDateTime now = LocalDateTime.now();
        if (selected.getStartTime().isAfter(now) || selected.getEndTime().isBefore(now)) {
            log.error("兑换码不在有效期 [{}] [{}] [{}]", cdKey, selected.getStartTime(), selected.getEndTime());
            throw new BusinessException(ErrorCode.CD_KEY_EXPIRE);
        }
        LambdaQueryWrapper<RedeemCodeScope> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RedeemCodeScope::getRedeemCodeId, selected.getRedeemCodeId());
        queryWrapper.eq(RedeemCodeScope::getStoreId, storeId);
        Long count = redeemCodeScopeMapper.selectCount(queryWrapper);
        if (count <= 0) {
            List<RedeemScope> scopeList = sysConfigApi.getListClass(REDEEM_CODE_SCOPE, RedeemScope.class);
            if (CollUtil.isEmpty(scopeList)) {
                log.error("兑换码不适用该店铺 [{}] [{}]", cdKey, selected.getRedeemCodeId());
                throw new BusinessException(ErrorCode.CD_KEY_STORE_SCOPE);
            }
            Map<Long, List<Long>> scopeMap = scopeList.stream().collect(Collectors.toMap(RedeemScope::getRedeemCodeId, RedeemScope::getProductIds));
            List<Long> productList = scopeMap.get(selected.getRedeemCodeId());
            if (CollUtil.isEmpty(productList) || !productList.contains(productId)) {
                log.error("兑换码不适用该商品 [{}] [{}]", cdKey, productId);
                throw new BusinessException(ErrorCode.CD_KEY_PRODUCT_SCOPE);
            }
        }
        return selected.getAmount();
    }

    @Override
    public void useRedeem(String cdKey, Long memberId) {
        LambdaUpdateWrapper<RedeemCodeGrant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(RedeemCodeGrant::getCdKey, cdKey);
        wrapper.set(RedeemCodeGrant::getUseTime, LocalDateTime.now());
        wrapper.set(RedeemCodeGrant::getState, 1);
        baseMapper.update(null, wrapper);
    }
}
