package com.eghm.business.operation.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.delivery.dto.BannerAddRequest;
import com.eghm.business.operation.delivery.dto.BannerEditRequest;
import com.eghm.business.operation.delivery.dto.BannerQueryRequest;
import com.eghm.business.operation.delivery.mapper.BannerMapper;
import com.eghm.business.operation.delivery.entity.Banner;
import com.eghm.business.operation.delivery.service.BannerService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.business.operation.delivery.vo.BannerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:50
 */
@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerMapper bannerMapper;

    @Override
    public Page<BannerResponse> getByPage(BannerQueryRequest request) {
        return bannerMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(BannerAddRequest request) {
        DataUtil.copy(request, Banner.class, bannerMapper::insert);
    }

    @Override
    public void update(BannerEditRequest request) {
        DataUtil.copy(request, Banner.class, bannerMapper::updateById);
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
