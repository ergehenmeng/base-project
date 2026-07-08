package com.eghm.query.sys;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.SysDeptMapper;
import com.eghm.service.sys.SysDeptQueryGateway;
import com.eghm.vo.sys.ext.SysDeptResponse;
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
