package com.eghm.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PageData;
import com.eghm.vo.business.BaseConfigResponse;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 主要针对移动端或前后端分离<br>
 * 数据格式化及数据转换<br/>
 * 例如:分页数据转换,集合转换
 *
 * @author 二哥很猛
 * @since 2018/11/21 10:10
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataUtil {

    /**
     * 分页数据格式转换
     *
     * @param page     page对象
     * @param transfer 转换对象
     * @return 结果
     */
    public static <S, T> PageData<T> copy(Page<S> page, Function<S, T> transfer) {

        PageData<T> paging = new PageData<>();
        List<T> formatList = Lists.newArrayList();

        page.getRecords().forEach(s -> formatList.add(transfer.apply(s)));

        paging.setRows(formatList);
        paging.setTotal(page.getTotal());
        paging.setPage(page.getCurrent());
        paging.setPageSize(page.getSize());

        return paging;
    }

    /**
     * 分页数据格式转换
     *
     * @param page page对象
     * @param cls  要转换的数据类型
     * @param <S>  源类型
     * @param <T>  目标类型
     * @return 结果
     */
    public static <S, T> PageData<T> copy(Page<S> page, Class<T> cls) {

        PageData<T> paging = new PageData<>();
        List<T> formatList = Lists.newArrayList();

        page.getRecords().forEach(s -> formatList.add(copy(s, cls)));

        paging.setRows(formatList);
        paging.setTotal(page.getTotal());
        paging.setPage(page.getCurrent());
        paging.setPageSize(page.getSize());

        return paging;
    }

    /**
     * 列表数据转换
     *
     * @param sourceList 原列表数据 集合
     * @param transfer   转换方式
     * @param <S>        原数据类型
     * @param <T>        目标数据类型
     * @return 结果数据列表
     */
    public static <S, T> List<T> copy(Collection<S> sourceList, Function<S, T> transfer) {
        List<T> resultList = Lists.newArrayList();
        sourceList.forEach(s -> resultList.add(transfer.apply(s)));
        return resultList;
    }

    /**
     * 列表数据转换
     *
     * @param sourceList 原列表数据 集合
     * @param cls        新数据
     * @param <S>        原数据类型
     * @param <T>        目标数据类型
     * @return 结果数据列表
     */
    public static <S, T> List<T> copy(Collection<S> sourceList, Class<T> cls, String... ignoreProperties) {
        return copy(sourceList, s -> DataUtil.copy(s, cls, ignoreProperties));
    }


    public static <T> T copy(Object source, Class<T> cls, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T t = cls.getDeclaredConstructor().newInstance();
            if (ignoreProperties != null && ignoreProperties.length > 0) {
                BeanUtils.copyProperties(source, t, ignoreProperties);
            } else {
                BeanUtils.copyProperties(source, t);
            }
            return t;
        } catch (Exception e) {
            log.error("bean复制异常 [{}] [{}]", source, cls, e);
            return null;
        }
    }

    public static <T> T copy(Object source, Class<T> cls, Consumer<T> consumer, String... ignoreProperties) {
        T copy = copy(source, cls, ignoreProperties);
        if (copy != null) {
            consumer.accept(copy);
        } else {
            log.error("bean复制异常,不执行consumer [{}] [{}]", source, cls);
        }
        return copy;
    }

    /**
     * 按月填充日态
     *
     * @param configList 原配置信息
     * @param filter     过滤条件
     * @param supplier   结果对象(空对象)
     * @param monthDay   月份
     * @param <S>        原对象
     * @param <T>        结果对象
     * @return list
     */
    public static <S, T extends BaseConfigResponse> List<T> paddingMonth(List<S> configList, BiPredicate<S, LocalDate> filter, Supplier<T> supplier, LocalDate monthDay) {
        int ofMonth = monthDay.lengthOfMonth();
        List<T> responseList = new ArrayList<>(45);
        // 月初到月末进行拼装
        for (int i = 0; i < ofMonth; i++) {
            LocalDate localDate = monthDay.plusDays(i);
            Optional<S> optional = configList.stream().filter(config -> filter.test(config, localDate)).findFirst();
            T response = supplier.get();
            if (optional.isPresent()) {
                BeanUtil.copyProperties(optional.get(), response);
                response.setHasSet(true);
            } else {
                response.setHasSet(false);
                response.setConfigDate(localDate);
            }
            responseList.add(response);
        }
        return responseList;
    }

    /**
     * 填充日数据
     *
     * @param targetMap 原数据
     * @param startDate 需要填充的开始日期
     * @param endDate   需要填充的结束日期
     * @param function 没查找到时执行的数据
     * @param <T> <T>
     * @return 最终数据
     */
    public static <T> List<T> paddingDay(Map<LocalDate, T> targetMap, LocalDate startDate, LocalDate endDate, Function<LocalDate, T> function) {
        long between = ChronoUnit.DAYS.between(startDate, endDate);
        List<T> resultList = new ArrayList<>();
        for (int i = 0; i < between; i++) {
            LocalDate date = startDate.plusDays(i);
            resultList.add(targetMap.getOrDefault(date, function.apply(date)));
        }
        return resultList;
    }
}
