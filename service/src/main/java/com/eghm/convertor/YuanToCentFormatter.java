package com.eghm.convertor;

import com.eghm.utils.DecimalUtil;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * @author 二哥很猛
 * @since 2023/10/8
 */
public class YuanToCentFormatter implements Formatter<Integer> {

    @NonNull
    @Override
    public Integer parse(@NonNull String text, @NonNull Locale locale) {
        double value = new BigDecimal(text.trim()).doubleValue();
        return DecimalUtil.yuanToCent(value);
    }

    @NonNull
    @Override
    public String print(@NonNull Integer value, @NonNull Locale locale) {
        return DecimalUtil.centToYuan(value);
    }
}
