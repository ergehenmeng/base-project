package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SysTaskMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysTaskPO;
import com.eghm.domain.system.model.SysTask;
import com.eghm.domain.system.repository.SysTaskRepository;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis定时任务仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysTaskRepository implements SysTaskRepository {

    private final SysTaskMapper sysTaskMapper;

    @Override
    public SysTask findById(Long id) {
        return DataUtil.copy(sysTaskMapper.selectById(id), SysTask.class);
    }

    @Override
    public void update(SysTask task) {
        sysTaskMapper.updateById(DataUtil.copy(task, SysTaskPO.class));
    }
}
