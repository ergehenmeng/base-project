package com.eghm.service.common.impl;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eghm.common.enums.Channel;
import com.eghm.configuration.ManageProperties;
import com.eghm.model.dto.ext.JwtToken;
import com.eghm.service.common.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Service("jwtTokenService")
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    private ManageProperties manageProperties;

    @Autowired
    public void setManageProperties(ManageProperties manageProperties) {
        this.manageProperties = manageProperties;
    }

    @Override
    public String createRefreshToken(Long userId, String channel) {
        return this.doCreateJwt(userId, channel, manageProperties.getJwt().getRefreshExpire(), null);
    }

    @Override
    public String createToken(Long userId, String channel, List<String> authList) {
        return this.doCreateJwt(userId, channel, manageProperties.getJwt().getExpire(), authList);
    }

    @Override
    public Optional<JwtToken> parseToken(String token) {
        JWTVerifier verifier = JWT.require(this.getAlgorithm()).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            JwtToken jwtToken = new JwtToken();
            jwtToken.setId(verify.getClaim("id").asLong());
            jwtToken.setChannel(Channel.valueOf(verify.getClaim("channel").asString()));
            jwtToken.setAuthList(verify.getClaim("auth").asList(String.class));
            return Optional.of(jwtToken);
        } catch (Exception e) {
            log.warn("jwt解析失败,token可能已过期 [{}]", token);
            return Optional.empty();
        }
    }

    /**
     * 根据用户id及渠道创建token
     * @param userId 用户id
     * @param channel 渠道
     * @param expireSeconds 过期时间
     */
    private String doCreateJwt(Long userId, String channel, int expireSeconds, List<String> authList) {
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("id", userId)
                .withClaim("channel", channel)
                .withClaim("r", System.currentTimeMillis())
                .withClaim("auth", authList)
                .withExpiresAt(DateUtil.offsetSecond(DateUtil.date(), expireSeconds))
                .sign(this.getAlgorithm());
    }

    /**
     * 获取加密签名信息
     * @return secretKey
     */
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(manageProperties.getJwt().getSecretKey());
    }
}
