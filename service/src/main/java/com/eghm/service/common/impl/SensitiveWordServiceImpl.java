package com.eghm.service.common.impl;

import cn.hutool.dfa.SensitiveUtil;
import com.eghm.mapper.SensitiveWordMapper;
import com.eghm.service.common.SensitiveWordService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
    public boolean match(String keywords) {
        return SensitiveUtil.containsSensitive(keywords);
    }

    @Override
    public void reloadLexicon() {
        long start = System.currentTimeMillis();
        List<String> wordList = sensitiveWordMapper.getWordList();
        SensitiveUtil.setCharFilter(character -> true);
        SensitiveUtil.init(wordList, false);
        LoggerUtil.print(String.format("敏感词库加载成功,耗时:%dms", (System.currentTimeMillis() - start)));
    }

}
