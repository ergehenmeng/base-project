package com.eghm.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.dto.ext.PagingQuery;

/**
 * @author wyb
 * @date 2021/12/25 13:57
 */
public class PageUtil {

    private PageUtil() {}

    /**
     * 创建分页对象
     * @param query 分页参数
     * @param <T> 分页泛型对象
     * @return 分页对象
     */
    public static <T> Page<T> createPage(PagingQuery query) {
        return new Page<>(query.getPage(), query.getPageSize());
    }
}
