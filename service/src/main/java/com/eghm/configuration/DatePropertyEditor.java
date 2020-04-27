package com.eghm.configuration;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.common.utils.DateUtil;
import com.eghm.common.utils.StringUtil;

import java.beans.PropertyEditorSupport;

/**
 * @author 二哥很猛
 * @date 2019/1/22 17:07
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            if (StringUtil.isNotBlank(text)) {
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
