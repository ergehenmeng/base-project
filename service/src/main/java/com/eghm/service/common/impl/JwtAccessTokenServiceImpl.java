package com.eghm.service.common.impl;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eghm.configuration.SystemProperties;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.DataType;
import com.eghm.model.SysUser;
import com.eghm.service.common.AccessTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Slf4j
@AllArgsConstructor
public class JwtAccessTokenServiceImpl implements AccessTokenService {

    private final SystemProperties systemProperties;

    @Override
    public String createToken(SysUser user, Long merchantId,  List<String> authList) {
        SystemProperties.ManageProperties.Token token = systemProperties.getManage().getToken();
        return token.getPrefix() + this.doCreateJwt(user, merchantId, token.getExpire(), authList);
    }

    @Override
    public Optional<UserToken> parseToken(String token) {
        JWTVerifier verifier = JWT.require(this.getAlgorithm()).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            UserToken userToken = new UserToken();
            userToken.setId(verify.getClaim("id").asLong());
            userToken.setMerchantId(verify.getClaim("merchantId").asLong());
            userToken.setUserType(verify.getClaim("userType").asInt());
            userToken.setDataType(DataType.of(verify.getClaim("dataType").asInt()));
            userToken.setNickName(verify.getClaim("nickName").asString());
            userToken.setAuthList(verify.getClaim("auth").asList(String.class));
            userToken.setDeptCode(verify.getClaim("deptCode").asString());
            userToken.setDeptList(verify.getClaim("deptList").asList(String.class));
            return Optional.of(userToken);
        } catch (Exception e) {
            log.warn("jwt解析失败,token可能已过期 [{}]", token);
            return Optional.empty();
        }
    }

    /**
     * 根据用户id及渠道创建token
     * @param user  用户信息
     * @param merchantId 商户id
     * @param expireSeconds 过期时间
     * @param authList 权限信息
     * @return jwtToken
     */
    private String doCreateJwt(SysUser user, Long merchantId, int expireSeconds, List<String> authList) {
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("id", user.getId())
                .withClaim("nickName", user.getNickName())
                .withClaim("deptCode", user.getDeptCode())
                .withClaim("userType", user.getUserType())
                .withClaim("dataType", user.getDataType().getValue())
                .withClaim("deptList", user.getDeptList())
                .withClaim("auth", authList);
        if (merchantId != null) {
            builder.withClaim("merchantId", merchantId);
        }
        return builder.withExpiresAt(DateUtil.offsetSecond(DateUtil.date(), expireSeconds)).sign(this.getAlgorithm());
    }

    /**
     * 获取加密签名信息
     * @return secretKey
     */
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(systemProperties.getManage().getToken().getJwtSecret());
    }
}
