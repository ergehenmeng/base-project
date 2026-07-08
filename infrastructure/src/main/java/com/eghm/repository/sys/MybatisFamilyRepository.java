package com.eghm.repository.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.FamilyMapper;
import com.eghm.po.FamilyPO;
import com.eghm.sys.model.Family;
import com.eghm.sys.repository.FamilyRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis家谱仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisFamilyRepository implements FamilyRepository {

    private final FamilyMapper familyMapper;

    @Override
    public boolean existsByNameAndId(String name, String id, boolean exclude) {
        LambdaQueryWrapper<FamilyPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FamilyPO::getName, name);
        if (exclude) {
            wrapper.ne(FamilyPO::getId, id);
        } else {
            wrapper.eq(FamilyPO::getId, id);
        }
        return familyMapper.selectCount(wrapper) > 0;
    }

    @Override
    public String findMaxId(String pid) {
        return familyMapper.getMaxId(pid);
    }

    @Override
    public void save(Family family) {
        familyMapper.insert(DataUtil.copy(family, FamilyPO.class));
    }

    @Override
    public void update(Family family) {
        familyMapper.updateById(DataUtil.copy(family, FamilyPO.class));
    }

    @Override
    public boolean hasChildren(String id) {
        LambdaQueryWrapper<FamilyPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FamilyPO::getPid, id);
        return familyMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void deleteById(String id) {
        familyMapper.deleteById(id);
    }
}
