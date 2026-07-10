package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.operate.banner.BannerAddRequest;
import com.eghm.application.shared.dto.operate.banner.BannerEditRequest;
import com.eghm.domain.operate.model.Banner;
import com.eghm.domain.operate.repository.BannerRepository;
import com.eghm.application.operate.service.BannerApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:50
 */
@AllArgsConstructor
@Service("bannerService")
public class BannerApplicationServiceImpl implements BannerApplicationService {

    private final BannerRepository bannerRepository;

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
