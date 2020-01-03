package com.fanyin.service.system.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.dao.mapper.system.BlackRosterMapper;
import com.fanyin.dao.model.system.BlackRoster;
import com.fanyin.model.dto.system.roster.BlackRosterAddRequest;
import com.fanyin.model.dto.system.roster.BlackRosterQueryRequest;
import com.fanyin.service.system.BlackRosterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:45
 */
@Service("blackRosterService")
public class BlackRosterServiceImpl implements BlackRosterService {

    @Autowired
    private BlackRosterMapper blackRosterMapper;

    @Override
    public PageInfo<BlackRoster> getByPage(BlackRosterQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<BlackRoster> list = blackRosterMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addBlackRoster(BlackRosterAddRequest request) {
        BlackRoster roster = new BlackRoster();
        roster.setIp(request.getIp());
        roster.setEndTime(request.getEndTime());
        blackRosterMapper.insertSelective(roster);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.BLACK_ROSTER, unless = "#result.size() == 0")
    public List<BlackRoster> getAvailableList() {
        return blackRosterMapper.getAvailableList();
    }
}
