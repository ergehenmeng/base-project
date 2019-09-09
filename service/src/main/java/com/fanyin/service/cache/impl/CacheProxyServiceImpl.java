package com.fanyin.service.cache.impl;


import com.fanyin.common.utils.DateUtil;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.configuration.security.Encoder;
import com.fanyin.dao.model.system.BlackRoster;
import com.fanyin.dao.model.system.SystemDict;
import com.fanyin.dao.model.user.User;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.service.cache.CacheProxyService;
import com.fanyin.service.common.AccessTokenService;
import com.fanyin.service.system.BlackRosterService;
import com.fanyin.service.system.SystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 该类为全局Bean在Aop中无效时,额外存放类
 * 由于@Async,@CachePut等相关aop注解在同一个类方法间进行调用时无法生效
 * 可全部放到本类中维护
 * @author 二哥很猛
 * @date 2018/10/11 13:47
 */
@Service("cacheProxyService")
@Transactional(rollbackFor = RuntimeException.class,readOnly = true)
public class CacheProxyServiceImpl implements CacheProxyService {

    @Autowired
    private SystemDictService systemDictService;

    @Autowired
    private Encoder encoder;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private BlackRosterService blackRosterService;

    @Override
    public String getDictValue(String nid, Byte hiddenValue) {
        List<SystemDict> dictList = systemDictService.getDictByNid(nid);
        for (SystemDict dict : dictList){
            if(dict.getHiddenValue().equals(hiddenValue)){
                return dict.getShowValue();
            }
        }
        return null;
    }

    @Override
    public AccessToken createAccessToken(User user,String channel) {
        String accessKey = encoder.encode(user.getId() + channel + StringUtil.random(16));
        String accessToken = encoder.encode(user.getId() + accessKey);
        AccessToken token = AccessToken.builder().accessKey(accessKey).accessToken(accessToken).userId(user.getId()).channel(channel).build();
        token = accessTokenService.saveByAccessKey(token);
        return accessTokenService.saveByUserId(token);
    }

    @Override
    public boolean isInterceptIp(String ip) {
        List<BlackRoster> availableList = blackRosterService.getAvailableList();
        if(!CollectionUtils.isEmpty(availableList)){
            Date now = DateUtil.getNow();
            return availableList.stream().anyMatch(blackRoster -> ip.equals(blackRoster.getIp()) && (blackRoster.getEndTime() == null || now.before(blackRoster.getEndTime())));
        }
        return false;
    }
}
