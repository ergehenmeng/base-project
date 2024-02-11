package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.redeem.RedeemCodeAddRequest;
import com.eghm.dto.business.redeem.RedeemCodeEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.RedeemCodeMapper;
import com.eghm.model.RedeemCode;
import com.eghm.model.RedeemCodeGrant;
import com.eghm.service.business.RedeemCodeGrantService;
import com.eghm.service.business.RedeemCodeService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 兑换码配置表 服务实现类
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

    private final RedeemCodeGrantService redeemCodeGrantService;

    @Override
    public void create(RedeemCodeAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        RedeemCode copy = DataUtil.copy(request, RedeemCode.class);
        copy.setState(0);
        redeemCodeMapper.insert(copy);
    }

    @Override
    public void update(RedeemCodeEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        RedeemCode redeemCode = this.selectByIdRequired(request.getId());
        if (redeemCode.getState() == 1) {
            throw new BusinessException(ErrorCode.REDEEM_CODE_UPDATE);
        }
        RedeemCode copy = DataUtil.copy(request, RedeemCode.class);
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
