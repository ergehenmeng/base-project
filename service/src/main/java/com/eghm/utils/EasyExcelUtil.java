package com.eghm.utils;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.Header;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.eghm.annotation.ExcelSpinner;
import com.eghm.excel.ExcelSpinnerResolver;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * easy-excel封装工具类
 *
 * @author wyb
 * @since 2023/3/31
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EasyExcelUtil {

    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * 默认sheetName
     */
    private static final String DEFAULT_SHEET_NAME = "表格";

    /**
     * 读取数据条数(一批次)
     */
    private static final int BATCH_SIZE = 500;

    /**
     * 冻结首行
     */
    private static final SheetWriteHandler FREEZE_ROW_HANDLER = new FreezeRowHandler();

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
            response.setHeader(Header.CONTENT_DISPOSITION.getValue(), "attachment;filename=" + URLEncodeUtil.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType(XLSX_CONTENT_TYPE);
            EasyExcelFactory.write(response.getOutputStream(), cls).sheet(sheetName).registerWriteHandler(FREEZE_ROW_HANDLER).registerWriteHandler(new ExcelSpinnerHandler(cls)).doWrite(rowValues);
        } catch (Exception e) {
            log.error("导出Excel异常 [{}] [{}]", fileName, cls, e);
        }
    }

    /**
     * 读取excel
     *
     * @param stream   excel文件流
     * @param consumer 数据处理回调
     * @param <T>      映射的对象
     */
    public static <T> void read(InputStream stream, Consumer<List<T>> consumer) {
        read(stream, BATCH_SIZE, consumer);
    }

    /**
     * 读取excel
     *
     * @param stream    excel文件流
     * @param consumer  数据处理回调
     * @param batchSize 每批次读取条数
     * @param <T>       映射的对象
     */
    public static <T> void read(InputStream stream, int batchSize, Consumer<List<T>> consumer) {
        EasyExcelFactory.read(stream, new ReadExcelListener<>(consumer, batchSize)).sheet().doRead();
    }

    /**
     * 读取excel
     *
     * @param <T> T
     */
    private static class ReadExcelListener<T> implements ReadListener<T> {

        /**
         * 批量读取到的数据
         */
        private final List<T> batchList;

        /**
         * 单次最多读取多少条数据
         */
        private final int batchSize;

        /**
         * 读取到数据后处理
         */
        private final Consumer<List<T>> consumer;

        public ReadExcelListener(Consumer<List<T>> consumer, int batchSize) {
            this.consumer = consumer;
            this.batchSize = batchSize;
            this.batchList = Lists.newArrayListWithExpectedSize(batchSize);
        }

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
    }

    /**
     * 冻结首行
     */
    private static class FreezeRowHandler implements SheetWriteHandler {

        @Override
        public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
            writeSheetHolder.getSheet().createFreezePane(0, 1, 0, 1);
        }

    }

    private static class ExcelSpinnerHandler implements SheetWriteHandler {

        private final Map<Integer, ExcelSpinnerResolver> selectedMap = new HashMap<>();

        public ExcelSpinnerHandler(Class<?> head) {
            Field[] fields = head.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                ExcelSpinner spinner = field.getAnnotation(ExcelSpinner.class);
                ExcelProperty property = field.getAnnotation(ExcelProperty.class);
                if (spinner != null) {
                    ExcelSpinnerResolver resolver = new ExcelSpinnerResolver();
                    // 解析下拉列表数据源
                    String[] source = resolver.resolveSource(spinner);
                    if (source != null && source.length > 0) {
                        resolver.setSource(source);
                        resolver.setStart(spinner.start());
                        resolver.setEnd(spinner.end());
                        // 使用注解中的索引或字段顺序作为列索引
                        if (property != null && property.index() >= 0) {
                            selectedMap.put(property.index(), resolver);
                        } else {
                            selectedMap.put(i, resolver);
                        }
                    }
                }
            }
        }

        @Override
        public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
            Sheet sheet = writeSheetHolder.getSheet();
            Workbook workbook = sheet.getWorkbook();
            SXSSFWorkbook sw = (SXSSFWorkbook) workbook;
            String hiddenName = "hidden";
            XSSFSheet hiddenSheet = sw.getXSSFWorkbook().createSheet(hiddenName);
            // 将隐藏的sheet设置为不可见
            workbook.setSheetHidden(workbook.getSheetIndex(hiddenName), true);
            // 创建数据验证辅助器
            DataValidationHelper helper = sheet.getDataValidationHelper();
            // 为每个需要下拉列表的列创建数据验证
            selectedMap.forEach((index, selectedResolve) -> {
                // 设置下拉列表的范围：起始行，结束行，起始列，结束列
                CellRangeAddressList rangeList = new CellRangeAddressList(selectedResolve.getStart(), selectedResolve.getEnd(), index, index);
                // 在隐藏的sheet中生成下拉列表选项值
                String[] values = selectedResolve.getSource();
                generateSelectValue(hiddenSheet, index, values);
                // 获取Excel列标，例如A, B, AA
                String excelLine = getExcelLine(index);
                // 引用隐藏sheet中的单元格区域，例如hidden!$H$1:$H$50
                String refers = hiddenName + "!$" + excelLine + "$1:$" + excelLine + "$" + values.length;
                // 使用引用的内容作为下拉列表的值
                DataValidationConstraint constraint = helper.createFormulaListConstraint(refers);
                DataValidation validation = helper.createValidation(constraint, rangeList);
                // 设置验证属性，阻止输入非下拉选项的值
                validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                validation.setShowErrorBox(true);
                validation.setSuppressDropDownArrow(true);
                validation.createErrorBox("提示", "请输入下拉选项中的内容");
                // 将验证添加到当前的sheet中
                sheet.addValidationData(validation);
            });
        }

        /**
         * 在隐藏的sheet中生成下拉列表选项值。
         *
         * @param sheet  隐藏的sheet对象。
         * @param col    列索引。
         * @param values 下拉列表选项值数组。
         */
        private void generateSelectValue(Sheet sheet, int col, String[] values) {
            // 将下拉列表选项值写入隐藏的sheet中，每个选项值占用一行
            for (int i = 0, length = values.length; i < length; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }
                // 在指定列中创建单元格并设置下拉列表选项值
                row.createCell(col).setCellValue(values[i]);
            }
        }

        /**
         * 获取Excel列标（例如：A-Z, AA-ZZ）。
         *
         * @param num 列索引，从0开始。
         * @return Excel列标字符串。
         */
        public static String getExcelLine(int num) {
            StringBuilder line = new StringBuilder();
            // 计算列标，使用字母表示，例如 A, B, ..., Z, AA, AB, ...
            int first = num / 26;
            int second = num % 26;
            if (first > 0) {
                line.append((char) ('A' + first - 1));
            }
            line.append((char) ('A' + second));
            return line.toString();
        }
    }
}
