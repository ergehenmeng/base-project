package com.eghm.application.operate.service.impl;

import com.eghm.dto.ext.Page;
import com.eghm.dto.operate.banner.BannerAddRequest;
import com.eghm.dto.operate.banner.BannerEditRequest;
import com.eghm.dto.operate.banner.BannerQueryRequest;
import com.eghm.domain.operate.model.Banner;
import com.eghm.domain.operate.repository.BannerRepository;
import com.eghm.application.operate.service.BannerQueryGateway;
import com.eghm.application.operate.service.BannerService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.banner.BannerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:50
 */
@AllArgsConstructor
@Service("bannerService")
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    private final BannerQueryGateway bannerQueryGateway;

    @Override
    public Page<BannerResponse> getByPage(BannerQueryRequest request) {
        return bannerQueryGateway.getByPage(request.createPage(), request);
    }

    @Override
    public void create(BannerAddRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerRepository.save(banner);
    }

    @Override
    public void update(BannerEditRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerRepository.update(banner);
    }

    @Override
    public void deleteById(Long id) {
        bannerRepository.deleteById(id);
    }

    @Override
    public void sort(Long id, Integer sort) {
        bannerRepository.updateSort(id, sort);
    }

    @Override
    public void updateState(Long id, Boolean state) {
        Banner banner = bannerRepository.findById(id);
        if (banner == null) {
            return;
        }
        if (Boolean.TRUE.equals(state)) {
            banner.enable();
        } else {
            banner.disable();
        }
        bannerRepository.updateState(banner.getId(), banner.getState());
    }
}
