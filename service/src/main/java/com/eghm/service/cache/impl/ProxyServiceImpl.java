package com.eghm.service.cache.impl;


import com.eghm.common.utils.DateUtil;
import com.eghm.dao.model.BlackRoster;
import com.eghm.dao.model.SysDict;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.service.sys.SysDictService;
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
@Service("proxyService")
public class ProxyServiceImpl implements ProxyService {

    private SysDictService sysDictService;

    private BlackRosterService blackRosterService;

    @Autowired
    public void setSysDictService(SysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    @Autowired
    public void setBlackRosterService(BlackRosterService blackRosterService) {
        this.blackRosterService = blackRosterService;
    }

    @Override
    public String getDictValue(String nid, Byte hiddenValue) {
        List<SysDict> dictList = sysDictService.getDictByNid(nid);
        for (SysDict dict : dictList) {
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
