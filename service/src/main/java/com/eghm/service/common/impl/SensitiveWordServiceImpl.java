package com.eghm.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.SensitiveWordMapper;
import com.eghm.model.SensitiveWord;
import com.eghm.service.common.SensitiveWordService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eghm
 * @since 2020/11/4 19:12
 */
@Slf4j
@AllArgsConstructor
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements SensitiveWordService {

    private final SensitiveWordMapper sensitiveWordMapper;

    @PostConstruct
    public void init() {
        reloadLexicon();
    }

    @Override
    public List<String> match(String keywords) {
        if (StrUtil.isBlank(keywords)) {
            return Lists.newArrayList();
        }
        List<FoundWord> wordList = SensitiveUtil.getFoundAllSensitive(keywords);
        if (CollUtil.isEmpty(wordList)) {
            return Lists.newArrayList();
        }
        return wordList.stream().map(FoundWord::getFoundWord).collect(Collectors.toList());
    }

    @Override
    public void reloadLexicon() {
        long start = System.currentTimeMillis();
        List<String> wordList = sensitiveWordMapper.getWordList();
        SensitiveUtil.setCharFilter(character -> true);
        SensitiveUtil.init(wordList, false);
        LoggerUtil.print(String.format("敏感词库加载成功,耗时:%dms", (System.currentTimeMillis() - start)));
    }

    @Override
    public void delete(String keyword) {
        LambdaUpdateWrapper<SensitiveWord> wrapper = Wrappers.<SensitiveWord>lambdaUpdate().eq(SensitiveWord::getKeyword, keyword);
        sensitiveWordMapper.delete(wrapper);
        this.reloadLexicon();
    }
}
