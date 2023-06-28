package com.eghm.service.common.impl;

import cn.hutool.dfa.SensitiveUtil;
import com.eghm.mapper.SensitiveWordMapper;
import com.eghm.service.common.SensitiveWordService;
import com.eghm.utils.LoggerUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author wyb
 * @date 2021/12/4 11:10
 */
@Slf4j
@Service("SensitiveWordService")
@AllArgsConstructor
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
        SensitiveUtil.init(wordList, false);
        LoggerUtil.print(String.format("敏感词库加载成功,耗时:%dms", (System.currentTimeMillis() - start)));
    }

    public static void main(String[] args) {
        SensitiveUtil.init(Lists.newArrayList("１６大"));
        SensitiveUtil.containsSensitive("大吃饭");
    }
}
