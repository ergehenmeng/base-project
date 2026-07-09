package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SysConfigMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysConfigPO;
import com.eghm.domain.system.model.SysConfig;
import com.eghm.domain.system.repository.SysConfigRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis系统配置仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysConfigRepository implements SysConfigRepository {

    private final SysConfigMapper sysConfigMapper;

    @Override
    public SysConfig findById(Long id) {
        return DataUtil.copy(sysConfigMapper.selectById(id), SysConfig.class);
    }

    @Override
    public void update(SysConfig config) {
        sysConfigMapper.updateById(DataUtil.copy(config, SysConfigPO.class));
    }
}
