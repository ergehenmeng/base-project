package com.eghm.service.user;

import com.eghm.dao.model.UserInviteLog;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
public interface UserInviteLogService {

    /**
     * 添加邀请记录
     * @param inviteLog log
     */
    void insertSelective(UserInviteLog inviteLog);
}
