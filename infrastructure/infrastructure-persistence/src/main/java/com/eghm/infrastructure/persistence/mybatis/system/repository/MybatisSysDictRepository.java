package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysDictMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysDictPO;
import com.eghm.domain.system.model.SysDict;
import com.eghm.domain.system.repository.SysDictRepository;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis数据字典仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysDictRepository implements SysDictRepository {

    private final SysDictMapper sysDictMapper;

    @Override
    public boolean existsTitle(String title, Long excludeId) {
        LambdaQueryWrapper<SysDictPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictPO::getTitle, title);
        if (excludeId != null) {
            wrapper.ne(SysDictPO::getId, excludeId);
        }
        return sysDictMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean existsNid(String nid) {
        LambdaQueryWrapper<SysDictPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictPO::getNid, nid);
        return sysDictMapper.selectCount(wrapper) > 0;
    }

    @Override
    public SysDict findById(Long id) {
        return DataUtil.copy(sysDictMapper.selectById(id), SysDict.class);
    }

    @Override
    public void save(SysDict dict) {
        sysDictMapper.insert(DataUtil.copy(dict, SysDictPO.class));
    }

    @Override
    public void update(SysDict dict) {
        sysDictMapper.updateById(DataUtil.copy(dict, SysDictPO.class));
    }
}
