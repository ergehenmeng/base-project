package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.dto.sys.dict.DictQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysDictMapper;
import com.eghm.application.system.service.SysDictQueryGateway;
import com.eghm.vo.sys.dict.DictResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis数据字典查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysDictQueryGateway implements SysDictQueryGateway {

    private final SysDictMapper sysDictMapper;

    @Override
    public List<DictResponse> getList(DictQueryRequest request) {
        return sysDictMapper.getList(request);
    }
}
