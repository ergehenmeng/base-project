package com.eghm.service.cache.impl;


import com.eghm.common.utils.DateUtil;
import com.eghm.dao.model.system.BlackRoster;
import com.eghm.dao.model.system.SystemDict;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.system.BlackRosterService;
import com.eghm.service.system.SystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 该类为全局Bean在Aop中无效时,额外存放类
 * 由于@AsyncResponse,@CachePut等相关aop注解在同一个类方法间进行调用时无法生效
 * 可全部放到本类中维护
 *
 * @author 二哥很猛
 * @date 2018/10/11 13:47
 */
@Service("cacheProxyService")
public class CacheProxyServiceImpl implements CacheProxyService {

    @Autowired
    private SystemDictService systemDictService;

    @Autowired
    private BlackRosterService blackRosterService;

    @Override
    public String getDictValue(String nid, Byte hiddenValue) {
        List<SystemDict> dictList = systemDictService.getDictByNid(nid);
        for (SystemDict dict : dictList) {
            if (dict.getHiddenValue().equals(hiddenValue)) {
                return dict.getShowValue();
            }
        }
        return null;
    }


    @Override
    public boolean isInterceptIp(String ip) {
        List<BlackRoster> availableList = blackRosterService.getAvailableList();
        if (!CollectionUtils.isEmpty(availableList)) {
            Date now = DateUtil.getNow();
            return availableList.stream().anyMatch(blackRoster -> ip.equals(blackRoster.getIp()) && (blackRoster.getEndTime() == null || now.before(blackRoster.getEndTime())));
        }
        return false;
    }
}
