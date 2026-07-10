package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.CheckBox;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysRoleMapper;
import com.eghm.application.system.query.SysRoleQueryService;
import com.eghm.application.shared.vo.sys.ext.SysRoleResponse;
import com.eghm.domain.shared.enums.DisplayState;
import com.eghm.domain.shared.enums.RoleType;
import com.eghm.domain.system.model.SysRole;
import com.eghm.domain.system.repository.SysRoleMenuRepository;
import com.eghm.domain.system.repository.SysRoleRepository;
import com.eghm.domain.system.repository.SysUserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis角色查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysRoleQueryService implements SysRoleQueryService {

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleRepository sysRoleRepository;

    private final SysUserRoleRepository sysUserRoleRepository;

    private final SysRoleMenuRepository sysRoleMenuRepository;

    @Override
    public Page<SysRoleResponse> getByPage(PagingQuery request) {
        return MybatisPageUtil.fromMybatis(sysRoleMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request.getQueryName()));
    }

    @Override
    public List<CheckBox> listCommonRoles() {
        List<SysRole> roleList = sysRoleRepository.findCommonRoles();
        return DataUtil.copy(roleList, role -> new CheckBox(role.getId(), role.getRoleName()));
    }

    @Override
    public List<Long> listRoleIdsByUserId(Long userId) {
        return sysUserRoleRepository.findRoleIdsByUserId(userId);
    }

    @Override
    public List<String> listRoleMenuIds(Long roleId) {
        return sysRoleMenuRepository.findMenuIdsByRoleId(roleId);
    }

    @Override
    public Integer getMenuDisplayState(Long roleId) {
        SysRole role = sysRoleRepository.findById(roleId);
        if (role == null || role.getRoleType() == RoleType.COMMON) {
            return DisplayState.SYSTEM.getValue();
        }
        return DisplayState.MERCHANT.getValue();
    }
}

