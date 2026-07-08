package com.eghm.query.operate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.SensitiveWordMapper;
import com.eghm.operate.model.SensitiveWord;
import com.eghm.po.SensitiveWordPO;
import com.eghm.service.operate.SensitiveWordQueryGateway;
import com.eghm.utils.DataUtil;
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

