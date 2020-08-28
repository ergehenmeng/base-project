package com.eghm.service.user.impl;

import com.eghm.common.utils.AesUtil;
import com.eghm.common.utils.BankCardUtil;
import com.eghm.configuration.ApplicationProperties;
import com.eghm.dao.mapper.user.UserExtMapper;
import com.eghm.dao.model.business.UserExt;
import com.eghm.dao.model.user.User;
import com.eghm.model.dto.user.UserAuthRequest;
import com.eghm.service.user.UserExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2019/8/28 16:18
 */
@Service("userExtService")
@Transactional(rollbackFor = RuntimeException.class)
public class UserExtServiceImpl implements UserExtService {

    private UserExtMapper userExtMapper;

    private ApplicationProperties applicationProperties;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Autowired
    public void setUserExtMapper(UserExtMapper userExtMapper) {
        this.userExtMapper = userExtMapper;
    }

    @Override
    public void init(User user) {
        UserExt ext = new UserExt();
        ext.setUserId(user.getId());
        userExtMapper.insertSelective(ext);
    }

    @Override
    public void realNameAuth(UserAuthRequest request) {
        UserExt ext = new UserExt();
        ext.setRealName(request.getRealName());
        ext.setUserId(request.getUserId());
        ext.setBirthday(BankCardUtil.getNormalBirthDay(request.getIdCard()));
        ext.setIdCard(AesUtil.encrypt(request.getIdCard(), applicationProperties.getSecretKey()));
        userExtMapper.updateByPrimaryKeySelective(ext);
        //TODO 实名制认证
    }
}
