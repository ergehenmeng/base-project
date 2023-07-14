package com.eghm.service.common.impl;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.configuration.SystemProperties;
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.SysUser;
import com.eghm.dto.ext.UserToken;
import com.eghm.service.common.AccessTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Slf4j
@Service("jwtAccessTokenService")
@AllArgsConstructor
public class JwtAccessTokenServiceImpl implements AccessTokenService {

    private final SystemProperties systemProperties;

    private final MerchantMapper merchantMapper;

    @Override
    public String createToken(SysUser user, List<String> authList) {
        // 在此处调用merchantService有点鸡肋, 但是在userService调用,会出现循环依赖问题
        Long merchantId = null;
        if (user.getUserType() == SysUser.USER_TYPE_2) {
            merchantId = merchantMapper.getByUserId(user.getId());
            if (merchantId == null) {
                log.error("商户信息未查询到 [{}]", user.getId());
                throw new BusinessException(ErrorCode.MERCHANT_NOT_FOUND);
            }
        }
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
            userToken.setId(verify.getClaim("merchantId").asLong());
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
        return builder.withClaim("id", user.getId())
                .withClaim("nickName", user.getNickName())
                .withClaim("merchantId", merchantId)
                .withClaim("deptCode", user.getDeptCode())
                .withClaim("deptList", user.getDeptList())
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
        return Algorithm.HMAC512(systemProperties.getManage().getToken().getSecretKey());
    }
}
