package com.eghm.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @date 2021/12/25 13:57
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {

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
