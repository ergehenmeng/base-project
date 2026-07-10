package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.banner.BannerAddRequest;
import com.eghm.application.shared.dto.operate.banner.BannerEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.domain.operate.model.Banner;
import com.eghm.domain.operate.repository.BannerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:20
 */

@Service
@AllArgsConstructor
public class BannerApplicationService {

    private final BannerRepository bannerRepository;

    /**
     * 新增轮播图信息
     *
     * @param request 前台参数
     */
    public void create(BannerAddRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerRepository.save(banner);
    }

    /**
     * 编辑保存轮播图信息
     *
     * @param request 前台参数
     */
    public void update(BannerEditRequest request) {
        Banner banner = DataUtil.copy(request, Banner.class);
        bannerRepository.update(banner);
    }

    /**
     * 删除轮播图信息
     *
     * @param id id
     */
    public void deleteById(Long id) {
        bannerRepository.deleteById(id);
    }

    /**
     * 更新排序
     *
     * @param id   主键
     * @param sort 1~999
     */
    public void sort(Long id, Integer sort) {
        bannerRepository.updateSort(id, sort);
    }

    /**
     * 更新状态
     *
     * @param id    主键
     * @param state 是否可点击
     */
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
