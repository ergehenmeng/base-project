package com.eghm.configuration;

import com.eghm.utils.StringUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * @author 二哥很猛
 * @since 2021/12/25 22:35
 */
public class SqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtil.isNotBlank(sql) ? sql.replaceAll("\\s+", " ") + "\n" : "";
    }
}
