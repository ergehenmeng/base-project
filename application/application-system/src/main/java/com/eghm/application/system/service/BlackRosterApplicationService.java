package com.eghm.application.system.service;

import cn.hutool.core.net.Ipv4Util;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.constants.CacheConstant;
import com.eghm.application.shared.dto.operate.roster.BlackRosterAddRequest;
import com.eghm.domain.system.model.BlackRoster;
import com.eghm.domain.system.repository.BlackRosterRepository;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:45
 */
@Slf4j
@Service
@AllArgsConstructor
public class BlackRosterApplicationService {

    private final CacheService cacheService;

    private final BlackRosterRepository blackRosterRepository;

    /**
     * 添加黑名单信息
     *
     * @param request ip及时间
     */
    public void addBlackRoster(BlackRosterAddRequest request) {
        BlackRoster blackRoster = DataUtil.copy(request, BlackRoster.class);
        blackRoster.assertRangeValid();
        blackRosterRepository.save(blackRoster);
    }

    /**
     * 删除黑名单
     *
     * @param id id
     */
    public void deleteById(Long id) {
        blackRosterRepository.deleteById(id);
    }

    /**
     * 重新加载黑名单列表
     */
    public void reloadBlackRoster() {
        cacheService.delete(CacheConstant.BLACK_ROSTER);
        List<BlackRoster> rosterList = blackRosterRepository.findAll();
        for (BlackRoster roster : rosterList) {
            cacheService.setSetValue(CacheConstant.BLACK_ROSTER, roster.toCacheValues());
        }
    }

    /**
     * 是否是需要拦截的ip
     *
     * @param ip ip地址
     * @return true:黑名单 false:白名单
     */
    public boolean isInterceptIp(String ip) {
        return cacheService.hasSetKey(CacheConstant.BLACK_ROSTER, String.valueOf(Ipv4Util.ipv4ToLong(ip)));
    }
}
