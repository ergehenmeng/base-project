package com.eghm.service.business.impl;

import cn.hutool.crypto.digest.MD5;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.dto.business.merchant.MerchantUserAddRequest;
import com.eghm.dto.business.merchant.MerchantUserEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MerchantUserMapper;
import com.eghm.model.MerchantUser;
import com.eghm.model.SysUser;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.MerchantUserService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
@Slf4j
@AllArgsConstructor
@Service("merchantUserService")
public class MerchantUserServiceImpl implements MerchantUserService {

    private final MerchantUserMapper merchantUserMapper;

    private final Encoder encoder;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final CommonService commonService;

    @Override
    public void create(MerchantUserAddRequest request) {
        SysUser user = new SysUser();
        user.setUserType(SysUser.USER_TYPE_3);
        user.setPwd(encoder.encode(MD5.create().digestHex(request.getPassword())));
        user.setInitPwd(user.getPwd());
        user.setMobile(request.getMobile());
        user.setNickName(request.getNickName());
        sysUserService.insert(user);
        MerchantUser merchant = DataUtil.copy(request, MerchantUser.class);
        merchant.setUserId(user.getId());
        merchantUserMapper.insert(merchant);
        sysRoleService.authRole(merchant.getId(), Lists.newArrayList(RoleType.MERCHANT));
    }

    @Override
    public void update(MerchantUserEditRequest request) {
        MerchantUser merchant = merchantUserMapper.selectById(request.getId());
        if (merchant == null) {
            log.warn("编辑用户, 商户用户未查询到 [{}]", request.getId());
            throw new BusinessException(ErrorCode.MERCHANT_USER_NULL);
        }
        commonService.checkIllegal(merchant.getMerchantId());
        SysUser user = new SysUser();
        user.setId(merchant.getUserId());
        user.setNickName(request.getNickName());
        user.setMobile(request.getMobile());
        user.setPwd(encoder.encode(MD5.create().digestHex(request.getPassword())));
        user.setInitPwd(user.getPwd());
        sysUserService.updateById(user);

        merchant.setNickName(request.getNickName());
        merchant.setMobile(request.getMobile());
        merchantUserMapper.updateById(merchant);
    }

}
