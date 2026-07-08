package com.eghm.service.sys.impl;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.net.Ipv4Util;
import com.eghm.dto.ext.Page;
import com.eghm.cache.CacheService;
import com.eghm.constants.CacheConstant;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.roster.BlackRosterAddRequest;
import com.eghm.service.sys.BlackRosterQueryGateway;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.sys.model.BlackRoster;
import com.eghm.sys.repository.BlackRosterRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:45
 */
@Slf4j
@AllArgsConstructor
@Service("blackRosterService")
public class BlackRosterServiceImpl implements BlackRosterService {

    private final CacheService cacheService;

    private final BlackRosterRepository blackRosterRepository;

    private final BlackRosterQueryGateway blackRosterQueryGateway;

    @Override
    public Page<BlackRoster> getByPage(PagingQuery request) {
        return blackRosterQueryGateway.getByPage(request);
    }

    @Override
    public void addBlackRoster(BlackRosterAddRequest request) {
        BlackRoster blackRoster = DataUtil.copy(request, BlackRoster.class);
        blackRoster.assertRangeValid();
        blackRosterRepository.save(blackRoster);
    }

    @Override
    public void deleteById(Long id) {
        blackRosterRepository.deleteById(id);
    }

    @Override
    public void reloadBlackRoster() {
        cacheService.delete(CacheConstant.BLACK_ROSTER);
        List<BlackRoster> rosterList = blackRosterRepository.findAll();
        for (BlackRoster roster : rosterList) {
            cacheService.setSetValue(CacheConstant.BLACK_ROSTER, roster.toCacheValues());
        }
    }

    @Override
    public boolean isInterceptIp(String ip) {
        return cacheService.hasSetKey(CacheConstant.BLACK_ROSTER, String.valueOf(Ipv4Util.ipv4ToLong(ip)));
    }
}
