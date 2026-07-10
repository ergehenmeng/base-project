package com.eghm.application.operate.query;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.domain.operate.model.SensitiveWord;

import java.util.List;

import static com.eghm.application.shared.utils.StringUtil.isNotBlank;

/**
 * 敏感词查询服务
 *
 * @author wyb
 * @since 2021/12/4 11:02
 */
public interface SensitiveWordQueryService {

    default Page<SensitiveWord> getByPage(PagingQuery query) {
        List<String> keywords = null;
        if (isNotBlank(query.getQueryName())) {
            List<FoundWord> wordList = SensitiveUtil.getFoundAllSensitive(query.getQueryName());
            if (CollUtil.isEmpty(wordList)) {
                return new Page<>();
            }
            keywords = wordList.stream().map(FoundWord::getFoundWord).toList();
        }
        return getByPage(query, keywords);
    }

    Page<SensitiveWord> getByPage(PagingQuery query, List<String> keywords);
}
