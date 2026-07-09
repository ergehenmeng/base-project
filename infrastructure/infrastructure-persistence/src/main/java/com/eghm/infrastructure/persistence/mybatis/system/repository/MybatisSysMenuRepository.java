package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysMenuMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysMenuPO;
import com.eghm.domain.system.model.SysMenu;
import com.eghm.domain.system.repository.SysMenuRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis菜单仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysMenuRepository implements SysMenuRepository {

    private final SysMenuMapper sysMenuMapper;

    @Override
    public boolean existsTitle(String pid, String title, String excludeId) {
        LambdaQueryWrapper<SysMenuPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMenuPO::getPid, pid);
        wrapper.eq(SysMenuPO::getTitle, title);
        if (excludeId != null) {
            wrapper.ne(SysMenuPO::getId, excludeId);
        }
        return sysMenuMapper.selectCount(wrapper) > 0;
    }

    @Override
    public SysMenu findById(String id) {
        return DataUtil.copy(sysMenuMapper.selectById(id), SysMenu.class);
    }

    @Override
    public String findMaxId(String pid) {
        return sysMenuMapper.getMaxId(pid);
    }

    @Override
    public void save(SysMenu menu) {
        sysMenuMapper.insert(DataUtil.copy(menu, SysMenuPO.class));
    }

    @Override
    public void update(SysMenu menu) {
        sysMenuMapper.updateById(DataUtil.copy(menu, SysMenuPO.class));
    }

    @Override
    public void deleteById(String id) {
        sysMenuMapper.deleteById(id);
    }

    @Override
    public void updateState(String id, Boolean state) {
        LambdaUpdateWrapper<SysMenuPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysMenuPO::getId, id);
        wrapper.set(SysMenuPO::getState, state);
        sysMenuMapper.update(null, wrapper);
    }

    @Override
    public void updateSort(String id, Integer sortBy) {
        LambdaUpdateWrapper<SysMenuPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysMenuPO::getId, id);
        wrapper.set(SysMenuPO::getSort, sortBy);
        sysMenuMapper.update(null, wrapper);
    }

    @Override
    public List<SysMenu> findEnabledButtons() {
        LambdaQueryWrapper<SysMenuPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMenuPO::getState, true);
        wrapper.eq(SysMenuPO::getGrade, 2);
        return DataUtil.copy(sysMenuMapper.selectList(wrapper), SysMenu.class);
    }
}
