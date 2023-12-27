package com.eghm.service.common.impl;


import cn.hutool.core.util.IdUtil;
import com.eghm.constant.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.MemberToken;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.JsonService;
import com.eghm.service.common.TokenService;
import com.eghm.service.sys.impl.SysConfigApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/8/14 17:36
 */
@Service("tokenService")
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final CacheService cacheService;

    private final SysConfigApi sysConfigApi;

    private final JsonService jsonService;

    @Override
    public MemberToken createToken(Long memberId, String channel) {
        String refreshToken = IdUtil.fastSimpleUUID();
        String accessToken = IdUtil.fastSimpleUUID();
        MemberToken memberToken = MemberToken.builder().token(accessToken).memberId(memberId).channel(channel).refreshToken(refreshToken).build();
        this.cacheToken(memberToken);
        return memberToken;
    }

    @Override
    public MemberToken getByAccessToken(String accessToken) {
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + accessToken, MemberToken.class);
    }

    @Override
    public MemberToken getByMemberId(Long memberId) {
        return cacheService.getHashValue(CacheConstant.MEMBER_TOKEN_MAPPING, String.valueOf(memberId), MemberToken.class);
    }

    @Override
    public MemberToken getByRefreshToken(String refreshToken) {
        return cacheService.getValue(CacheConstant.REFRESH_TOKEN + refreshToken, MemberToken.class);
    }

    @Override
    public void cleanToken(String accessToken) {
        cacheService.delete(CacheConstant.ACCESS_TOKEN + accessToken);
    }

    @Override
    public void cleanMemberId(Long memberId) {
        cacheService.deleteHashKey(CacheConstant.MEMBER_TOKEN_MAPPING, String.valueOf(memberId));
    }

    @Override
    public void cleanRefreshToken(String refreshToken) {
        cacheService.delete(CacheConstant.REFRESH_TOKEN + refreshToken);
    }

    @Override
    public void cacheToken(MemberToken memberToken) {
        String tokenJson = jsonService.toJson(memberToken);
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + memberToken.getToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.TOKEN_EXPIRE));
        // 注意:假如token_expire设置7天,refresh_token_expire为30天时,在第7~30天的时间里,账号重新登陆,
        // refresh_token_expire缓存的用户信息将会无效且不会被立即删除(无法通过memberId定位到该缓存数据),
        // 因此:此处过期时间与refresh_token_expire保持一致,在登陆的时候可通过memberId定位登陆信息,仅仅方便删除无用缓存,方便强制下线
        cacheService.setHashValue(CacheConstant.MEMBER_TOKEN_MAPPING, String.valueOf(memberToken.getMemberId()), tokenJson);
        cacheService.setValue(CacheConstant.REFRESH_TOKEN + memberToken.getRefreshToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
    }

}
