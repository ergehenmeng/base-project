package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.SysMerchantMapper;
import com.eghm.dao.model.SysMerchant;
import com.eghm.model.dto.merchant.MerchantAddRequest;
import com.eghm.model.dto.merchant.MerchantEditRequest;
import com.eghm.model.dto.merchant.MerchantQueryRequest;
import com.eghm.model.enums.MerchantState;
import com.eghm.service.business.SysMerchantService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Service("sysMerchantService")
@AllArgsConstructor
public class SysMerchantServiceImpl implements SysMerchantService {
    
    private final SysMerchantMapper sysMerchantMapper;
    
    private final SysConfigApi sysConfigApi;
    
    private final Encoder encoder;
    
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
        // TODO 账户名重复校验
        SysMerchant merchant = DataUtil.copy(request, SysMerchant.class);
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        merchant.setPwd(encoder.encode(pwd));
        merchant.setInitPwd(true);
        merchant.setState(MerchantState.NORMAL);
        sysMerchantMapper.insert(merchant);
    
        // TODO 根据商户类型进行授权
    }
    
    @Override
    public void update(MerchantEditRequest request) {
        SysMerchant merchant = DataUtil.copy(request, SysMerchant.class);
        sysMerchantMapper.insert(merchant);
        // TODO 更新角色权限信息
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
}
