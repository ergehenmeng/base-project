package com.eghm.repository.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.SysDeptMapper;
import com.eghm.po.SysDeptPO;
import com.eghm.sys.model.SysDept;
import com.eghm.sys.repository.SysDeptRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis部门仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysDeptRepository implements SysDeptRepository {

    private final SysDeptMapper sysDeptMapper;

    @Override
    public boolean existsByParentCodeAndTitle(String parentCode, String title, Long excludeId) {
        LambdaQueryWrapper<SysDeptPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDeptPO::getParentCode, parentCode);
        wrapper.eq(SysDeptPO::getTitle, title);
        if (excludeId != null) {
            wrapper.ne(SysDeptPO::getId, excludeId);
        }
        return sysDeptMapper.selectCount(wrapper) > 0;
    }

    @Override
    public String findMaxChildCode(String code) {
        return sysDeptMapper.getMaxCodeChild(code);
    }

    @Override
    public void save(SysDept dept) {
        sysDeptMapper.insert(DataUtil.copy(dept, SysDeptPO.class));
    }

    @Override
    public void update(SysDept dept) {
        sysDeptMapper.updateById(DataUtil.copy(dept, SysDeptPO.class));
    }

    @Override
    public void deleteById(Long id) {
        sysDeptMapper.deleteById(id);
    }
}
