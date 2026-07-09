package com.eghm.application.system.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.eghm.application.shared.configuration.encoder.Encoder;
import com.eghm.domain.shared.enums.DataType;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.UserState;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.system.model.SysDeptData;
import com.eghm.domain.system.model.SysUser;
import com.eghm.domain.system.repository.SysUserRepository;
import com.eghm.application.shared.dto.sys.user.UserAddRequest;
import com.eghm.application.shared.dto.sys.user.UserEditRequest;
import com.eghm.application.shared.dto.sys.user.UserProfileRequest;
import com.eghm.application.system.service.SysDeptDataApplicationService;
import com.eghm.application.system.service.SysRoleApplicationService;
import com.eghm.application.system.service.SysUserCommandApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 系统用户命令服务实现
 *
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserCommandApplicationServiceImpl implements SysUserCommandApplicationService {

    private final Encoder encoder;
    private final SysUserRepository sysUserRepository;
    private final SysRoleApplicationService sysRoleService;
    private final SysDeptDataApplicationService sysDeptDataService;

    @Override
    public void create(UserAddRequest request) {
        checkUserName(request.getUserName(), null);
        checkMobile(request.getMobile(), null);
        SysUser user = DataUtil.copy(request, SysUser.class);
        String password = this.initPassword(request.getMobile());
        user.initializeSystemUser(password, LocalDateTime.now());
        sysUserRepository.save(user);
        sysRoleService.auth(user.getId(), request.getRoleIds());
        if (request.getDataType() == DataType.CUSTOM) {
            request.getDeptIds().forEach(deptId -> sysDeptDataService.insert(new SysDeptData(user.getId(), deptId)));
        }
    }

    @Override
    public void update(UserEditRequest request) {
        checkUserName(request.getUserName(), request.getId());
        checkMobile(request.getMobile(), request.getId());
        SysUser user = DataUtil.copy(request, SysUser.class);
        sysUserRepository.update(user);
        sysRoleService.auth(user.getId(), request.getRoleIds());
        if (request.getDataType() != null && request.getDataType() == DataType.CUSTOM) {
            sysDeptDataService.deleteByUserId(user.getId());
            request.getDeptIds().forEach(deptId -> sysDeptDataService.insert(new SysDeptData(user.getId(), deptId)));
        }
    }

    @Override
    public void deleteById(Long id) {
        sysUserRepository.deleteById(id);
    }

    @Override
    public void updateState(Long id, UserState state) {
        SysUser user = this.getByIdRequired(id);
        user.changeState(state);
        sysUserRepository.updateState(user.getId(), user.getState());
    }

    @Override
    public SysUser getByIdRequired(Long id) {
        SysUser user = sysUserRepository.findById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public void updateAvatar(Long userId, String avatar) {
        sysUserRepository.updateAvatar(userId, avatar);
    }

    @Override
    public void updateProfile(UserProfileRequest request) {
        sysUserRepository.updateProfile(request.getUserId(), request.getNickName(), request.getMobile());
    }

    private String initPassword(String mobile) {
        String rsaPassword = SecureUtil.sha256(mobile.substring(3));
        return encoder.encode(rsaPassword);
    }

    private void checkUserName(String userName, Long excludeId) {
        if (sysUserRepository.existsUserName(userName, excludeId)) {
            log.warn("账户名被占用 [{}] [{}]", excludeId, userName);
            throw new BusinessException(ErrorCode.USER_NAME_REDO);
        }
    }

    private void checkMobile(String mobile, Long excludeId) {
        if (sysUserRepository.existsMobile(mobile, excludeId)) {
            log.warn("手机号码被占用 [{}] [{}]", excludeId, mobile);
            throw new BusinessException(ErrorCode.MOBILE_REDO);
        }
    }
}
