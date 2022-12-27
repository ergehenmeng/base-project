package com.eghm.service.common.impl;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eghm.configuration.SystemProperties;
import com.eghm.model.Merchant;
import com.eghm.model.SysOperator;
import com.eghm.model.dto.ext.JwtManage;
import com.eghm.service.common.JwtTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Service("jwtTokenService")
@Slf4j
@AllArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private SystemProperties systemProperties;

    @Override
    public String createToken(SysOperator operator, List<String> authList) {
        SystemProperties.ManageProperties.Jwt jwt = systemProperties.getManage().getJwt();
        return jwt.getPrefix() + this.doCreateJwt(operator, jwt.getExpire(), authList);
    }

    @Override
    public String createToken(Merchant merchant, List<String> authList) {
        SystemProperties.ManageProperties.Jwt jwt = systemProperties.getManage().getJwt();
        JWTCreator.Builder builder = JWT.create();
        return jwt.getPrefix() + builder.withClaim("id", merchant.getId())
                .withClaim("nickName", merchant.getNickName())
                .withClaim("r", System.currentTimeMillis())
                .withClaim("auth", authList)
                .withExpiresAt(DateUtil.offsetSecond(DateUtil.date(), jwt.getExpire()))
                .sign(this.getAlgorithm());
    }

    @Override
    public Optional<JwtManage> parseToken(String token) {
        JWTVerifier verifier = JWT.require(this.getAlgorithm()).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            JwtManage jwtManage = new JwtManage();
            jwtManage.setId(verify.getClaim("id").asLong());
            jwtManage.setNickName(verify.getClaim("nickName").asString());
            jwtManage.setAuthList(verify.getClaim("auth").asList(String.class));
            jwtManage.setDeptCode(verify.getClaim("deptCode").asString());
            jwtManage.setDeptList(verify.getClaim("deptList").asList(String.class));
            return Optional.of(jwtManage);
        } catch (Exception e) {
            log.warn("jwt解析失败,token可能已过期 [{}]", token);
            return Optional.empty();
        }
    }

    /**
     * 根据用户id及渠道创建token
     * @param operator  用户信息
     * @param expireSeconds 过期时间
     * @param authList 权限信息
     * @return jwtToken
     */
    private String doCreateJwt(SysOperator operator, int expireSeconds, List<String> authList) {
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("id", operator.getId())
                .withClaim("nickName", operator.getOperatorName())
                .withClaim("deptCode", operator.getDeptCode())
                .withClaim("deptList", operator.getDeptList())
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
        return Algorithm.HMAC512(systemProperties.getManage().getJwt().getSecretKey());
    }
}
