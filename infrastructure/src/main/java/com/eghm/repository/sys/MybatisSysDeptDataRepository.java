package com.eghm.repository.sys;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.SysDeptDataMapper;
import com.eghm.po.SysDeptDataPO;
import com.eghm.sys.model.SysDeptData;
import com.eghm.sys.repository.SysDeptDataRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis部门数据权限仓储实现
 *
 * @author eghm
 */
@Repository
@AllArgsConstructor
public class MybatisSysDeptDataRepository implements SysDeptDataRepository {

    private final SysDeptDataMapper sysDeptDataMapper;

    @Override
    public void save(SysDeptData deptData) {
        sysDeptDataMapper.insert(DataUtil.copy(deptData, SysDeptDataPO.class));
    }

    @Override
    public void deleteByUserId(Long userId) {
        LambdaUpdateWrapper<SysDeptDataPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysDeptDataPO::getUserId, userId);
        sysDeptDataMapper.delete(wrapper);
    }
}
