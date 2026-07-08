package com.eghm.query.sys;

import com.eghm.mapper.SysDeptDataMapper;
import com.eghm.service.sys.SysDeptDataQueryGateway;
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
public class MybatisSysDeptDataQueryGateway implements SysDeptDataQueryGateway {

    private final SysDeptDataMapper sysDeptDataMapper;

    @Override
    public List<String> getDeptList(Long userId) {
        return sysDeptDataMapper.getDeptList(userId);
    }
}
