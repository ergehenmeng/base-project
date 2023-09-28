package com.eghm.service.common.impl;

import cn.hutool.core.lang.UUID;
import com.eghm.configuration.SystemProperties;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.model.SysUser;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.AccessTokenService;
import com.eghm.service.common.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 默认实现
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
@AllArgsConstructor
public class RedisAccessTokenServiceImpl implements AccessTokenService {

    private final SystemProperties systemProperties;

    private final CacheService cacheService;

    private final JsonService jsonService;

    @Override
    public String createToken(SysUser user, Long merchantId, List<String> authList, List<String> dataList) {
        SystemProperties.ManageProperties.Token token = systemProperties.getManage().getToken();
        return token.getPrefix() + this.doCreateToken(user, merchantId, token.getExpire(), authList, dataList);
    }

    @Override
    public Optional<UserToken> parseToken(String token) {
        String key = CacheConstant.USER_REDIS_TOKEN + token;
        UserToken value = cacheService.getValue(key, UserToken.class);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }


    /**
     * 根据用户id及渠道创建token
     * @param user  用户信息
     * @param merchantId 商户id
     * @param expireSeconds 过期时间
     * @param authList 权限信息
     * @return jwtToken
     */
    private String doCreateToken(SysUser user, Long merchantId, int expireSeconds, List<String> authList, List<String> dataList) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", user.getId());
        hashMap.put("nickName", user.getNickName());
        if (user.getDataType() != null) {
            hashMap.put("dataType", user.getDataType().getValue());
        }
        hashMap.put("userType", user.getUserType());
        hashMap.put("dataList", dataList);
        hashMap.put("authList", authList);
        hashMap.put("merchantId", merchantId);
        hashMap.put("deptCode", user.getDeptCode());
        String token = UUID.randomUUID().toString(true);
        String key = CacheConstant.USER_REDIS_TOKEN + token;
        cacheService.setValue(key, jsonService.toJson(hashMap), expireSeconds);
        return token;
    }

}
