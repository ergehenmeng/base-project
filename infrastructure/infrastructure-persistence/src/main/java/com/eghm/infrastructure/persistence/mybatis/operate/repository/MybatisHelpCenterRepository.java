package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.infrastructure.persistence.mybatis.mapper.HelpCenterMapper;
import com.eghm.domain.operate.model.HelpCenter;
import com.eghm.domain.operate.repository.HelpCenterRepository;
import com.eghm.infrastructure.persistence.mybatis.po.HelpCenterPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 帮助中心 MyBatis 仓储适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisHelpCenterRepository implements HelpCenterRepository {

    private final HelpCenterMapper helpCenterMapper;

    @Override
    public HelpCenter findById(Long id) {
        return toDomain(helpCenterMapper.selectById(id));
    }

    @Override
    public void save(HelpCenter helpCenter) {
        helpCenterMapper.insert(toPo(helpCenter));
    }

    @Override
    public void update(HelpCenter helpCenter) {
        helpCenterMapper.updateById(toPo(helpCenter));
    }

    @Override
    public void updateSort(Long id, Integer sortBy) {
        LambdaUpdateWrapper<HelpCenterPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(HelpCenterPO::getId, id);
        wrapper.set(HelpCenterPO::getSort, sortBy);
        helpCenterMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        helpCenterMapper.deleteById(id);
    }

    private HelpCenter toDomain(HelpCenterPO helpCenterPO) {
        return DataUtil.copy(helpCenterPO, HelpCenter.class);
    }

    private HelpCenterPO toPo(HelpCenter helpCenter) {
        return DataUtil.copy(helpCenter, HelpCenterPO.class);
    }
}
