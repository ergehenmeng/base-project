package com.fanyin.service.common.impl;


import com.fanyin.common.constant.CacheConstant;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.service.common.AccessTokenService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/8/14 17:36
 */
@Service("accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {


    @Override
    @Cacheable(cacheNames = CacheConstant.ACCESS_TOKEN,key = "#p0",unless = "#result == null")
    public AccessToken getByAccessKey(String accessKey) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.ACCESS_TOKEN,key = "#p0",unless = "#result == null")
    public AccessToken getByUserId(int userId) {
        return null;
    }

    @Override
    @CachePut(cacheNames = CacheConstant.ACCESS_TOKEN,key = "#token.accessKey",cacheManager = "tokenCacheManager")
    public AccessToken saveByAccessKey(AccessToken token) {
        return token;
    }

    @Override
    @CachePut(cacheNames = CacheConstant.ACCESS_TOKEN,key = "#token.userId",cacheManager = "tokenCacheManager")
    public AccessToken saveByUserId(AccessToken token) {
        return token;
    }

}
