package com.fanyin.service.user.impl;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.RegExpUtil;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.configuration.security.PasswordEncoder;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.dao.mapper.user.UserMapper;
import com.fanyin.dao.model.user.User;
import com.fanyin.model.dto.user.UserAccountLogin;
import com.fanyin.model.dto.user.UserRegister;
import com.fanyin.model.dto.user.UserSmsLogin;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.service.common.AccessTokenService;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.fanyin.service.user.UserService;
import com.fanyin.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/8/19 15:50
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccessTokenService accessTokenService;


    @Override
    public User register(UserRegister register) {
        User user = DataUtil.copy(register, User.class);
        this.encodePassword(user);
        this.generateNickName(user);
        userMapper.insertSelective(user);
        return user;
    }

    /**
     * 对登陆密码进行加密
     * @param user 用户信息
     */
    private void encodePassword(User user){
        if(StringUtil.isNotBlank(user.getPwd())){
            user.setPwd(passwordEncoder.encode(user.getPwd()));
        }
    }

    /**
     * 昵称为空时,动态生成昵称
     * @param user 用户信息
     */
    private void generateNickName(User user){
        if(StringUtil.isBlank(user.getNickName())){
            user.setNickName(systemConfigApi.getString(ConfigConstant.NICK_NAME_PREFIX) + System.nanoTime());
        }
    }


    @Override
    public AccessToken accountLogin(UserAccountLogin login) {
        User user = this.getByAccount(login.getAccount());
        if(user == null){
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }
        if(!passwordEncoder.encode(login.getPwd()).equals(user.getPwd())){
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ERROR);
        }


        return null;
    }


    @Override
    public AccessToken smsLogin(UserSmsLogin login) {
        return null;
    }

    @Override
    public User getByAccount(String account) {
        if(RegExpUtil.mobile(account)){
            return userMapper.getByMobile(account);
        }
        return userMapper.getByEmail(account);
    }

}
