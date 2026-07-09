package com.eghm.domain.system.repository;

import com.eghm.domain.shared.enums.UserState;
import com.eghm.domain.system.model.SysUser;

import java.time.LocalDateTime;

/**
 * 系统用户仓储
 *
 * @author 二哥很猛
 */
public interface SysUserRepository {

    boolean existsUserName(String userName, Long excludeId);

    boolean existsMobile(String mobile, Long excludeId);

    SysUser findById(Long id);

    SysUser findByMobile(String mobile);

    SysUser findByUserName(String userName);

    SysUser findByOpenId(String openId);

    void save(SysUser user);

    void update(SysUser user);

    void updatePassword(Long id, String password, LocalDateTime pwdUpdateTime);

    void resetPassword(Long id, String password, LocalDateTime pwdUpdateTime);

    void deleteById(Long id);

    void updateState(Long id, UserState state);

    void updateOpenId(Long id, String openId);

    void clearOpenId(Long id);

    void updateTotpSecret(Long id, String secret);

    void clearTotpSecret(Long id);

    void updateAvatar(Long id, String avatar);

    void updateProfile(Long id, String nickName, String mobile);
}
