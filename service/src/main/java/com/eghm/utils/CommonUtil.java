package com.eghm.utils;

import com.eghm.enums.RepastType;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/5/7
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

    /**
     * 包含就餐 1:早餐 2:午餐 4:晚餐
     *
     * @param repast 最大7
     * @return list
     */
    public static List<Integer> parseRepast(Integer repast) {
        if (repast == null) {
            return Lists.newArrayList();
        }
        List<Integer> list = Lists.newArrayList();
        for (RepastType value : RepastType.values()) {
            if ((repast & value.getValue()) == value.getValue()) {
                list.add(value.getValue());
                return list;
            }
        }
        return list;
    }
}
