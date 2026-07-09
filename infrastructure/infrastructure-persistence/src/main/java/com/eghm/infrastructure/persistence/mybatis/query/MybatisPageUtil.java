package com.eghm.infrastructure.persistence.mybatis.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * MyBatis分页适配.
 *
 * @author 二哥很猛
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MybatisPageUtil {

    public static <T> com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> toMybatis(Page<T> page) {
        return new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.isSearchCount());
    }

    public static <T> Page<T> fromMybatis(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        Page<T> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
        result.setRecords(page.getRecords());
        return result;
    }

    public static <S, T> Page<T> copy(com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> page, Class<T> cls) {
        Page<T> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());
        result.setRecords(DataUtil.copy(page.getRecords(), cls));
        return result;
    }
}

