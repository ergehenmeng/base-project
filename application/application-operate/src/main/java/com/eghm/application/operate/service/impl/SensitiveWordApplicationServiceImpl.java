package com.eghm.application.operate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.enums.ExchangeQueue;
import com.eghm.application.shared.mq.service.MessageService;
import com.eghm.domain.operate.model.SensitiveWord;
import com.eghm.domain.operate.repository.SensitiveWordRepository;
import com.eghm.application.operate.query.SensitiveWordQueryService;
import com.eghm.application.operate.service.SensitiveWordApplicationService;
import com.eghm.application.shared.utils.LoggerUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.application.shared.utils.StringUtil.isNotBlank;

/**
 * @author eghm
 * @since 2020/11/4 19:12
 */
@Slf4j
@AllArgsConstructor
@Service("sensitiveWordService")
public class SensitiveWordApplicationServiceImpl implements SensitiveWordApplicationService {

    private final MessageService messageService;

    private final SensitiveWordRepository sensitiveWordRepository;

    private final SensitiveWordQueryService sensitiveWordQueryGateway;

    @PostConstruct
    public void init() {
        this.reloadLexicon(false);
    }

    @Override
    public Page<SensitiveWord> getByPage(PagingQuery query) {
        List<String> keywords = null;
        if (isNotBlank(query.getQueryName())) {
            List<FoundWord> wordList = SensitiveUtil.getFoundAllSensitive(query.getQueryName());
            if (CollUtil.isEmpty(wordList)) {
                return new Page<>();
            }
            keywords = wordList.stream().map(FoundWord::getFoundWord).toList();
        }
        return sensitiveWordQueryGateway.getByPage(query, keywords);
    }

    @Override
    public void reloadLexicon(boolean sync) {
        long start = System.currentTimeMillis();
        List<String> wordList = sensitiveWordRepository.listWords();
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
        List<SensitiveWord> wordList = sensitiveWordRepository.findByKeyword(keyword);
        if (CollUtil.isEmpty(wordList)) {
            SensitiveWord word = new SensitiveWord();
            word.setKeyword(keyword);
            sensitiveWordRepository.save(word);
        }
        this.reloadLexicon(true);
    }

    @Override
    public void delete(Long id) {
        sensitiveWordRepository.deleteById(id);
        this.reloadLexicon(true);
    }
}
