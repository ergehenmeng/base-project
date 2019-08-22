package com.fanyin.service.common.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.common.enums.Channel;
import com.fanyin.common.enums.SourceClassify;
import com.fanyin.dao.mapper.common.BannerMapper;
import com.fanyin.dao.model.business.Banner;
import com.fanyin.model.dto.business.banner.BannerQueryRequest;
import com.fanyin.service.common.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/10/17 9:50
 */
@Service("bannerService")
@Transactional(rollbackFor = RuntimeException.class)
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.BANNER,key = "#source.name() + #type",unless = "#result == null",cacheManager = "smallCacheManager")
    public List<Banner> getBanner(Channel source, Byte type) {
        byte clientType = SourceClassify.getType(source);
        return bannerMapper.getBannerList(type,clientType);
    }

    @Override
    public PageInfo<Banner> getByPage(BannerQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<Banner> list = bannerMapper.getList(request);
        return new PageInfo<>(list);
    }


}
