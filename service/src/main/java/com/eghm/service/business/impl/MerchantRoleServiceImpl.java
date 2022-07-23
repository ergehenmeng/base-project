package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.RoleType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.MerchantRoleMapper;
import com.eghm.dao.mapper.SysRoleMapper;
import com.eghm.dao.model.MerchantRole;
import com.eghm.dao.model.SysRole;
import com.eghm.service.business.MerchantRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/24 18:25
 */
@Service("merchantRoleService")
@AllArgsConstructor
public class MerchantRoleServiceImpl implements MerchantRoleService {

    private final MerchantRoleMapper merchantRoleMapper;

    private final SysRoleMapper sysRoleMapper;

    @Override
    public void authRole(Long merchantId, List<RoleType> roleList) {
        if (CollUtil.isEmpty(roleList)) {
            throw new BusinessException(ErrorCode.MERCHANT_ROLE_NULL);
        }
        merchantRoleMapper.deleteByMerchantId(merchantId);

        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysRole::getId);
        wrapper.eq(SysRole::getRoleType, roleList);
        List<SysRole> selectList = sysRoleMapper.selectList(wrapper);
        for (SysRole sysRole : selectList) {
            merchantRoleMapper.insert(new MerchantRole(merchantId, sysRole.getId()));
        }
    }



}
