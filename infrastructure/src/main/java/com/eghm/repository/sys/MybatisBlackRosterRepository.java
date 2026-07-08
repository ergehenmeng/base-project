package com.eghm.repository.sys;

import com.eghm.mapper.BlackRosterMapper;
import com.eghm.po.BlackRosterPO;
import com.eghm.sys.model.BlackRoster;
import com.eghm.sys.repository.BlackRosterRepository;
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
