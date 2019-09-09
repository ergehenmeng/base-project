package com.fanyin.service.user.impl;

import com.fanyin.common.utils.BankCardUtil;
import com.fanyin.common.utils.Md5Util;
import com.fanyin.configuration.security.Encoder;
import com.fanyin.dao.mapper.business.UserExtMapper;
import com.fanyin.dao.model.business.UserExt;
import com.fanyin.dao.model.user.User;
import com.fanyin.model.dto.user.UserAuthRequest;
import com.fanyin.service.user.UserExtService;
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

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private Encoder encoder;

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
        ext.setIdCard(Md5Util.md5(encoder.encode(request.getIdCard())));
        userExtMapper.updateByPrimaryKeySelective(ext);
        //TODO 实名制认证
    }
}
