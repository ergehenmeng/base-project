package com.fanyin.service.common.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.common.enums.Channel;
import com.fanyin.dao.mapper.common.BannerMapper;
import com.fanyin.dao.model.business.Banner;
import com.fanyin.model.dto.business.banner.BannerAddRequest;
import com.fanyin.model.dto.business.banner.BannerEditRequest;
import com.fanyin.model.dto.business.banner.BannerQueryRequest;
import com.fanyin.service.common.BannerService;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    @Cacheable(cacheNames = CacheConstant.BANNER,key = "#channel.name() + #classify",unless = "#result.empty")
    public List<Banner> getBanner(Channel channel, Byte classify) {
        return bannerMapper.getBannerList(classify,channel.name());
    }

    @Override
    public Banner getById(Integer id) {
        return bannerMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Banner> getByPage(BannerQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<Banner> list = bannerMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.BANNER,key = "#request.clientType + #request.classify",allEntries = true)
    public void addBanner(BannerAddRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerMapper.insertSelective(banner);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.BANNER,key = "#request.clientType + #request.classify",allEntries = true)
    public void editBanner(BannerEditRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerMapper.updateByPrimaryKeySelective(banner);
    }
}
