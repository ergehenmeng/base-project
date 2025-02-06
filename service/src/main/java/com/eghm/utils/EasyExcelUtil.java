package com.eghm.utils;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.Header;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
     * 冻结首行配置
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
            EasyExcelFactory.write(response.getOutputStream(), cls).sheet(sheetName).registerWriteHandler(FREEZE_ROW_HANDLER).doWrite(rowValues);
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
        read(stream, BATCH_SIZE, consumer);
    }

    /**
     * 读取excel
     *
     * @param stream excel文件流
     * @param consumer 数据处理回调
     * @param batchSize 每批次读取条数
     * @param <T> 映射的对象
     */
    public static <T> void read(InputStream stream, int batchSize, Consumer<List<T>> consumer) {
        EasyExcelFactory.read(stream, new ReadExcelListener<>(consumer, batchSize)).sheet().doRead();
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
}
