package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.SensitiveWordMapper;
import com.eghm.domain.operate.model.SensitiveWord;
import com.eghm.infrastructure.persistence.mybatis.po.SensitiveWordPO;
import com.eghm.application.operate.port.out.SensitiveWordQueryGateway;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MybatisSensitiveWordQueryGateway implements SensitiveWordQueryGateway {

    private final SensitiveWordMapper sensitiveWordMapper;

    @Override
    public Page<SensitiveWord> getByPage(PagingQuery query, List<String> keywords) {
        LambdaQueryWrapper<SensitiveWordPO> wrapper = Wrappers.lambdaQuery();
        if (keywords != null) {
            wrapper.in(SensitiveWordPO::getKeyword, keywords);
        }
        wrapper.orderByDesc(SensitiveWordPO::getId);
        return MybatisPageUtil.copy(sensitiveWordMapper.selectPage(MybatisPageUtil.toMybatis(query.createPage()), wrapper), SensitiveWord.class);
    }
}

