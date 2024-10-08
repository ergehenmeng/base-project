package com.eghm.common.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.cache.CacheService;
import com.eghm.common.JsonService;
import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.CacheConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.model.SysUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 默认实现
 *
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
@AllArgsConstructor
public class RedisUserTokenServiceImpl implements UserTokenService {

    private final JsonService jsonService;

    private final CacheService cacheService;

    private final SystemProperties systemProperties;

    @Override
    public String createToken(SysUser user, Long merchantId, List<String> authList, List<String> dataList) {
        SystemProperties.ManageProperties.Token tokenConfig = systemProperties.getManage().getToken();
        String token = this.doCreateToken(user, merchantId, tokenConfig.getExpire(), authList, dataList);
        this.clearSetToken(user.getId(), token);
        return tokenConfig.getTokenPrefix() + token;
    }

    @Override
    public Optional<UserToken> parseToken(String token) {
        String key = CacheConstant.USER_TOKEN + token;
        UserToken value = cacheService.getValue(key, UserToken.class);
        if (value == null) {
            return Optional.empty();
        }
        value.setToken(token);
        return Optional.of(value);
    }

    @Override
    public void logout(String token) {
        if (token == null) {
            return;
        }
        cacheService.delete(CacheConstant.USER_TOKEN + token);
    }

    /**
     * 根据用户id及渠道创建token
     *
     * @param user          用户信息
     * @param merchantId    商户id
     * @param expireSeconds 过期时间
     * @param authList      权限信息
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
        String token = IdUtil.fastSimpleUUID();
        String key = CacheConstant.USER_TOKEN + token;
        cacheService.setValue(key, jsonService.toJson(hashMap), expireSeconds);
        return token;
    }

    /**
     * 由于每次登录都会生成新的token, 因此为了防止产生过多的token以至于占用过多内存, 需要在登录时清除旧的token, 同时保存新的
     *
     * @param userId 用户id
     * @param token  新token
     */
    private void clearSetToken(Long userId, String token) {
        String oldToken = cacheService.getHashValue(CacheConstant.USER_TOKEN_MAPPING, String.valueOf(userId));
        if (oldToken != null) {
            cacheService.delete(CacheConstant.USER_TOKEN + oldToken);
        }
        cacheService.setHashValue(CacheConstant.USER_TOKEN_MAPPING, String.valueOf(userId), token);
    }
}
