package com.eghm.service.sys.impl;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.roster.BlackRosterAddRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.BlackRosterMapper;
import com.eghm.model.BlackRoster;
import com.eghm.service.sys.BlackRosterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:45
 */
@Slf4j
@Service("blackRosterService")
@AllArgsConstructor
public class BlackRosterServiceImpl implements BlackRosterService {

    private final BlackRosterMapper blackRosterMapper;

    private final CacheService cacheService;

    @Override
    public Page<BlackRoster> getByPage(PagingQuery request) {
        LambdaQueryWrapper<BlackRoster> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(request.getQueryName())) {
            if (PatternPool.IPV4.matcher(request.getQueryName()).matches()) {
                long aLong = Ipv4Util.ipv4ToLong(request.getQueryName());
                wrapper.ge(BlackRoster::getEndIp, aLong);
                wrapper.le(BlackRoster::getStartIp, aLong);
            } else {
                wrapper.like(BlackRoster::getRemark, request.getQueryName());
            }
        }
        wrapper.orderByDesc(BlackRoster::getId);
        return blackRosterMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void addBlackRoster(BlackRosterAddRequest request) {
        if (request.getStartIp() > request.getEndIp()) {
            log.warn("ip黑名单范围错误 [{}] [{}]", request.getStartIp(), request.getEndIp());
            throw new BusinessException(ErrorCode.IP_RANGE_ILLEGAL);
        }
        BlackRoster roster = new BlackRoster();
        roster.setStartIp(request.getStartIp());
        roster.setEndIp(request.getEndIp());
        roster.setRemark(request.getRemark());
        blackRosterMapper.insert(roster);
        this.reloadBlackRoster();
    }

    @Override
    public void deleteById(Long id) {
        blackRosterMapper.deleteById(id);
        this.reloadBlackRoster();
    }

    @Override
    public void reloadBlackRoster() {
        cacheService.delete(CacheConstant.BLACK_ROSTER);
        List<BlackRoster> rosterList = blackRosterMapper.selectList(null);
        for (BlackRoster roster : rosterList) {
            long max = roster.getEndIp() - roster.getStartIp();
            for (int i = 0; i <= max; i++) {
                cacheService.setBitmap(CacheConstant.BLACK_ROSTER, roster.getStartIp() + i, true);
            }
        }
    }

    @Override
    public boolean isInterceptIp(String ip) {
        return cacheService.getBitmap(CacheConstant.BLACK_ROSTER, Ipv4Util.ipv4ToLong(ip));
    }
}
