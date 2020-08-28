package com.eghm.service.common.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eghm.common.enums.Channel;
import com.eghm.common.utils.DateUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.ext.JwtToken;
import com.eghm.service.common.JwtTokenService;
import com.eghm.service.sys.impl.SysConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Service("jwtTokenService")
public class JwtTokenServiceImpl implements JwtTokenService {

    private SysConfigApi sysConfigApi;

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Override
    public String createToken(int userId, String channel) {
        int expireSeconds = sysConfigApi.getInt(ConfigConstant.TOKEN_EXPIRE);
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("userId", userId)
                .withClaim("channel", channel)
                .withExpiresAt(DateUtil.addSeconds(DateUtil.getNow(), expireSeconds))
                .sign(this.getAlgorithm());
    }

    @Override
    public JwtToken verifyToken(String token) {
        JWTVerifier verifier = JWT.require(this.getAlgorithm()).build();
        DecodedJWT verify = verifier.verify(token);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setUserId(verify.getClaim("userId").asInt());
        jwtToken.setChannel(Channel.valueOf(verify.getClaim("channel").asString()));
        return jwtToken;
    }

    /**
     * 获取加密签名信息
     * @return secretKey
     */
    private Algorithm getAlgorithm() {
        String secretKey = sysConfigApi.getString(ConfigConstant.JWT_SECRET_KEY);
        return Algorithm.HMAC512(secretKey);
    }
}
