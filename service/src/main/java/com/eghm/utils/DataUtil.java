package com.eghm.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.dto.ext.PageData;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * 主要针对移动端或前后端分离<br>
 * 数据格式化及数据转换<br/>
 * 例如:分页数据转换,集合转换
 *
 * @author 二哥很猛
 * @date 2018/11/21 10:10
 */
@Slf4j
public class DataUtil {

    private DataUtil() {
    }

    /**
     * 分页数据格式转换
     *
     * @param page page对象
     * @param transfer 转换对象
     * @return 结果
     */
    public static <S, T> PageData<T> convert(Page<S> page, Function<S, T> transfer) {

        PageData<T> paging = new PageData<>();

        List<S> list = page.getRecords();

        List<T> formatList = Lists.newArrayList();
        list.forEach(s -> {
            T format = transfer.apply(s);
            formatList.add(format);
        });

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
    public static <S, T> List<T> convert(Collection<S> sourceList, Function<S, T> transfer) {
        List<T> resultList = Lists.newArrayList();
        sourceList.forEach(s -> resultList.add(transfer.apply(s)));
        return resultList;
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
            log.error("bean复制异常", e);
            return null;
        }
    }
}
