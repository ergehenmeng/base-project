package com.eghm.common.impl;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.DataType;
import com.eghm.enums.UserType;
import com.eghm.model.SysUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@Slf4j
@AllArgsConstructor
public class JwtUserTokenServiceImpl implements UserTokenService {

    private final SystemProperties systemProperties;

    @Override
    public String createToken(SysUser user, Long merchantId, List<String> authList, List<String> dataList) {
        SystemProperties.ManageProperties.Token token = systemProperties.getManage().getToken();
        return token.getTokenPrefix() + this.doCreateJwt(user, merchantId, token.getExpire(), authList, dataList);
    }

    @Override
    public Optional<UserToken> parseToken(String token) {
        JWTVerifier verifier = JWT.require(this.getAlgorithm()).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            UserToken userToken = new UserToken();
            userToken.setId(verify.getClaim("id").asLong());
            userToken.setMerchantId(verify.getClaim("merchantId").asLong());
            userToken.setUserType(UserType.of(verify.getClaim("userType").asInt()));
            userToken.setDataType(DataType.of(verify.getClaim("dataType").asInt()));
            userToken.setNickName(verify.getClaim("nickName").asString());
            userToken.setAuthList(verify.getClaim("auth").asList(String.class));
            userToken.setDeptCode(verify.getClaim("deptCode").asString());
            userToken.setDataList(verify.getClaim("dataList").asList(String.class));
            userToken.setToken(token);
            return Optional.of(userToken);
        } catch (Exception e) {
            log.warn("jwt解析失败,token可能已过期 [{}]", token);
            return Optional.empty();
        }
    }

    @Override
    public void logout(String token) {
        log.info("用户主动退出系统啦~ [{}]", token);
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
    private String doCreateJwt(SysUser user, Long merchantId, int expireSeconds, List<String> authList, List<String> dataList) {
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("id", user.getId())
                .withClaim("nickName", user.getNickName())
                .withClaim("deptCode", user.getDeptCode())
                .withClaim("userType", user.getUserType().getValue())
                .withClaim("dataType", user.getDataType().getValue())
                .withClaim("dataList", dataList)
                .withClaim("auth", authList);
        if (merchantId != null) {
            builder.withClaim("merchantId", merchantId);
        }
        return builder.withExpiresAt(DateUtil.offsetSecond(DateUtil.date(), expireSeconds)).sign(this.getAlgorithm());
    }

    /**
     * 获取加密签名信息
     *
     * @return secretKey
     */
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(systemProperties.getManage().getToken().getJwtSecret());
    }
}
