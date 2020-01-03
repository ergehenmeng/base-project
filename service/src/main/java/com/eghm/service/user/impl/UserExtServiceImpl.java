package com.eghm.service.user.impl;

import com.eghm.common.utils.BankCardUtil;
import com.eghm.common.utils.Md5Util;
import com.eghm.configuration.security.Encoder;
import com.eghm.dao.mapper.business.UserExtMapper;
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
