package com.eghm.configuration;

import cn.hutool.core.util.StrUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.ParameterException;
import com.eghm.utils.DateUtil;

import java.beans.PropertyEditorSupport;

/**
 * @author 二哥很猛
 * @date 2019/1/22 17:07
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {

        try {
            if (StrUtil.isNotBlank(text)) {
                super.setValue(DateUtil.parseLongJava8(text));
            }
        } catch (Exception e) {
            try {
                super.setValue(DateUtil.parseShort(text));
            } catch (Exception e1) {
                throw new ParameterException(ErrorCode.DATE_CASE_ERROR);
            }
        }
    }
}
