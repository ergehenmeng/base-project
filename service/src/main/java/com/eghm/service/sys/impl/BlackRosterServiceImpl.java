package com.eghm.service.sys.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.BlackRosterMapper;
import com.eghm.dao.model.BlackRoster;
import com.eghm.model.dto.roster.BlackRosterAddRequest;
import com.eghm.model.dto.roster.BlackRosterQueryRequest;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.BlackRosterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:45
 */
@Service("blackRosterService")
@AllArgsConstructor
public class BlackRosterServiceImpl implements BlackRosterService {

    private final BlackRosterMapper blackRosterMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public Page<BlackRoster> getByPage(BlackRosterQueryRequest request) {
        LambdaQueryWrapper<BlackRoster> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), BlackRoster::getIp, request.getQueryName());
        return blackRosterMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void addBlackRoster(BlackRosterAddRequest request) {
        BlackRoster roster = new BlackRoster();
        roster.setIp(request.getIp());
        roster.setLongIp(NetUtil.ipv4ToLong(request.getIp()));
        roster.setEndTime(request.getEndTime());
        blackRosterMapper.insert(roster);
    }

    @Override
    public List<BlackRoster> getAvailableList() {
        return cacheProxyService.getBlackRosterList();
    }

    @Override
    public boolean isInterceptIp(String ip) {
        List<BlackRoster> availableList = cacheProxyService.getBlackRosterList();
        if (!CollectionUtils.isEmpty(availableList)) {
            return availableList.stream().anyMatch(blackRoster -> NetUtil.ipv4ToLong(ip) == blackRoster.getLongIp() && (blackRoster.getEndTime() == null || LocalDateTime.now().isBefore(blackRoster.getEndTime())));
        }
        return false;
    }
}
