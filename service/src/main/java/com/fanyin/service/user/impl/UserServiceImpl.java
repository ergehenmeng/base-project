package com.fanyin.service.user.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.common.constant.SmsTypeConstant;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.RegExpUtil;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.configuration.security.Encoder;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.dao.mapper.user.UserMapper;
import com.fanyin.dao.model.user.User;
import com.fanyin.model.dto.login.LoginRecord;
import com.fanyin.model.dto.login.AccountLoginRequest;
import com.fanyin.model.dto.login.SmsLoginRequest;
import com.fanyin.model.dto.user.UserRegister;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.model.ext.RequestMessage;
import com.fanyin.model.ext.RequestThreadLocal;
import com.fanyin.model.vo.login.LoginToken;
import com.fanyin.service.cache.CacheProxyService;
import com.fanyin.service.common.SmsService;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.fanyin.service.user.LoginLogService;
import com.fanyin.service.user.UserExtService;
import com.fanyin.service.user.UserService;
import com.fanyin.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    private Encoder encoder;

    @Autowired
    private CacheProxyService cacheProxyService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserExtService userExtService;

    @Override
    public User register(UserRegister register) {
        User user = DataUtil.copy(register, User.class);
        this.encodePassword(user);
        this.generateNickName(user);
        userMapper.insertSelective(user);
        this.doPostRegister(user);
        return user;
    }

    /**
     * 用户注册后置处理 初始化用户附加信息
     * @param user 用户信息
     */
    private void doPostRegister(User user){
        userExtService.init(user);
    }

    /**
     * 对登陆密码进行加密
     * @param user 用户信息
     */
    private void encodePassword(User user){
        if(StringUtil.isNotBlank(user.getPwd())){
            user.setPwd(encoder.encode(user.getPwd()));
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
    public LoginToken accountLogin(AccountLoginRequest login) {
        User user = this.getByAccount(login.getAccount());
        if(user == null){
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }
        if(!encoder.encode(login.getPwd()).equals(user.getPwd())){
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        return this.doLogin(user,login.getIp());
    }


    @Override
    public LoginToken smsLogin(SmsLoginRequest login) {
        String smsCode = smsService.getSmsCode(SmsTypeConstant.LOGIN_SMS, login.getMobile());
        if(smsCode == null){
            throw new BusinessException(ErrorCodeEnum.LOGIN_SMS_CODE_EXPIRE);
        }
        if(!smsCode.equals(login.getSmsCode())){
            throw new BusinessException(ErrorCodeEnum.LOGIN_SMS_CODE_ERROR);
        }
        User user = userMapper.getByMobile(login.getMobile());
        if(user == null){
            throw new BusinessException(ErrorCodeEnum.MOBILE_NOT_REGISTER);
        }
        return this.doLogin(user,login.getIp());
    }

    /**
     * 移动端登陆系统
     * 1.创建token
     * 2.添加登陆日志
     * @param user 用户id
     * @param ip 登陆ip
     * @return token信息
     */
    private LoginToken doLogin(User user,String ip){
        RequestMessage request = RequestThreadLocal.get();
        AccessToken accessToken = cacheProxyService.createAccessToken(user, request.getChannel());
        LoginRecord record = LoginRecord.builder().channel(request.getChannel()).ip(ip).deviceBrand(request.getDeviceBrand()).deviceModel(request.getDeviceModel()).userId(user.getId()).softwareVersion(request.getVersion()).build();
        loginLogService.addLoginLog(record);
        return LoginToken.builder().accessKey(accessToken.getAccessKey()).accessToken(accessToken.getAccessToken()).build();
    }


    @Override
    public User getByAccount(String account) {
        if(RegExpUtil.mobile(account)){
            return userMapper.getByMobile(account);
        }
        return userMapper.getByEmail(account);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.USER,key = "#p0",cacheManager = "longCacheManager")
    public User getById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
