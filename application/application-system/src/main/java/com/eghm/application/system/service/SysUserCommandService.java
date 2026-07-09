package com.eghm.application.system.service;

import com.eghm.domain.shared.enums.UserState;
import com.eghm.domain.system.model.SysUser;
import com.eghm.dto.sys.user.UserAddRequest;
import com.eghm.dto.sys.user.UserEditRequest;
import com.eghm.dto.sys.user.UserProfileRequest;

/**
 * 系统用户命令服务 - 负责用户CRUD操作
 *
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
public interface SysUserCommandService {

    void create(UserAddRequest request);

    void update(UserEditRequest request);

    void deleteById(Long id);

    void updateState(Long id, UserState state);

    SysUser getByIdRequired(Long id);

    void updateAvatar(Long userId, String avatar);

    void updateProfile(UserProfileRequest request);
}
