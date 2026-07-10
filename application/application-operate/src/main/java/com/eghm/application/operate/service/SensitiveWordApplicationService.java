package com.eghm.application.operate.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.dfa.SensitiveUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eghm.application.shared.mq.service.MessageService;
import com.eghm.application.shared.utils.LoggerUtil;
import com.eghm.domain.operate.model.SensitiveWord;
import com.eghm.domain.operate.repository.SensitiveWordRepository;
import com.eghm.enums.ExchangeQueue;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eghm
 * @since 2020/11/4 19:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class SensitiveWordApplicationService {

    private final MessageService messageService;

    private final SensitiveWordRepository sensitiveWordRepository;

    @PostConstruct
    public void init() {
        this.reloadLexicon(false);
    }

    /**
     * 重新加载敏感词
     * @param sync 同步给其他服务 true:同步 false:不同步
     */
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

    /**
     * 添加敏感词
     *
     * @param keyword 敏感词
     */
    public void create(String keyword) {
        List<SensitiveWord> wordList = sensitiveWordRepository.findByKeyword(keyword);
        if (CollUtil.isEmpty(wordList)) {
            SensitiveWord word = new SensitiveWord();
            word.setKeyword(keyword);
            sensitiveWordRepository.save(word);
        }
        this.reloadLexicon(true);
    }

    /**
     * 删除敏感词
     *
     * @param id 敏感词id
     */
    public void delete(Long id) {
        sensitiveWordRepository.deleteById(id);
        this.reloadLexicon(true);
    }
}
