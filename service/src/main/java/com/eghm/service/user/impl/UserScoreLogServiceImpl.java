package com.eghm.service.user.impl;

import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.UserScoreLogMapper;
import com.eghm.dao.model.UserScoreLog;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.user.UserScoreLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@Service("userScoreLogService")
public class UserScoreLogServiceImpl implements UserScoreLogService {

    private UserScoreLogMapper userScoreLogMapper;

    private SysConfigApi sysConfigApi;

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Autowired
    public void setUserScoreLogMapper(UserScoreLogMapper userScoreLogMapper) {
        this.userScoreLogMapper = userScoreLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertSelective(UserScoreLog scoreLog) {
        userScoreLogMapper.insertSelective(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigApi.getInt(ConfigConstant.SIGN_IN_SCORE));
    }


}
