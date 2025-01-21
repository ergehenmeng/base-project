package com.eghm.configuration;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * SQL格式化
 *
 * @author 二哥很猛
 * @since 2021/12/25 22:35
 */
public class SqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return isNotBlank(sql) ? sql.replaceAll("\\s+", " ") + "\n" : "";
    }
}
