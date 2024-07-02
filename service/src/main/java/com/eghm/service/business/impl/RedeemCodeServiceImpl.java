package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.redeem.RedeemCodeAddRequest;
import com.eghm.dto.business.redeem.RedeemCodeEditRequest;
import com.eghm.dto.business.redeem.RedeemCodeQueryRequest;
import com.eghm.dto.ext.StoreScope;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.RedeemCodeMapper;
import com.eghm.model.RedeemCode;
import com.eghm.model.RedeemCodeGrant;
import com.eghm.service.business.RedeemCodeGrantService;
import com.eghm.service.business.RedeemCodeScopeService;
import com.eghm.service.business.RedeemCodeService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.eghm.vo.business.redeem.RedeemDetailResponse;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 兑换码配置表 服务实现类<br>
 * 兑换码目前只针对于: 民宿, 线路, 场馆
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Slf4j
@AllArgsConstructor
@Service("redeemCodeService")
public class RedeemCodeServiceImpl implements RedeemCodeService {

    private final RedeemCodeMapper redeemCodeMapper;

    private final RedeemCodeScopeService redeemCodeScopeService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    @Override
    public Page<RedeemCode> listPage(RedeemCodeQueryRequest request) {
        LambdaQueryWrapper<RedeemCode> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), RedeemCode::getTitle, request.getQueryName());
        wrapper.eq(request.getState() != null, RedeemCode::getState, request.getState());
        wrapper.orderByDesc(RedeemCode::getId);
        return redeemCodeMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(RedeemCodeAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        RedeemCode copy = DataUtil.copy(request, RedeemCode.class);
        copy.setState(0);
        redeemCodeMapper.insert(copy);
        redeemCodeScopeService.insertOrUpdate(copy.getId(), request.getStoreList());
    }

    @Override
    public void update(RedeemCodeEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        RedeemCode redeemCode = this.selectByIdRequired(request.getId());
        RedeemCode copy;
        if (redeemCode.getState() == 1) {
            copy = DataUtil.copy(request, RedeemCode.class, "startTime", "endTime", "amount", "num");
            // 已发放兑换码, 只允许修改使用范围
            redeemCodeScopeService.insertOrUpdate(request.getId(), request.getStoreList());
        } else {
            copy = DataUtil.copy(request, RedeemCode.class);
        }
        redeemCodeMapper.updateById(copy);
    }

    @Override
    public void delete(Long id) {
        RedeemCode select = this.selectByIdRequired(id);
        if (select.getState() == 1) {
            log.error("兑换码已发放 [{}]", id);
            throw new BusinessException(ErrorCode.REDEEM_CODE_DELETE);
        }
        redeemCodeMapper.deleteById(id);
        redeemCodeScopeService.delete(id);
    }

    @Override
    public RedeemCode selectByIdRequired(Long id) {
        RedeemCode redeemCode = redeemCodeMapper.selectById(id);
        if (redeemCode == null) {
            log.error("兑换码不存在 [{}]", id);
            throw new BusinessException(ErrorCode.REDEEM_CODE_NULL);
        }
        return redeemCode;
    }

    @Override
    public RedeemDetailResponse detail(Long id) {
        RedeemCode select = this.selectByIdRequired(id);
        RedeemDetailResponse response = DataUtil.copy(select, RedeemDetailResponse.class);
        List<StoreScope>  scopeList = redeemCodeScopeService.getScopeList(id);
        response.setStoreIds(scopeList.stream().map(StoreScope::getStoreId).collect(Collectors.toList()));
        return response;
    }

    @Override
    public void generate(Long id) {
        RedeemCode select = this.selectByIdRequired(id);
        if (select.getState() == 1) {
            log.error("兑换码已发放 [{}]", id);
            throw new BusinessException(ErrorCode.REDEEM_CODE_GENERATE);
        }
        List<RedeemCodeGrant> grantList = Lists.newArrayListWithExpectedSize(select.getNum());
        for (int i = 0; i < select.getNum(); i++) {
            RedeemCodeGrant grant = DataUtil.copy(select, RedeemCodeGrant.class, "id", "createTime", "updateTime", "deleted");
            grant.setState(0);
            grant.setRedeemCodeId(select.getId());
            grant.setCdKey(StringUtil.generateCdKey());
            grantList.add(grant);
        }
        redeemCodeGrantService.saveBatch(grantList);
        select.setState(1);
        redeemCodeMapper.updateById(select);
    }

    /**
     * 校验标题是否重复
     *
     * @param title 名称
     * @param id id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<RedeemCode> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RedeemCode::getTitle, title);
        wrapper.ne(id != null, RedeemCode::getId, id);
        Long count = redeemCodeMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("重复的兑换码名称 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.REDEEM_CODE_REDO);
        }
    }

}
