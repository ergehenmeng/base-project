package com.eghm.service.user.impl;

import com.eghm.dao.mapper.UserInviteLogMapper;
import com.eghm.dao.model.UserInviteLog;
import com.eghm.service.common.KeyGenerator;
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

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setUserInviteLogMapper(UserInviteLogMapper userInviteLogMapper) {
        this.userInviteLogMapper = userInviteLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertSelective(UserInviteLog inviteLog) {
        inviteLog.setId(keyGenerator.generateKey());
        userInviteLogMapper.insertSelective(inviteLog);
    }
}
