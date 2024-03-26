package com.eghm.utils;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

/**
 * easy-excel封装工具类
 *
 * @author wyb
 * @since 2023/3/31
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class EasyExcelUtil {

    /**
     * 默认sheetName
     */
    private static final String DEFAULT_SHEET_NAME = "表格";

    /**
     * 读取数据条数(一批次)
     */
    private static final int BATCH_SIZE = 500;

    /**
     * 导出xlsx表格
     *
     * @param response  httpResponse
     * @param fileName  文件名
     * @param rowValues 导出excel表格
     * @param cls       类型, 强烈建议继承 ExcelStyle 接口 来保持风格统一
     * @param <T>       泛型
     */
    public static <T> void export(HttpServletResponse response, String fileName, List<T> rowValues, Class<T> cls) {
        export(response, fileName, rowValues, cls, DEFAULT_SHEET_NAME);
    }

    /**
     * 导出xlsx表格
     *
     * @param response  httpResponse
     * @param fileName  文件名
     * @param rowValues 导出excel表格
     * @param cls       类型
     * @param sheetName sheet名称
     * @param <T>       泛型
     */
    public static <T> void export(HttpServletResponse response, String fileName, List<T> rowValues, Class<T> cls, String sheetName) {
        if (!fileName.endsWith(ExcelTypeEnum.XLSX.getValue())) {
            fileName = fileName + ExcelTypeEnum.XLSX.getValue();
        }
        try {
            response.setHeader(Header.CONTENT_DISPOSITION.getValue(), "attachment;filename=" + URLUtil.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType(ExcelUtil.XLSX_CONTENT_TYPE);
            EasyExcel.write(response.getOutputStream(), cls).sheet(sheetName).doWrite(rowValues);
        } catch (Exception e) {
            log.error("导出Excel异常 [{}] [{}]", fileName, cls, e);
        }
    }

    /**
     * 读取excel
     *
     * @param stream excel文件流
     * @param consumer 数据处理回调
     * @param <T> 映射的对象
     */
    public static <T> void read(InputStream stream, Consumer<List<T>> consumer) {
        read(stream, consumer, BATCH_SIZE);
    }

    /**
     * 读取excel
     *
     * @param stream excel文件流
     * @param consumer 数据处理回调
     * @param batchSize 每批次读取条数
     * @param <T> 映射的对象
     */
    public static <T> void read(InputStream stream, Consumer<List<T>> consumer, int batchSize) {
        EasyExcel.read(stream, new ReadListener<T>() {

            private final List<T> batchList = ListUtils.newArrayListWithExpectedSize(batchSize);

            @Override
            public void invoke(T data, AnalysisContext context) {
                batchList.add(data);
                if (batchList.size() >= batchSize) {
                    consumer.accept(batchList);
                    batchList.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                consumer.accept(batchList);
            }
        }).sheet().doRead();
    }
}
