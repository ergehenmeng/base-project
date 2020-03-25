package com.eghm.service.common.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.Md5Util;
import com.eghm.common.utils.StringUtil;
import com.eghm.configuration.security.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.ext.Token;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.TokenService;
import com.eghm.service.system.impl.SystemConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/8/14 17:36
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Autowired
    private Encoder encoder;

    @Override
    public Token createToken(int userId, String channel) {
        String accessToken = encoder.encode(userId + channel + StringUtil.random(16));
        String refreshToken = StringUtil.random(32);
        String secret = StringUtil.random(32);
        Token token = Token.builder().secret(secret).accessToken(accessToken).userId(userId).channel(channel).refreshToken(refreshToken).build();
        this.cacheByAccessToken(token);
        this.cacheByUserId(token);
        this.cacheByRefreshToken(token);
        return token;
    }

    @Override
    public Token getByAccessToken(String accessToken) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + accessToken, Token.class);
    }

    @Override
    public Token getByUserId(int userId) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + userId, Token.class);
    }

    @Override
    public Token getByRefreshToken(String refreshToken) {
        return cacheService.getValue(CacheConstant.REFRESH_TOKEN + refreshToken, Token.class);
    }

    @Override
    public void cacheByAccessToken(Token token) {
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getAccessToken(), token, systemConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
    }

    @Override
    public void cacheByUserId(Token token) {
        //注意:假如token_expire设置7天,refresh_token_expire为30天时,在第7~30天的时间里,账号重新登陆,refresh_token_expire缓存的用户信息将会无效且不会被立即删除(无法通过userId定位到该缓存数据),
        //因此:此处过期时间与refresh_token_expire保持一致,在登陆的时候可通过userId定位登陆信息,以便于删除无用缓存
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getUserId(), token, systemConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
    }

    @Override
    public void cacheByRefreshToken(Token token) {
        cacheService.setValue(CacheConstant.REFRESH_TOKEN + token.getRefreshToken(), token, systemConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
    }

    @Override
    public void cleanAccessToken(String accessToken) {
        cacheService.delete(CacheConstant.ACCESS_TOKEN + accessToken);
    }

    @Override
    public void cleanUserId(int userId) {
        cacheService.delete(CacheConstant.ACCESS_TOKEN + userId);
    }

    @Override
    public void cleanRefreshToken(String refreshToken) {
        cacheService.delete(CacheConstant.REFRESH_TOKEN + refreshToken);
    }
}
