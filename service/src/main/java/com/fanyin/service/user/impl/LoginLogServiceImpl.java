package com.fanyin.service.user.impl;

import com.fanyin.dao.mapper.business.LoginLogMapper;
import com.fanyin.dao.model.business.LoginLog;
import com.fanyin.model.dto.login.LoginRecord;
import com.fanyin.service.user.LoginLogService;
import com.fanyin.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2019/8/28 15:25
 */
@Service("loginLogService")
@Transactional(rollbackFor = RuntimeException.class)
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Async
    public void addLoginLog(LoginRecord record) {
        LoginLog loginLog = DataUtil.copy(record, LoginLog.class);
        loginLogMapper.insertSelective(loginLog);
    }

    @Override
    public LoginLog getLastLogin(Integer userId) {
        return loginLogMapper.getLastLogin(userId);
    }
}
