package com.eghm.repository.sys;

import com.eghm.mapper.SysTaskMapper;
import com.eghm.po.SysTaskPO;
import com.eghm.sys.model.SysTask;
import com.eghm.sys.repository.SysTaskRepository;
import com.eghm.utils.DataUtil;
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
