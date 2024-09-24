package com.eghm.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.SensitiveWordMapper;
import com.eghm.model.SensitiveWord;
import com.eghm.service.common.SensitiveWordService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Page<SensitiveWord> getByPage(PagingQuery query) {
        LambdaQueryWrapper<SensitiveWord> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(query.getQueryName())) {
            List<FoundWord> wordList = SensitiveUtil.getFoundAllSensitive(query.getQueryName());
            if (CollUtil.isEmpty(wordList)) {
                return new Page<>();
            }
            wrapper.in(SensitiveWord::getKeyword, wordList.stream().map(FoundWord::getFoundWord).collect(Collectors.toList()));
        }
        wrapper.orderByDesc(SensitiveWord::getId);
        return sensitiveWordMapper.selectPage(query.createPage(), wrapper);
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
    public void create(String keyword) {
        List<SensitiveWord> wordList = sensitiveWordMapper.selectList(Wrappers.<SensitiveWord>lambdaQuery().eq(SensitiveWord::getKeyword, keyword));
        if (CollUtil.isEmpty(wordList)) {
            SensitiveWord word = new SensitiveWord();
            word.setKeyword(keyword);
            sensitiveWordMapper.insert(word);
        }
        this.reloadLexicon();
    }

    @Override
    public void delete(Long id) {
        sensitiveWordMapper.deleteById(id);
        this.reloadLexicon();
    }

}
