package com.eghm.service.common.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.constants.ConfigConstant;
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

    @Override
    public AccessToken getByAccessToken(String accessToken) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + accessToken, AccessToken.class);
    }

    @Override
    public String getByUserId(int userId) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + userId, String.class);
    }

    @Override
    public void saveByAccessToken(AccessToken token) {
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getAccessToken(), token, systemConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
    }

    @Override
    public void saveByUserId(int userId, String accessToken) {
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + userId, accessToken, systemConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
    }

}
