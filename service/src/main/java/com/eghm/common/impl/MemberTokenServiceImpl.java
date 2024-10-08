package com.eghm.common.impl;


import cn.hutool.core.util.IdUtil;
import com.eghm.cache.CacheService;
import com.eghm.common.JsonService;
import com.eghm.common.MemberTokenService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.MemberToken;
import com.eghm.enums.Channel;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/8/14 17:36
 */
@Slf4j
@Service("tokenService")
@AllArgsConstructor
public class MemberTokenServiceImpl implements MemberTokenService {

    private final JsonService jsonService;

    private final CacheService cacheService;

    private final SysConfigApi sysConfigApi;

    @Override
    public MemberToken createToken(Long memberId, String channel) {
        MemberToken memberToken = MemberToken.builder().token(IdUtil.fastSimpleUUID()).memberId(memberId).channel(channel).refreshToken(IdUtil.fastSimpleUUID()).build();
        this.cacheToken(memberToken);
        this.cacheRefreshToken(memberToken);
        return memberToken;
    }

    @Override
    public MemberToken getByAccessToken(String accessToken) {
        return cacheService.getValue(CacheConstant.TOKEN + accessToken, MemberToken.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        String refreshKey = CacheConstant.REFRESH_TOKEN + refreshToken;
        MemberToken memberToken = cacheService.getValue(refreshKey, MemberToken.class);
        if (memberToken == null) {
            log.error("refreshToken已经过期 [{}]", refreshToken);
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRE);
        }
        MemberToken member = this.getByAccessToken(memberToken.getToken());
        if (member != null) {
            log.info("旧token依旧有效,无需重复刷新 [{}]", memberToken.getToken());
            return member.getToken();
        }
        String token = IdUtil.fastSimpleUUID();
        memberToken.setToken(token);
        this.cacheToken(memberToken);
        long expire = cacheService.getExpire(refreshKey);
        cacheService.setValue(refreshKey, jsonService.toJson(memberToken), expire);
        return token;
    }

    @Override
    public void cleanToken(Long memberId, String channel) {
        if (channel != null) {
            MemberToken memberToken = cacheService.getHashValue(CacheConstant.MEMBER_TOKEN_MAPPING, memberId + CommonConstant.SPECIAL_SPLIT + channel, MemberToken.class);
            if (memberToken != null) {
                this.doCleanToken(memberId, memberToken.getToken(), memberToken.getRefreshToken(), channel);
            }
            return;
        }
        // 删除所有客户端的token
        for (Channel value : Channel.values()) {
            this.cleanToken(memberId, value.name());
        }
    }

    /**
     * 删除指定客户端所有跟token相关的信息
     *
     * @param memberId 会员id
     * @param accessToken token
     * @param refreshToken refreshToken
     * @param channel 对应渠道
     */
    private void doCleanToken(Long memberId, String accessToken, String refreshToken, String channel) {
        cacheService.deleteHashKey(CacheConstant.MEMBER_TOKEN_MAPPING, memberId + CommonConstant.SPECIAL_SPLIT + channel);
        cacheService.delete(CacheConstant.REFRESH_TOKEN + refreshToken);
        cacheService.delete(CacheConstant.TOKEN + accessToken);
    }

    /**
     * 缓存登陆时所有需要保存的用户信息
     *
     * @param memberToken 用户登陆信息
     */
    private void cacheToken(MemberToken memberToken) {
        String tokenJson = jsonService.toJson(memberToken);
        cacheService.setValue(CacheConstant.TOKEN + memberToken.getToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
        // 注意:假如token_expire设置7天,refresh_token_expire为30天时,在第7~30天的时间里,账号重新登陆,
        // refresh_token_expire缓存的用户信息将会无效且不会被立即删除(无法通过memberId定位到该缓存数据),
        // 因此:此处过期时间与refresh_token_expire保持一致,在登陆的时候可通过memberId定位登陆信息,仅仅方便删除无用缓存,方便强制下线
        cacheService.setHashValue(CacheConstant.MEMBER_TOKEN_MAPPING, memberToken.getMemberId() + CommonConstant.SPECIAL_SPLIT + memberToken.getChannel(), tokenJson);
    }

    /**
     * 保存refreshToken
     *
     * @param memberToken 用户信息
     */
    private void cacheRefreshToken(MemberToken memberToken) {
        cacheService.setValue(CacheConstant.REFRESH_TOKEN + memberToken.getRefreshToken(), jsonService.toJson(memberToken), sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
    }

}
