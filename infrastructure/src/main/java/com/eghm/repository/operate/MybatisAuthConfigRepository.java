package com.eghm.repository.operate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.AuthConfigMapper;
import com.eghm.operate.model.AuthConfig;
import com.eghm.operate.repository.AuthConfigRepository;
import com.eghm.po.AuthConfigPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisAuthConfigRepository implements AuthConfigRepository {

    private final AuthConfigMapper authConfigMapper;

    @Override
    public void save(AuthConfig authConfig) {
        authConfigMapper.insert(toPo(authConfig));
    }

    @Override
    public void update(AuthConfig authConfig) {
        authConfigMapper.updateById(toPo(authConfig));
    }

    @Override
    public void deleteById(Long id) {
        authConfigMapper.deleteById(id);
    }

    @Override
    public AuthConfig findById(Long id) {
        return DataUtil.copy(authConfigMapper.selectById(id), AuthConfig.class);
    }

    @Override
    public boolean existsByTitle(String title, Long excludeId) {
        LambdaQueryWrapper<AuthConfigPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AuthConfigPO::getTitle, title);
        if (excludeId != null) {
            wrapper.ne(AuthConfigPO::getId, excludeId);
        }
        return authConfigMapper.selectCount(wrapper) > 0;
    }

    private AuthConfigPO toPo(AuthConfig authConfig) {
        return DataUtil.copy(authConfig, AuthConfigPO.class);
    }
}
