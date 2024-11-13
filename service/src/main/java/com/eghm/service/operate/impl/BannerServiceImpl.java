package com.eghm.service.operate.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.banner.BannerAddRequest;
import com.eghm.dto.operate.banner.BannerEditRequest;
import com.eghm.dto.operate.banner.BannerQueryRequest;
import com.eghm.mapper.BannerMapper;
import com.eghm.model.Banner;
import com.eghm.service.operate.BannerService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.banner.BannerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:50
 */
@Service("bannerService")
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerMapper bannerMapper;

    @Override
    public Page<BannerResponse> getByPage(BannerQueryRequest request) {
        return bannerMapper.getByPage(request.createPage(), request);
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

    @Override
    public void sort(Long id, Integer sort) {
        LambdaUpdateWrapper<Banner> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Banner::getId, id);
        wrapper.set(Banner::getSort, sort);
        bannerMapper.update(null, wrapper);
    }

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<Banner> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Banner::getId, id);
        wrapper.set(Banner::getState, state);
        bannerMapper.update(null, wrapper);
    }
}
