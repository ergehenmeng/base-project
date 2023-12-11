package com.eghm.configuration;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * @author 二哥很猛
 * @date 2021/12/25 22:35
 */
public class SqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StrUtil.isNotBlank(sql) ? sql.replaceAll("\\s+", " ") + "\n" : "";
    }
}
