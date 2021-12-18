package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号查询状态正常的用户信息
     * @param mobile 手机号
     * @return 用户信息
     */
    User getByMobile(@Param("mobile")String mobile);

    /**
     * 根据邮箱查询状态正常的用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    User getByEmail(@Param("email")String email);

    /**
     * 通过邀请码查询用户信息
     * @param inviteCode 邀请码
     * @return user
     */
    User getByInviteCode(@Param("inviteCode") String inviteCode);
}