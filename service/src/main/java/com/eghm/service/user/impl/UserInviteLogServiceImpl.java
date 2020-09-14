package com.eghm.service.user.impl;

import com.eghm.dao.mapper.UserInviteLogMapper;
import com.eghm.dao.model.UserInviteLog;
import com.eghm.service.user.UserInviteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
@Service("userInviteLogService")
public class UserInviteLogServiceImpl implements UserInviteLogService {

    private UserInviteLogMapper userInviteLogMapper;

    @Autowired
    public void setUserInviteLogMapper(UserInviteLogMapper userInviteLogMapper) {
        this.userInviteLogMapper = userInviteLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertSelective(UserInviteLog inviteLog) {
        userInviteLogMapper.insertSelective(inviteLog);
    }
}