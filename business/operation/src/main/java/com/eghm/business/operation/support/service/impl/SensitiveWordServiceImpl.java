package com.eghm.business.operation.support.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.support.entity.SensitiveWord;
import com.eghm.business.operation.support.mapper.SensitiveWordMapper;
import com.eghm.business.operation.support.service.SensitiveWordService;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.enums.ExchangeQueue;
import com.eghm.foundation.web.utility.LoggerUtil;
import com.eghm.foundation.web.utility.MybatisUtil;
import com.eghm.integration.messaging.service.MessageService;
import com.eghm.platform.config.service.SensitiveWordReloader;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.foundation.core.utils.StringUtil.isNotBlank;

/**
 * @author eghm
 * @since 2020/11/4 19:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class SensitiveWordServiceImpl implements SensitiveWordService, SensitiveWordReloader {

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
        List<SensitiveWord> wordList = MybatisUtil.getList(sensitiveWordMapper, SensitiveWord::getKeyword, keyword);
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
