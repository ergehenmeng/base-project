package com.eghm.service.common.impl;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.SystemProperties;
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.Merchant;
import com.eghm.model.SysOperator;
import com.eghm.model.dto.ext.JwtOperator;
import com.eghm.service.business.MerchantService;
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

    private final MerchantMapper merchantMapper;

    @Override
    public String createToken(SysOperator operator, List<String> authList) {
        // 在此处调用merchantService有点鸡肋, 但是在operatorService调用,会出现循环依赖问题
        Long merchantId = null;
        if (operator.getUserType() == SysOperator.USER_TYPE_2) {
            merchantId = merchantMapper.getOperatorId(operator.getId());
            if (merchantId == null) {
                log.error("商户信息未查询到 [{}]", operator.getId());
                throw new BusinessException(ErrorCode.MERCHANT_NOT_FOUND);
            }
        }
        SystemProperties.ManageProperties.Jwt jwt = systemProperties.getManage().getJwt();
        return jwt.getPrefix() + this.doCreateJwt(operator, merchantId, jwt.getExpire(), authList);
    }

    @Override
    public Optional<JwtOperator> parseToken(String token) {
        JWTVerifier verifier = JWT.require(this.getAlgorithm()).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            JwtOperator jwtOperator = new JwtOperator();
            jwtOperator.setId(verify.getClaim("id").asLong());
            jwtOperator.setId(verify.getClaim("merchantId").asLong());
            jwtOperator.setNickName(verify.getClaim("nickName").asString());
            jwtOperator.setAuthList(verify.getClaim("auth").asList(String.class));
            jwtOperator.setDeptCode(verify.getClaim("deptCode").asString());
            jwtOperator.setDeptList(verify.getClaim("deptList").asList(String.class));
            return Optional.of(jwtOperator);
        } catch (Exception e) {
            log.warn("jwt解析失败,token可能已过期 [{}]", token);
            return Optional.empty();
        }
    }

    /**
     * 根据用户id及渠道创建token
     * @param operator  用户信息
     * @param merchantId 商户id
     * @param expireSeconds 过期时间
     * @param authList 权限信息
     * @return jwtToken
     */
    private String doCreateJwt(SysOperator operator, Long merchantId, int expireSeconds, List<String> authList) {
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("id", operator.getId())
                .withClaim("nickName", operator.getNickName())
                .withClaim("merchantId", merchantId)
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
