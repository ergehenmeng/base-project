package com.eghm.service.sys.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.BlackRosterMapper;
import com.eghm.dao.model.BlackRoster;
import com.eghm.model.dto.roster.BlackRosterAddRequest;
import com.eghm.model.dto.roster.BlackRosterQueryRequest;
import com.eghm.service.common.KeyGenerator;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.utils.IpUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:45
 */
@Service("blackRosterService")
public class BlackRosterServiceImpl implements BlackRosterService {

    private BlackRosterMapper blackRosterMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setBlackRosterMapper(BlackRosterMapper blackRosterMapper) {
        this.blackRosterMapper = blackRosterMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<BlackRoster> getByPage(BlackRosterQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<BlackRoster> list = blackRosterMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addBlackRoster(BlackRosterAddRequest request) {
        BlackRoster roster = new BlackRoster();
        roster.setIp(IpUtil.ipToLong(request.getIp()));
        roster.setEndTime(request.getEndTime());
        roster.setId(keyGenerator.generateKey());
        blackRosterMapper.insertSelective(roster);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.BLACK_ROSTER, unless = "#result.size() == 0")
    public List<BlackRoster> getAvailableList() {
        return blackRosterMapper.getAvailableList();
    }
}
