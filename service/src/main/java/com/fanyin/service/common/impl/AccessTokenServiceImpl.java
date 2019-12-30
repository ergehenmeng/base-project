package com.fanyin.service.common.impl;


import com.fanyin.common.constant.CacheConstant;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.service.cache.CacheService;
import com.fanyin.service.common.AccessTokenService;
import com.fanyin.service.system.impl.SystemConfigApi;
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
    public AccessToken getByAccessKey(String accessKey) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + accessKey,AccessToken.class);
    }

    @Override
    public AccessToken getByUserId(int userId) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + userId,AccessToken.class);
    }

    @Override
    public void saveByAccessKey(AccessToken token) {
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getAccessKey(),token,systemConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
    }

    @Override
    public void saveByUserId(AccessToken token) {
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + token.getUserId(),token,systemConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
    }

}
