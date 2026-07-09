package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysDeptMapper;
import com.eghm.application.system.port.out.SysDeptQueryGateway;
import com.eghm.application.shared.vo.sys.ext.SysDeptResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis部门查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysDeptQueryGateway implements SysDeptQueryGateway {

    private final SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptResponse> getList(PagingQuery query) {
        return sysDeptMapper.getList(query.getQueryName());
    }
}
