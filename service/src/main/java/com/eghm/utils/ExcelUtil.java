package com.eghm.utils;

import cn.hutool.core.net.RFC3986;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.eghm.dto.ext.ExcelStyle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author wyb
 * @since 2023/3/31
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ExcelUtil {

    private static final String DEFAULT_SHEET_NAME = "表格";

    /**
     * 导出xlsx表格 (统一表格样式)
     * @param response httpResponse
     * @param fileName 文件名
     * @param rowValues 导出excel表格
     * @param cls 类型
     * @param <T> 泛型
     */
    public static <T extends ExcelStyle> void exportBeauty(HttpServletResponse response, String fileName, List<T> rowValues, Class<T> cls) {
        export(response, fileName, rowValues, cls, DEFAULT_SHEET_NAME);
    }

    /**
     * 导出xlsx表格
     * @param response httpResponse
     * @param fileName 文件名
     * @param rowValues 导出excel表格
     * @param cls 类型
     * @param <T> 泛型
     */
    public static <T> void export(HttpServletResponse response, String fileName, List<T> rowValues, Class<T> cls) {
        export(response, fileName, rowValues, cls, DEFAULT_SHEET_NAME);
    }

    /**
     * 导出xlsx表格
     * @param response httpResponse
     * @param fileName 文件名
     * @param rowValues 导出excel表格
     * @param cls 类型
     * @param sheetName sheet名称
     * @param <T> 泛型
     */
    public static <T> void export(HttpServletResponse response, String fileName, List<T> rowValues, Class<T> cls, String sheetName) {
        if (!fileName.endsWith(ExcelTypeEnum.XLSX.getValue())) {
            fileName += fileName + ExcelTypeEnum.XLSX.getValue();
        }
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + RFC3986.PATH.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            EasyExcel.write(response.getOutputStream(), cls).sheet(sheetName).doWrite(rowValues);
        } catch (Exception e) {
            log.error("导出Excel异常 [{}] [{}]", fileName, cls, e);
        }
    }

}
