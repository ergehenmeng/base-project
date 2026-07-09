package com.eghm.excel;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eghm.annotation.ExcelSpinner;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.domain.system.model.SysDictItem;
import com.eghm.application.system.service.SysDictService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/3/18
 */
@Data
@Slf4j
public class ExcelSpinnerResolver {

    /**
     * 下拉选项数组。
     */
    private String[] source;

    /**
     * 下拉列表的起始行。
     */
    private int start;

    /**
     * 下拉列表的结束行。
     */
    private int end;

    /**
     * 解析下拉列表数据来源
     *
     * @param spinner 下拉框注解对象
     * @return 下拉框选项数组
     */
    public String[] resolveSource(ExcelSpinner spinner) {
        if (spinner == null) {
            return new String[]{};
        }
        // 方式一：获取固定下拉框的内容
        String[] sourceList = spinner.source();
        if (sourceList.length > 0) {
            return sourceList;
        }
        // 方式二：获取动态下拉框的内容
        Class<? extends DynamicSpinner> cls = spinner.sourceClass();
        if (DynamicSpinner.class.isAssignableFrom(cls)) {
            try {
                DynamicSpinner dynamicSpinner = SpringUtil.getBean(cls);
                return dynamicSpinner.getOptions();
            } catch (Exception e) {
                log.error("解析动态下拉框数据异常 [{}]", cls, e);
            }
        }
        // 方式三：获取码值下拉数据（动态下拉）
        String key = spinner.dictKey();
        if (CharSequenceUtil.isNotBlank(key)) {
            try {
                SysDictService service = SpringUtil.getBean(SysDictService.class);
                List<SysDictItem> itemList = service.getDictByNid(key);
                return itemList.stream().map(SysDictItem::getShowValue).toArray(String[]::new);
            } catch (Exception e) {
                log.error("解析动态下拉框数据异常 [{}]", key, e);
            }
        }
        throw new BusinessException(ErrorCode.SPINNER_ERROR);
    }
}
