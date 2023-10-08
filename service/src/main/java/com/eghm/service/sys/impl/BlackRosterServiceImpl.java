package com.eghm.service.sys.impl;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.roster.BlackRosterAddRequest;
import com.eghm.dto.roster.BlackRosterQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.BlackRosterMapper;
import com.eghm.model.BlackRoster;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.BlackRosterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:45
 */
@Service("blackRosterService")
@AllArgsConstructor
public class BlackRosterServiceImpl implements BlackRosterService {

    private final BlackRosterMapper blackRosterMapper;

    private final CacheService cacheService;

    @Override
    public Page<BlackRoster> getByPage(BlackRosterQueryRequest request) {
        LambdaQueryWrapper<BlackRoster> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(request.getQueryName())) {
            if (!PatternPool.IPV4.matcher(request.getQueryName()).matches()) {
                throw new BusinessException(ErrorCode.IP_ILLEGAL);
            }
            long aLong = Ipv4Util.ipv4ToLong(request.getQueryName());
            wrapper.le(BlackRoster::getEndIp, aLong);
            wrapper.ge(BlackRoster::getStartIp, aLong);
        }
        return blackRosterMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void addBlackRoster(BlackRosterAddRequest request) {
        BlackRoster roster = new BlackRoster();
        roster.setStartIp(request.getStartIp());
        roster.setEndIp(request.getEndIp());
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
            for (int i = 0; i <= max ; i++) {
                cacheService.setBitmap(CacheConstant.BLACK_ROSTER, roster.getStartIp() + i, true);
            }
        }
    }

    @Override
    public boolean isInterceptIp(String ip) {
       return cacheService.getBitmap(CacheConstant.BLACK_ROSTER, Ipv4Util.ipv4ToLong(ip));
    }
}
