package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.dto.business.merchant.MerchantUserAddRequest;
import com.eghm.dto.business.merchant.MerchantUserEditRequest;
import com.eghm.dto.business.merchant.MerchantUserQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.UserType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MerchantUserMapper;
import com.eghm.model.MerchantUser;
import com.eghm.model.SysUser;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.MerchantUserService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.merchant.MerchantUserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
@Slf4j
@AllArgsConstructor
@Service("merchantUserService")
public class MerchantUserServiceImpl implements MerchantUserService {

    private final Encoder encoder;

    private final CommonService commonService;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final MerchantUserMapper merchantUserMapper;

    @Override
    public Page<MerchantUserResponse> getByPage(MerchantUserQueryRequest request) {
        return merchantUserMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(MerchantUserAddRequest request) {
        SysUser user = new SysUser();
        user.setUserType(UserType.MERCHANT_USER);
        user.setPwd(encoder.encode(MD5.create().digestHex(request.getPassword())));
        user.setInitPwd(user.getPwd());
        user.setPwdUpdateTime(LocalDateTime.now());
        user.setMobile(request.getMobile());
        user.setNickName(request.getNickName());
        sysUserService.insert(user);
        MerchantUser merchant = DataUtil.copy(request, MerchantUser.class);
        merchant.setUserId(user.getId());
        merchantUserMapper.insert(merchant);
        sysRoleService.auth(user.getId(), request.getRoleIds());
    }

    @Override
    public void update(MerchantUserEditRequest request) {
        MerchantUser merchant = this.getMerchantUser(request.getId());

        SysUser user = new SysUser();
        user.setId(merchant.getUserId());
        user.setNickName(request.getNickName());
        user.setMobile(request.getMobile());
        if (StrUtil.isNotBlank(request.getPassword())) {
            user.setPwd(encoder.encode(request.getPassword()));
            user.setInitPwd(user.getPwd());
        }
        sysUserService.updateById(user);
        merchantUserMapper.updateById(DataUtil.copy(request, MerchantUser.class));
        sysRoleService.auth(merchant.getUserId(), request.getRoleIds());
    }

    @Override
    public void deleteById(Long id) {
        MerchantUser merchant = this.getMerchantUser(id);
        sysUserService.deleteById(merchant.getUserId());
        merchantUserMapper.deleteById(id);
    }

    @Override
    public void lockUser(Long id) {
        MerchantUser merchant = this.getMerchantUser(id);
        sysUserService.lockUser(merchant.getUserId());
        merchant.setUpdateTime(LocalDateTime.now());
        merchantUserMapper.updateById(merchant);
    }

    @Override
    public void unlockUser(Long id) {
        MerchantUser merchant = this.getMerchantUser(id);
        sysUserService.unlockUser(merchant.getUserId());
        merchant.setUpdateTime(LocalDateTime.now());
        merchantUserMapper.updateById(merchant);
    }

    /**
     * 查询商户用户信息,并校验是否合法操作
     *
     * @param id id
     * @return 商户用户信息
     */
    private MerchantUser getMerchantUser(Long id) {
        MerchantUser merchant = merchantUserMapper.selectById(id);
        if (merchant == null) {
            log.warn("编辑用户, 商户用户未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.MERCHANT_USER_NULL);
        }
        commonService.checkIllegal(merchant.getMerchantId());
        return merchant;
    }
}
