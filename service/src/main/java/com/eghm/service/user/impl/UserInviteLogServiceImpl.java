package com.eghm.service.user.impl;

import com.eghm.mapper.UserInviteLogMapper;
import com.eghm.model.UserInviteLog;
import com.eghm.service.user.UserInviteLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
@Service("userInviteLogService")
@AllArgsConstructor
public class UserInviteLogServiceImpl implements UserInviteLogService {

    private final UserInviteLogMapper userInviteLogMapper;

    @Override
    public void insert(UserInviteLog inviteLog) {
        userInviteLogMapper.insert(inviteLog);
    }
}
