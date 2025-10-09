package com.eghm.service.operate.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ExchangeQueue;
import com.eghm.mapper.SensitiveWordMapper;
import com.eghm.model.SensitiveWord;
import com.eghm.mq.service.MessageService;
import com.eghm.service.operate.SensitiveWordService;
import com.eghm.utils.LoggerUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author eghm
 * @since 2020/11/4 19:12
 */
@Slf4j
@AllArgsConstructor
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements SensitiveWordService {

    private final MessageService messageService;

    private final SensitiveWordMapper sensitiveWordMapper;

    @PostConstruct
    public void init() {
        this.reloadLexicon(false);
    }

    @Override
    public Page<SensitiveWord> getByPage(PagingQuery query) {
        LambdaQueryWrapper<SensitiveWord> wrapper = Wrappers.lambdaQuery();
        if (isNotBlank(query.getQueryName())) {
            List<FoundWord> wordList = SensitiveUtil.getFoundAllSensitive(query.getQueryName());
            if (CollUtil.isEmpty(wordList)) {
                return new Page<>();
            }
            wrapper.in(SensitiveWord::getKeyword, wordList.stream().map(FoundWord::getFoundWord).toList());
        }
        wrapper.orderByDesc(SensitiveWord::getId);
        return sensitiveWordMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public void reloadLexicon(boolean sync) {
        long start = System.currentTimeMillis();
        List<String> wordList = sensitiveWordMapper.getWordList();
        SensitiveUtil.setCharFilter(character -> true);
        SensitiveUtil.init(wordList, false);
        LoggerUtil.print(String.format("敏感词库加载成功,耗时:%dms", (System.currentTimeMillis() - start)));
        if (sync) {
            String appName = SpringUtil.getApplicationName();
            messageService.sendDelay(ExchangeQueue.SENSITIVE_SYNC, appName, 3);
        }
    }

    @Override
    public void create(String keyword) {
        List<SensitiveWord> wordList = sensitiveWordMapper.selectList(Wrappers.<SensitiveWord>lambdaQuery().eq(SensitiveWord::getKeyword, keyword));
        if (CollUtil.isEmpty(wordList)) {
            SensitiveWord word = new SensitiveWord();
            word.setKeyword(keyword);
            sensitiveWordMapper.insert(word);
        }
        this.reloadLexicon(true);
    }

    @Override
    public void delete(Long id) {
        sensitiveWordMapper.deleteById(id);
        this.reloadLexicon(true);
    }

}
