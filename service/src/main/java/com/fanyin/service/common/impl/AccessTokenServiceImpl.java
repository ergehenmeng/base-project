package com.fanyin.service.common.impl;


import com.fanyin.common.utils.Sha256Util;
import com.fanyin.ext.AccessToken;
import com.fanyin.service.cache.CacheService;
import com.fanyin.service.common.AccessTokenService;
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

    @Override
    public AccessToken getAccessToken(String accessKey) {
        return cacheService.getAccessToken(accessKey);
    }

    @Override
    public void saveAccessToken(AccessToken token) {
        cacheService.cacheAccessToken(token);
    }

    @Override
    public AccessToken createAccessToken(int userId, String source) {
        String accessKey = Sha256Util.sha256Hmac(userId + source);
        String accessToken = Sha256Util.sha256Hmac(userId + accessKey);
        AccessToken token = new AccessToken();
        token.setAccessKey(accessKey);
        token.setAccessToken(accessToken);
        token.setUserId(userId);
        cacheService.cacheAccessToken(token);
        return token;
    }
}
