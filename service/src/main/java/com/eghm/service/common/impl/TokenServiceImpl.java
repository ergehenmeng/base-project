package com.eghm.service.common.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.dto.ext.RedisToken;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.JsonService;
import com.eghm.service.common.TokenService;
import com.eghm.service.sys.impl.SysConfigApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/8/14 17:36
 */
@Service("tokenService")
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final CacheService cacheService;

    private final SysConfigApi sysConfigApi;

    private final JsonService jsonService;

    @Override
    public RedisToken createToken(Long userId, String channel) {
        String refreshToken = StringUtil.random(32);
        String accessToken = StringUtil.random(64);
        RedisToken redisToken = RedisToken.builder().token(accessToken).userId(userId).channel(channel).refreshToken(refreshToken).build();
        this.cacheToken(redisToken);
        return redisToken;
    }

    @Override
    public RedisToken getByAccessToken(String accessToken) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + accessToken, RedisToken.class);
    }

    @Override
    public RedisToken getByUserId(Long userId) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + userId, RedisToken.class);
    }

    @Override
    public long getTokenExpire(Long userId) {
        return cacheService.getExpire(CacheConstant.ACCESS_TOKEN + userId);
    }

    @Override
    public RedisToken getByRefreshToken(String refreshToken) {
        return cacheService.getValue(CacheConstant.REFRESH_TOKEN + refreshToken, RedisToken.class);
    }

    @Override
    public void cleanToken(String accessToken) {
        cacheService.delete(CacheConstant.ACCESS_TOKEN + accessToken);
    }

    @Override
    public void cleanUserId(Long userId) {
        cacheService.delete(CacheConstant.ACCESS_TOKEN + userId);
    }

    @Override
    public void cleanRefreshToken(String refreshToken) {
        cacheService.delete(CacheConstant.REFRESH_TOKEN + refreshToken);
    }

    @Override
    public void cacheToken(RedisToken redisToken) {
        String tokenJson = jsonService.toJson(redisToken);
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + redisToken.getToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
        // 注意:假如token_expire设置7天,refresh_token_expire为30天时,在第7~30天的时间里,账号重新登陆,
        // refresh_token_expire缓存的用户信息将会无效且不会被立即删除(无法通过userId定位到该缓存数据),
        // 因此:此处过期时间与refresh_token_expire保持一致,在登陆的时候可通过userId定位登陆信息,仅仅方便删除无用缓存,方便强制下线
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + redisToken.getUserId(), tokenJson, sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
        cacheService.setValue(CacheConstant.REFRESH_TOKEN + redisToken.getRefreshToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
    }

    @Override
    public void cacheOfflineToken(RedisToken redisToken, long expire) {
        cacheService.setValue(CacheConstant.FORCE_OFFLINE + redisToken.getToken(), jsonService.toJson(redisToken), expire);
    }

    @Override
    public RedisToken getOfflineToken(String accessToken) {
        return cacheService.getValue(CacheConstant.FORCE_OFFLINE + accessToken, RedisToken.class);
    }

    @Override
    public void cleanOfflineToken(String offlineToken) {
        cacheService.delete(CacheConstant.FORCE_OFFLINE + offlineToken);
    }
}
