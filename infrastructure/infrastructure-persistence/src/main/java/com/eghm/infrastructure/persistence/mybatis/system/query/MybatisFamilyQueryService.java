package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.infrastructure.persistence.mybatis.mapper.FamilyMapper;
import com.eghm.application.system.query.FamilyQueryService;
import com.eghm.application.shared.vo.sys.family.FamilyResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis家谱查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisFamilyQueryService implements FamilyQueryService {

    private final FamilyMapper familyMapper;

    @Override
    public List<FamilyResponse> getList() {
        return familyMapper.getList();
    }
}
