package com.eghm.service.sys.impl;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.constants.CacheConstant;
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
import java.util.stream.LongStream;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:45
 */
@Slf4j
@Service("blackRosterService")
@AllArgsConstructor
public class BlackRosterServiceImpl implements BlackRosterService {

    private final CacheService cacheService;

    private final BlackRosterMapper blackRosterMapper;

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
            String[] array = LongStream.range(roster.getStartIp(), roster.getEndIp() + 1).mapToObj(String::valueOf).toArray(String[]::new);
            cacheService.setSetValue(CacheConstant.BLACK_ROSTER, array);
        }
    }

    @Override
    public boolean isInterceptIp(String ip) {
        return cacheService.hasSetKey(CacheConstant.BLACK_ROSTER, String.valueOf(Ipv4Util.ipv4ToLong(ip)));
    }

}
