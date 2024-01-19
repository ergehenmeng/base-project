package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.banner.BannerAddRequest;
import com.eghm.dto.banner.BannerEditRequest;
import com.eghm.dto.banner.BannerQueryRequest;
import com.eghm.enums.Channel;
import com.eghm.mapper.BannerMapper;
import com.eghm.model.Banner;
import com.eghm.service.common.BannerService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:50
 */
@Service("bannerService")
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerMapper bannerMapper;

    @Override
    public Page<Banner> getByPage(BannerQueryRequest request) {
        LambdaQueryWrapper<Banner> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getClassify() != null, Banner::getClassify, request.getClassify());
        wrapper.eq(StrUtil.isNotBlank(request.getClientType()), Banner::getClientType, request.getClientType());
        wrapper.and(request.getMiddleTime() != null, queryWrapper ->
                queryWrapper.le(Banner::getStartTime, request.getMiddleTime()).ge(Banner::getEndTime, request.getMiddleTime()));
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Banner::getTitle, request.getQueryName());
        return bannerMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public List<Banner> getBanner(Channel channel, Integer classify) {
        return bannerMapper.getBannerList(classify, channel.name());
    }

    @Override
    public Banner getById(Long id) {
        return bannerMapper.selectById(id);
    }

    @Override
    public void create(BannerAddRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerMapper.insert(banner);
    }

    @Override
    public void update(BannerEditRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerMapper.updateById(banner);
    }

    @Override
    public void deleteById(Long id) {
        bannerMapper.deleteById(id);
    }
}
