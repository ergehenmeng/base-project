package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.MerchantRoleMapping;
import com.eghm.enums.ref.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.Merchant;
import com.eghm.model.SysUser;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.MerchantService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
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

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;
    
    private final List<MerchantInitService> initList;

    @Override
    public Page<Merchant> getByPage(MerchantQueryRequest request) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Merchant::getMerchantName, request.getQueryName());
        wrapper.eq(request.getType() != null, Merchant::getType, request.getType());
        return merchantMapper.selectPage(request.createPage(), wrapper);
    }
    
    @Override
    public void create(MerchantAddRequest request) {
        this.checkMerchantRedo(request.getMerchantName(), null);
        this.checkMobileRedo(request.getMobile(), null);
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        SysUser user = new SysUser();
        user.setUserType(SysUser.USER_TYPE_2);
        String encode = encoder.encode(MD5.create().digestHex(pwd));
        user.setInitPwd(encode);
        user.setPwd(encode);
        // 采用用户名登录
        user.setMobile(request.getMobile());
        user.setNickName(request.getNickName());
        sysUserService.insert(user);
        // 系统用户和商户关联
        merchant.setUserId(user.getId());
        merchantMapper.insert(merchant);
        List<RoleType> roleTypes = MerchantRoleMapping.parseRoleType(request.getType());
        sysRoleService.authRole(merchant.getId(), roleTypes);
        this.initStore(merchant, roleTypes);
    }
    
    @Override
    public void update(MerchantEditRequest request) {
        this.checkMerchantRedo(request.getMerchantName(), request.getId());
        this.checkMobileRedo(request.getMobile(), request.getId());
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        merchantMapper.updateById(merchant);
        SysUser user = new SysUser();
        user.setMobile(request.getMobile());
        user.setNickName(request.getNickName());
        sysUserService.updateById(user);
        sysRoleService.authRole(merchant.getId(), MerchantRoleMapping.parseRoleType(request.getType()));
    }

    @Override
    public Merchant selectByUserId(Long userId) {
        LambdaUpdateWrapper<Merchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Merchant::getUserId, userId);
        return merchantMapper.selectOne( wrapper);
    }

    @Override
    public void lock(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        sysUserService.lockUser(merchant.getUserId());
    }
    
    @Override
    public void unlock(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        sysUserService.unlockUser(merchant.getUserId());
    }
    
    @Override
    public void resetPwd(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        sysUserService.resetPassword(merchant.getUserId(), pwd);
    }

    /**
     * 校验商户名称是否被占用
     * @param merchantName 商户名称
     * @param id id
     */
    private void checkMerchantRedo(String merchantName, Long id) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getMerchantName, merchantName);
        wrapper.eq(id != null, Merchant::getId, id);
        Long count = merchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户名称被占用 [{}]", merchantName);
            throw new BusinessException(ErrorCode.MERCHANT_REDO);
        }
    }

    /**
     * 校验手机号是否被占用
     * @param mobile 用户名
     * @param id 用户
     */
    private void checkMobileRedo(String mobile, Long id) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getMobile, mobile);
        wrapper.eq(id != null, Merchant::getId, id);
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
