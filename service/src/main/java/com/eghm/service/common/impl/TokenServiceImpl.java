package com.eghm.service.common.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.dto.ext.Token;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.JsonService;
import com.eghm.service.common.TokenService;
import com.eghm.service.sys.impl.SysConfigApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/8/14 17:36
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    private CacheService cacheService;

    private SysConfigApi sysConfigApi;

    private JsonService jsonService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Autowired
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @Override
    public Token createToken(int userId, String channel) {
        String refreshToken = StringUtil.random(32);
        String accessToken = StringUtil.random(64);
        Token token = Token.builder().token(accessToken).userId(userId).channel(channel).refreshToken(refreshToken).build();
        this.cacheToken(token);
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
    public long getTokenExpire(int userId) {
        return cacheService.getExpire(CacheConstant.ACCESS_TOKEN + userId);
    }

    @Override
    public Token getByRefreshToken(String refreshToken) {
        return cacheService.getValue(CacheConstant.REFRESH_TOKEN + refreshToken, Token.class);
    }

    @Override
    public void cleanToken(String accessToken) {
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

    @Override
    public void cacheToken(Token token) {
        String tokenJson = jsonService.toJson(token);
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
        // 注意:假如token_expire设置7天,refresh_token_expire为30天时,在第7~30天的时间里,账号重新登陆,
        // refresh_token_expire缓存的用户信息将会无效且不会被立即删除(无法通过userId定位到该缓存数据),
        // 因此:此处过期时间与refresh_token_expire保持一致,在登陆的时候可通过userId定位登陆信息,仅仅方便删除无用缓存,方便强制下线
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getUserId(), tokenJson, sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
        cacheService.setValue(CacheConstant.REFRESH_TOKEN + token.getRefreshToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
    }

    @Override
    public void cacheOfflineToken(Token token, long expire) {
        cacheService.setValue(CacheConstant.FORCE_OFFLINE + token.getToken(), jsonService.toJson(token), expire);
    }

    @Override
    public Token getOfflineToken(String accessToken) {
        return cacheService.getValue(CacheConstant.FORCE_OFFLINE + accessToken, Token.class);
    }

    @Override
    public void cleanOfflineToken(String offlineToken) {
        cacheService.delete(CacheConstant.FORCE_OFFLINE + offlineToken);
    }
}
