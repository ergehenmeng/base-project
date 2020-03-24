package com.eghm.service.common.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.Md5Util;
import com.eghm.common.utils.StringUtil;
import com.eghm.configuration.security.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.user.User;
import com.eghm.model.ext.AccessToken;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.AccessTokenService;
import com.eghm.service.system.impl.SystemConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/8/14 17:36
 */
@Service("accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Autowired
    private Encoder encoder;

    @Override
    public AccessToken createAccessToken(int userId, String channel) {
        String signKey = encoder.encode(userId + channel + StringUtil.random(16));
        String accessToken = encoder.encode(userId + signKey);
        String refreshToken = encoder.encode(Md5Util.md5(signKey + accessToken));
        AccessToken token = AccessToken.builder().signKey(signKey).accessToken(accessToken).userId(userId).channel(channel).refreshToken(refreshToken).build();
        this.cacheByAccessToken(token);
        this.cacheByUserId(token.getUserId(), accessToken);
        this.cacheRefreshToken(refreshToken, token);
        return token;
    }

    @Override
    public AccessToken getByAccessToken(String accessToken) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + accessToken, AccessToken.class);
    }

    @Override
    public String getByUserId(int userId) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + userId, String.class);
    }

    @Override
    public AccessToken getByRefreshToken(String refreshToken) {
        return cacheService.getValue(CacheConstant.REFRESH_TOKEN + refreshToken, AccessToken.class);
    }

    @Override
    public void cacheByAccessToken(AccessToken token) {
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getAccessToken(), token, systemConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
    }

    @Override
    public void cacheByUserId(int userId, String accessToken) {
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + userId, accessToken, systemConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
    }

    @Override
    public void cacheRefreshToken(String refreshToken, AccessToken token) {
        cacheService.setValue(CacheConstant.REFRESH_TOKEN + refreshToken, token, systemConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
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
