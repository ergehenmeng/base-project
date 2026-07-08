package com.eghm.query.sys;

import com.eghm.mapper.FamilyMapper;
import com.eghm.service.sys.FamilyQueryGateway;
import com.eghm.vo.sys.family.FamilyResponse;
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
public class MybatisFamilyQueryGateway implements FamilyQueryGateway {

    private final FamilyMapper familyMapper;

    @Override
    public List<FamilyResponse> getList() {
        return familyMapper.getList();
    }
}
