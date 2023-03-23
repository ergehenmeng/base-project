package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.MerchantRoleMap;
import com.eghm.common.enums.ref.RoleType;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.Merchant;
import com.eghm.model.SysOperator;
import com.eghm.model.dto.business.merchant.MerchantAddRequest;
import com.eghm.model.dto.business.merchant.MerchantEditRequest;
import com.eghm.model.dto.business.merchant.MerchantQueryRequest;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.MerchantService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.utils.PageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Service("merchantService")
@AllArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    
    private final MerchantMapper merchantMapper;
    
    private final SysConfigApi sysConfigApi;
    
    private final Encoder encoder;

    private final SysOperatorService sysOperatorService;

    private final SysRoleService sysRoleService;
    
    private final List<MerchantInitService> initList;

    @Override
    public Page<Merchant> getByPage(MerchantQueryRequest request) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Merchant::getMerchantName, request.getQueryName());
        wrapper.eq(request.getType() != null, Merchant::getType, request.getType());
        return merchantMapper.selectPage(PageUtil.createPage(request), wrapper);
    }
    
    @Override
    public void create(MerchantAddRequest request) {
        this.checkMobileRedo(request.getMobile());
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        SysOperator operator = new SysOperator();
        operator.setUserType(SysOperator.USER_TYPE_2);
        String encode = encoder.encode(MD5.create().digestHex(pwd));
        operator.setInitPwd(encode);
        operator.setPwd(encode);
        // 采用用户名登录
        operator.setMobile(request.getUserName());
        operator.setNickName(request.getNickName());
        sysOperatorService.insert(operator);
        // 系统用户和商户关联
        merchant.setOperatorId(operator.getId());
        merchantMapper.insert(merchant);
        List<RoleType> roleTypes = MerchantRoleMap.parseRoleType(request.getType());
        sysRoleService.authRole(merchant.getId(), roleTypes);
        this.initStore(merchant, roleTypes);
    }
    
    @Override
    public void update(MerchantEditRequest request) {
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        merchantMapper.updateById(merchant);
        sysRoleService.authRole(merchant.getId(), MerchantRoleMap.parseRoleType(request.getType()));
    }

    @Override
    public Merchant selectByOperatorId(Long operatorId) {
        LambdaUpdateWrapper<Merchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Merchant::getOperatorId, operatorId);
        return merchantMapper.selectOne( wrapper);
    }

    @Override
    public void lock(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        sysOperatorService.lockOperator(merchant.getOperatorId());
    }
    
    @Override
    public void unlock(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        sysOperatorService.unlockOperator(merchant.getOperatorId());
    }
    
    @Override
    public void resetPwd(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        sysOperatorService.resetPassword(merchant.getOperatorId(), pwd);
    }

    /**
     * 校验手机号是否被占用
     * @param mobile 用户名
     */
    private void checkMobileRedo(String mobile) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getMobile, mobile);
        Long count = merchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户手机号被占用 [{}]", mobile);
            throw new BusinessException(ErrorCode.MERCHANT_MOBILE_REDO);
        }
    }
    
    /**
     * 根据商户信息初始化商户下的店铺信息
     * @param merchant 商户信息
     * @param typeList 角色类型
     */
    private void initStore(Merchant merchant, List<RoleType> typeList) {
        initList.forEach(service -> {
            if (service.support(typeList)) {
                service.init(merchant);
            }
        });
    }

}
