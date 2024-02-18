package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.redeem.RedeemCodeGrantQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.RedeemCodeGrantMapper;
import com.eghm.model.RedeemCodeGrant;
import com.eghm.service.business.RedeemCodeGrantService;
import com.eghm.vo.business.redeem.RedeemCodeGrantResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 兑换码发放表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Slf4j
@AllArgsConstructor
@Service("redeemCodeGrantService")
public class RedeemCodeGrantServiceImpl extends ServiceImpl<RedeemCodeGrantMapper, RedeemCodeGrant> implements RedeemCodeGrantService {

    @Override
    public Page<RedeemCodeGrantResponse> listPage(RedeemCodeGrantQueryRequest request) {
        return baseMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<RedeemCodeGrantResponse> getList(RedeemCodeGrantQueryRequest request) {
        return baseMapper.listPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public Integer getRedeemAmount(String cdKey) {
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
        return selected.getAmount();
    }
}
