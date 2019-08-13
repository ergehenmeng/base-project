package com.fanyin.service.common.impl;


import com.fanyin.common.constant.RedisConstant;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.configuration.security.PasswordEncoder;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.ext.AccessToken;
import com.fanyin.service.cache.CacheService;
import com.fanyin.service.common.AccessTokenService;
import com.fanyin.service.system.impl.SystemConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2018/8/14 17:36
 */
@Service("accessTokenService")
@Transactional(rollbackFor = RuntimeException.class,readOnly = true)
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public AccessToken getByAccessKey(String accessKey) {
        Object value = cacheService.getValue(RedisConstant.ACCESS_TOKEN + accessKey);
        if(value != null){
            return (AccessToken)value;
        }
        return null;
    }

    @Override
    public AccessToken getByUserId(int userId) {
        return this.getByAccessKey(String.valueOf(userId));
    }

    @Override
    public void saveByAccessKey(AccessToken token) {
        cacheService.cacheValue(RedisConstant.ACCESS_TOKEN + token.getAccessKey(),token,systemConfigApi.getInt(ConfigConstant.TOKEN_EXPIRE));
    }

    @Override
    public void saveByUserId(AccessToken token) {
        cacheService.cacheValue(RedisConstant.ACCESS_TOKEN + token.getUserId(),token,systemConfigApi.getInt(ConfigConstant.TOKEN_EXPIRE));
    }


    @Override
    public AccessToken createAccessToken(int userId, String requestType) {
        String accessKey = passwordEncoder.encode(userId + requestType + StringUtil.random(16));
        String accessToken = passwordEncoder.encode(userId + accessKey);
        AccessToken token = AccessToken.builder().accessKey(accessKey).accessToken(accessToken).userId(userId).requestType(requestType).build();
        this.saveByAccessKey(token);
        this.saveByUserId(token);
        return token;
    }
}
