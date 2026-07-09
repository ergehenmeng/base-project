package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.BlackRosterMapper;
import com.eghm.infrastructure.persistence.mybatis.po.BlackRosterPO;
import com.eghm.domain.system.model.BlackRoster;
import com.eghm.domain.system.repository.BlackRosterRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis黑名单仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisBlackRosterRepository implements BlackRosterRepository {

    private final BlackRosterMapper blackRosterMapper;

    @Override
    public void save(BlackRoster blackRoster) {
        blackRosterMapper.insert(DataUtil.copy(blackRoster, BlackRosterPO.class));
    }

    @Override
    public void deleteById(Long id) {
        blackRosterMapper.deleteById(id);
    }

    @Override
    public List<BlackRoster> findAll() {
        return DataUtil.copy(blackRosterMapper.selectList(null), BlackRoster.class);
    }
}
