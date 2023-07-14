package com.eghm.service.common.impl;

import com.eghm.dto.ext.UserToken;
import com.eghm.model.SysUser;
import com.eghm.service.common.AccessTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
@Service("redisAccessTokenService")
@AllArgsConstructor
public class RedisAccessTokenServiceImpl implements AccessTokenService {

    @Override
    public String createToken(SysUser user, List<String> authList) {
        return null;
    }

    @Override
    public Optional<UserToken> parseToken(String token) {
        return Optional.empty();
    }
}
