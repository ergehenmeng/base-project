package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysDeptDataMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysDeptDataPO;
import com.eghm.domain.system.model.SysDeptData;
import com.eghm.domain.system.repository.SysDeptDataRepository;
import com.eghm.application.shared.utils.DataUtil;
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
