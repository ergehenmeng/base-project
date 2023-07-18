package com.eghm.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.configuration.SystemProperties;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.DataType;
import com.eghm.model.SysUser;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.AccessTokenService;
import com.eghm.service.common.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
        Map<Object, Object> hasMap = cacheService.getHasMap(key);
        if (CollUtil.isEmpty(hasMap)) {
            return Optional.empty();
        }
        UserToken userToken = new UserToken();
        userToken.setId((long) hasMap.get("id"));
        userToken.setNickName((String) hasMap.get("nickName"));
        userToken.setMerchantId((Long) hasMap.get("merchantId"));
        userToken.setDeptCode((String) hasMap.get("deptCode"));
        userToken.setUserType((Integer) hasMap.get("userType"));
        userToken.setDataType(DataType.of(Integer.parseInt(hasMap.get("dataType").toString())));
        userToken.setDataList(jsonService.fromJsonList(hasMap.get("deptList").toString(), String.class));
        userToken.setAuthList(jsonService.fromJsonList(hasMap.get("auth").toString(), String.class));
        return Optional.of(userToken);
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
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("id", String.valueOf(user.getId()));
        hashMap.put("nickName", String.valueOf(user.getNickName()));
        hashMap.put("merchantId", String.valueOf(merchantId));
        hashMap.put("deptCode", user.getDeptCode());
        hashMap.put("dataType", String.valueOf(user.getDataType().getValue()));
        hashMap.put("userType", String.valueOf(user.getUserType()));
        hashMap.put("deptList", jsonService.toJson(dataList));
        hashMap.put("auth", jsonService.toJson(authList));
        String key = CacheConstant.USER_REDIS_TOKEN + UUID.randomUUID();
        cacheService.setHashMap(key, hashMap);
        cacheService.setExpire(key, expireSeconds);
        return key;
    }

}
