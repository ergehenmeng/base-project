package com.eghm.service.sys.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.system.BlackRosterMapper;
import com.eghm.dao.model.sys.BlackRoster;
import com.eghm.model.dto.sys.roster.BlackRosterAddRequest;
import com.eghm.model.dto.sys.roster.BlackRosterQueryRequest;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.utils.IpUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
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

    private BlackRosterMapper blackRosterMapper;

    @Autowired
    public void setBlackRosterMapper(BlackRosterMapper blackRosterMapper) {
        this.blackRosterMapper = blackRosterMapper;
    }

    @Override
    public PageInfo<BlackRoster> getByPage(BlackRosterQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<BlackRoster> list = blackRosterMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addBlackRoster(BlackRosterAddRequest request) {
        BlackRoster roster = new BlackRoster();
        roster.setIp(IpUtil.ipToLong(request.getIp()));
        roster.setEndTime(request.getEndTime());
        blackRosterMapper.insertSelective(roster);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.BLACK_ROSTER, unless = "#result.size() == 0")
    public List<BlackRoster> getAvailableList() {
        return blackRosterMapper.getAvailableList();
    }
}
