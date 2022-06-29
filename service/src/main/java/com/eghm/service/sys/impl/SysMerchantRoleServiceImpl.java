package com.eghm.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.RoleType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.SysMerchantRoleMapper;
import com.eghm.dao.mapper.SysRoleMapper;
import com.eghm.dao.model.SysMerchantRole;
import com.eghm.dao.model.SysRole;
import com.eghm.service.sys.SysMerchantRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/24 18:25
 */
@Service("sysMerchantRoleService")
@AllArgsConstructor
public class SysMerchantRoleServiceImpl implements SysMerchantRoleService {

    private final SysMerchantRoleMapper sysMerchantRoleMapper;

    private final SysRoleMapper sysRoleMapper;

    @Override
    public void authRole(Long merchantId, List<RoleType> roleList) {
        if (CollUtil.isEmpty(roleList)) {
            throw new BusinessException(ErrorCode.MERCHANT_ROLE_NULL);
        }
        sysMerchantRoleMapper.deleteByMerchantId(merchantId);

        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysRole::getId);
        wrapper.eq(SysRole::getRoleType, roleList);
        List<SysRole> selectList = sysRoleMapper.selectList(wrapper);
        for (SysRole sysRole : selectList) {
            sysMerchantRoleMapper.insert(new SysMerchantRole(merchantId, sysRole.getId()));
        }
    }



}
