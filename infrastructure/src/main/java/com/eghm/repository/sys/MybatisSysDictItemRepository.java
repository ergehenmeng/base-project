package com.eghm.repository.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.SysDictItemMapper;
import com.eghm.po.SysDictItemPO;
import com.eghm.sys.model.SysDictItem;
import com.eghm.sys.repository.SysDictItemRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis数据字典项仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysDictItemRepository implements SysDictItemRepository {

    private final SysDictItemMapper sysDictItemMapper;

    @Override
    public boolean existsShowValue(String nid, String showValue, Long excludeId) {
        LambdaQueryWrapper<SysDictItemPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictItemPO::getNid, nid);
        wrapper.eq(SysDictItemPO::getShowValue, showValue);
        if (excludeId != null) {
            wrapper.ne(SysDictItemPO::getId, excludeId);
        }
        return sysDictItemMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean existsHiddenValue(String nid, Integer hiddenValue, Long excludeId) {
        LambdaQueryWrapper<SysDictItemPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictItemPO::getNid, nid);
        wrapper.eq(SysDictItemPO::getHiddenValue, hiddenValue);
        if (excludeId != null) {
            wrapper.ne(SysDictItemPO::getId, excludeId);
        }
        return sysDictItemMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void save(SysDictItem item) {
        sysDictItemMapper.insert(DataUtil.copy(item, SysDictItemPO.class));
    }

    @Override
    public void update(SysDictItem item) {
        sysDictItemMapper.updateById(DataUtil.copy(item, SysDictItemPO.class));
    }

    @Override
    public void deleteById(Long id) {
        sysDictItemMapper.deleteById(id);
    }
}
