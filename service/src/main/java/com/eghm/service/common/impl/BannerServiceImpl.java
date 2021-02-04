package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.Channel;
import com.eghm.dao.mapper.BannerMapper;
import com.eghm.dao.model.Banner;
import com.eghm.model.dto.banner.BannerAddRequest;
import com.eghm.model.dto.banner.BannerEditRequest;
import com.eghm.model.dto.banner.BannerQueryRequest;
import com.eghm.service.common.BannerService;
import com.eghm.service.common.KeyGenerator;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
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
public class BannerServiceImpl implements BannerService {

    private BannerMapper bannerMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setBannerMapper(BannerMapper bannerMapper) {
        this.bannerMapper = bannerMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.BANNER, key = "#channel.name() + #classify", unless = "#result.size() == 0")
    public List<Banner> getBanner(Channel channel, Byte classify) {
        return bannerMapper.getBannerList(classify, channel.name());
    }

    @Override
    public Banner getById(Long id) {
        return bannerMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<Banner> getByPage(BannerQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<Banner> list = bannerMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.BANNER, key = "#request.clientType + #request.classify", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public void addBanner(BannerAddRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        banner.setId(keyGenerator.generateKey());
        bannerMapper.insertSelective(banner);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.BANNER, key = "#request.clientType + #request.classify", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public void editBanner(BannerEditRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerMapper.updateByPrimaryKeySelective(banner);
    }
}
