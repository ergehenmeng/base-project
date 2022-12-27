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
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.Merchant;
import com.eghm.model.dto.business.merchant.MerchantAddRequest;
import com.eghm.model.dto.business.merchant.MerchantEditRequest;
import com.eghm.model.dto.business.merchant.MerchantQueryRequest;
import com.eghm.model.vo.login.LoginResponse;
import com.eghm.model.vo.menu.MenuResponse;
import com.eghm.service.business.MerchantService;
import com.eghm.service.business.MerchantRoleService;
import com.eghm.service.common.JwtTokenService;
import com.eghm.service.sys.SysMenuService;
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

    private final MerchantRoleService merchantRoleService;

    private final SysMenuService sysMenuService;

    private final JwtTokenService jwtTokenService;

    @Override
    public Page<Merchant> getByPage(MerchantQueryRequest request) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Merchant::getMerchantName, request.getQueryName());
        wrapper.eq(request.getType() != null, Merchant::getType, request.getType());
        wrapper.eq(request.getState() != null, Merchant::getState, MerchantState.of(request.getState()));
        return merchantMapper.selectPage(PageUtil.createPage(request), wrapper);
    }
    
    @Override
    public void create(MerchantAddRequest request) {
        this.checkUserNameRedo(request.getUserName());
        this.checkMobileRedo(request.getMobile());
        Merchant merchant = DataUtil.copy(request, Merchant.class);

        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        merchant.setPwd(encoder.encode(pwd));
        merchant.setInitPwd(true);
        merchant.setState(MerchantState.NORMAL);
        merchantMapper.insert(merchant);
        merchantRoleService.authRole(merchant.getId(), MerchantRoleMap.parseRoleType(request.getType()));
    }
    
    @Override
    public void update(MerchantEditRequest request) {
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        merchantMapper.updateById(merchant);
        merchantRoleService.authRole(merchant.getId(), MerchantRoleMap.parseRoleType(request.getType()));
    }

    @Override
    public LoginResponse login(String userName, String pwd) {
        Merchant merchant = this.selectByUserName(userName);
        if (merchant == null) {
            throw new BusinessException(ErrorCode.MERCHANT_NOT_FOUND);
        }
        if (merchant.getState() == MerchantState.LOGOUT) {
            throw new BusinessException(ErrorCode.MERCHANT_PWD_ERROR);
        }
        if (merchant.getState() == MerchantState.LOCK) {
            throw new BusinessException(ErrorCode.MERCHANT_LOCKED);
        }
        boolean match = encoder.match(pwd, merchant.getPwd());
        if (!match) {
            throw new BusinessException(ErrorCode.MERCHANT_PWD_ERROR);
        }
        List<String> buttonList = sysMenuService.getMerchantAuth(merchant.getId());
        List<MenuResponse> menuList = sysMenuService.getMerchantLeftMenuList(merchant.getId());
        LoginResponse response = new LoginResponse();
        response.setButtonList(buttonList);
        response.setLeftMenuList(menuList);
        response.setToken(jwtTokenService.createToken(merchant, buttonList));
        return response;
    }

    @Override
    public void lock(Long id) {
        LambdaUpdateWrapper<Merchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Merchant::getId, id);
        wrapper.eq(Merchant::getState, MerchantState.NORMAL);
        wrapper.set(Merchant::getState, MerchantState.LOCK);
        merchantMapper.update(null, wrapper);
    }
    
    @Override
    public void unlock(Long id) {
        LambdaUpdateWrapper<Merchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Merchant::getId, id);
        wrapper.eq(Merchant::getState, MerchantState.LOCK);
        wrapper.set(Merchant::getState, MerchantState.NORMAL);
        merchantMapper.update(null, wrapper);
    }
    
    @Override
    public void resetPwd(Long id) {
        Merchant merchant = new Merchant();
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        merchant.setPwd(encoder.encode(pwd));
        merchant.setInitPwd(true);
        merchant.setId(id);
        merchantMapper.updateById(merchant);
    }

    @Override
    public Merchant selectByUserName(String userName) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getUserName, userName);
        return merchantMapper.selectOne(wrapper);
    }

    /**
     * 校验手机号是否被占用
     * @param mobile 用户名
     */
    private void checkMobileRedo(String mobile) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getMobile, mobile);
        Integer count = merchantMapper.selectCount(wrapper);
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
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getUserName, userName);
        Integer count = merchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户名被占用 [{}]", userName);
            throw new BusinessException(ErrorCode.MERCHANT_REDO);
        }
    }

}
