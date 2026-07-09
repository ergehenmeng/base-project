package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.infrastructure.persistence.mybatis.mapper.SysDeptDataMapper;
import com.eghm.application.system.query.SysDeptDataQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis部门数据权限查询网关实现
 *
 * @author eghm
 */
@Repository
@AllArgsConstructor
public class MybatisSysDeptDataQueryService implements SysDeptDataQueryService {

    private final SysDeptDataMapper sysDeptDataMapper;

    @Override
    public List<String> getDeptList(Long userId) {
        return sysDeptDataMapper.getDeptList(userId);
    }
}
