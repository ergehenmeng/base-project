package com.eghm.service.common.impl;


import cn.hutool.core.util.IdUtil;
import com.eghm.constant.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.MemberToken;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.JsonService;
import com.eghm.service.common.TokenService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.StringUtil;
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
        String accessToken = StringUtil.randomLowerCase(64);
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
        return cacheService.getValue(CacheConstant.ACCESS_TOKEN + memberId, MemberToken.class);
    }

    @Override
    public long getTokenExpire(Long memberId) {
        return cacheService.getExpire(CacheConstant.ACCESS_TOKEN + memberId);
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
        cacheService.delete(CacheConstant.ACCESS_TOKEN + memberId);
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
        cacheService.setValue(CacheConstant.ACCESS_TOKEN + memberToken.getMemberId(), tokenJson, sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
        cacheService.setValue(CacheConstant.REFRESH_TOKEN + memberToken.getRefreshToken(), tokenJson, sysConfigApi.getLong(ConfigConstant.REFRESH_TOKEN_EXPIRE));
    }

    @Override
    public void cacheOfflineToken(MemberToken memberToken, long expire) {
        cacheService.setValue(CacheConstant.FORCE_OFFLINE + memberToken.getToken(), jsonService.toJson(memberToken), expire);
    }

    @Override
    public MemberToken getOfflineToken(String accessToken) {
        return cacheService.getValue(CacheConstant.FORCE_OFFLINE + accessToken, MemberToken.class);
    }

    @Override
    public void cleanOfflineToken(String offlineToken) {
        cacheService.delete(CacheConstant.FORCE_OFFLINE + offlineToken);
    }
}
