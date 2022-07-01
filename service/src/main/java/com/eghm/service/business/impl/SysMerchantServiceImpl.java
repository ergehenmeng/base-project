package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.MerchantRoleMap;
import com.eghm.common.enums.ref.MerchantState;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.SysMerchantMapper;
import com.eghm.dao.model.SysMerchant;
import com.eghm.model.dto.business.merchant.MerchantAddRequest;
import com.eghm.model.dto.business.merchant.MerchantEditRequest;
import com.eghm.model.dto.business.merchant.MerchantQueryRequest;
import com.eghm.service.business.SysMerchantService;
import com.eghm.service.sys.SysMerchantRoleService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.utils.PageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Service("sysMerchantService")
@AllArgsConstructor
@Slf4j
public class SysMerchantServiceImpl implements SysMerchantService {
    
    private final SysMerchantMapper sysMerchantMapper;
    
    private final SysConfigApi sysConfigApi;
    
    private final Encoder encoder;

    private final SysMerchantRoleService sysMerchantRoleService;

    @Override
    public Page<SysMerchant> getByPage(MerchantQueryRequest request) {
        LambdaQueryWrapper<SysMerchant> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SysMerchant::getMerchantName, request.getQueryName());
        wrapper.eq(request.getType() != null, SysMerchant::getType, request.getType());
        wrapper.eq(request.getState() != null, SysMerchant::getState, MerchantState.of(request.getState()));
        return sysMerchantMapper.selectPage(PageUtil.createPage(request), wrapper);
    }
    
    @Override
    public void create(MerchantAddRequest request) {
        this.checkUserNameRedo(request.getUserName());
        this.checkMobileRedo(request.getMobile());
        SysMerchant merchant = DataUtil.copy(request, SysMerchant.class);

        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        merchant.setPwd(encoder.encode(pwd));
        merchant.setInitPwd(true);
        merchant.setState(MerchantState.NORMAL);
        sysMerchantMapper.insert(merchant);
        sysMerchantRoleService.authRole(merchant.getId(), MerchantRoleMap.parseRoleType(request.getType()));
    }
    
    @Override
    public void update(MerchantEditRequest request) {
        SysMerchant merchant = DataUtil.copy(request, SysMerchant.class);
        sysMerchantMapper.updateById(merchant);
        sysMerchantRoleService.authRole(merchant.getId(), MerchantRoleMap.parseRoleType(request.getType()));
    }
    
    @Override
    public void lock(Long id) {
        LambdaUpdateWrapper<SysMerchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysMerchant::getId, id);
        wrapper.eq(SysMerchant::getState, MerchantState.NORMAL);
        wrapper.set(SysMerchant::getState, MerchantState.LOCK);
        sysMerchantMapper.update(null, wrapper);
    }
    
    @Override
    public void unlock(Long id) {
        LambdaUpdateWrapper<SysMerchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysMerchant::getId, id);
        wrapper.eq(SysMerchant::getState, MerchantState.LOCK);
        wrapper.set(SysMerchant::getState, MerchantState.NORMAL);
        sysMerchantMapper.update(null, wrapper);
    }
    
    @Override
    public void resetPwd(Long id) {
        SysMerchant merchant = new SysMerchant();
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        merchant.setPwd(encoder.encode(pwd));
        merchant.setInitPwd(true);
        merchant.setId(id);
        sysMerchantMapper.updateById(merchant);
    }

    /**
     * 校验手机号是否被占用
     * @param mobile 用户名
     */
    private void checkMobileRedo(String mobile) {
        LambdaQueryWrapper<SysMerchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMerchant::getMobile, mobile);
        Integer count = sysMerchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户手机号被占用 [{}]", mobile);
            throw new BusinessException(ErrorCode.MERCHANT_MOBILE_REDO);
        }
    }

    /**
     * 校验用户名是否被占用
     * @param userName 用户名
     */
    private void checkUserNameRedo(String userName) {
        LambdaQueryWrapper<SysMerchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMerchant::getUserName, userName);
        Integer count = sysMerchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户名被占用 [{}]", userName);
            throw new BusinessException(ErrorCode.MERCHANT_REDO);
        }
    }

}
