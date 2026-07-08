package com.eghm.repository.operate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.AppVersionMapper;
import com.eghm.operate.model.AppVersion;
import com.eghm.operate.repository.AppVersionRepository;
import com.eghm.po.AppVersionPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 手机版本 MyBatis 仓储适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisAppVersionRepository implements AppVersionRepository {

    private final AppVersionMapper appVersionMapper;

    @Override
    public void save(AppVersion appVersion) {
        appVersionMapper.insert(toPo(appVersion));
    }

    @Override
    public void update(AppVersion appVersion) {
        appVersionMapper.updateById(toPo(appVersion));
    }

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<AppVersionPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(AppVersionPO::getId, id);
        wrapper.set(AppVersionPO::getState, state);
        appVersionMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        appVersionMapper.deleteById(id);
    }

    @Override
    public boolean existsByVersion(String version) {
        LambdaQueryWrapper<AppVersionPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppVersionPO::getVersion, version);
        return appVersionMapper.selectCount(wrapper) > 0;
    }

    @Override
    public AppVersion findLatestVersion(String channel) {
        return toDomain(appVersionMapper.getVersion(channel));
    }

    @Override
    public List<AppVersion> findForceUpdateVersions(String channel, Integer startVersion, Integer endVersion) {
        return DataUtil.copy(appVersionMapper.getForceUpdateVersion(channel, startVersion, endVersion), AppVersion.class);
    }

    private AppVersion toDomain(AppVersionPO appVersionPO) {
        return DataUtil.copy(appVersionPO, AppVersion.class);
    }

    private AppVersionPO toPo(AppVersion appVersion) {
        return DataUtil.copy(appVersion, AppVersionPO.class);
    }
}
