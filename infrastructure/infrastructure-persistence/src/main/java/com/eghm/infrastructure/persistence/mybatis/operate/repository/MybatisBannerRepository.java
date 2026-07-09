package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.infrastructure.persistence.mybatis.mapper.BannerMapper;
import com.eghm.domain.operate.model.Banner;
import com.eghm.domain.operate.repository.BannerRepository;
import com.eghm.infrastructure.persistence.mybatis.po.BannerPO;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 轮播图 MyBatis 仓储适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisBannerRepository implements BannerRepository {

    private final BannerMapper bannerMapper;

    @Override
    public Banner findById(Long id) {
        return DataUtil.copy(bannerMapper.selectById(id), Banner.class);
    }

    @Override
    public void save(Banner banner) {
        bannerMapper.insert(toPo(banner));
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.updateById(toPo(banner));
    }

    @Override
    public void deleteById(Long id) {
        bannerMapper.deleteById(id);
    }

    @Override
    public void updateSort(Long id, Integer sort) {
        LambdaUpdateWrapper<BannerPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(BannerPO::getId, id);
        wrapper.set(BannerPO::getSort, sort);
        bannerMapper.update(null, wrapper);
    }

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<BannerPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(BannerPO::getId, id);
        wrapper.set(BannerPO::getState, state);
        bannerMapper.update(null, wrapper);
    }

    private BannerPO toPo(Banner banner) {
        return DataUtil.copy(banner, BannerPO.class);
    }
}
