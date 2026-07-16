package com.eghm.foundation.web.utility;

import cn.hutool.core.collection.CollUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树形结构通用工具类
 * 提供高效的树形数据构建方法，时间复杂度 O(n)
 *
 * @author 二哥很猛
 * @since 2026/1/28
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TreeUtil {

    /**
     * 将平铺列表转换为树形结构
     *
     * @param dataList         平铺列表
     * @param rootId       根节点ID
     * @param idGetter     获取节点ID的函数
     * @param pidGetter    获取父节点ID的函数
     * @param childrenSetter 设置子节点的函数
     * @param <T>          节点类型
     * @param <ID>         ID类型
     * @return 树形结构列表
     */
    public static <T, ID> List<T> tree(List<T> dataList, ID rootId, Function<T, ID> idGetter, Function<T, ID> pidGetter, BiConsumer<T, List<T>> childrenSetter) {
        return tree(dataList, rootId, idGetter, pidGetter, childrenSetter, null);
    }

    /**
     * 将平铺列表转换为树形结构（支持排序）
     *
     * @param dataList         平铺列表
     * @param rootId       根节点ID
     * @param idGetter     获取节点ID的函数
     * @param pidGetter    获取父节点ID的函数
     * @param childrenSetter 设置子节点的函数
     * @param comparator   排序比较器（可为null）
     * @param <T>          节点类型
     * @param <ID>         ID类型
     * @return 树形结构列表
     */
    public static <T, ID> List<T> tree(List<T> dataList, ID rootId, Function<T, ID> idGetter, Function<T, ID> pidGetter, BiConsumer<T, List<T>> childrenSetter, Comparator<T> comparator) {
        if (CollUtil.isEmpty(dataList)) {
            return Collections.emptyList();
        }
        // 按父节点ID分组，时间复杂度 O(n)
        Map<ID, List<T>> groupMap = dataList.stream().collect(Collectors.groupingBy(pidGetter));
        return treeBin(rootId, groupMap, idGetter, childrenSetter, comparator);
    }

    /**
     * 递归构建树形结构
     * @param parentId       父节点ID
     * @param groupMap       父节点ID分组的Map
     * @param idGetter       获取节点ID的函数
     * @param childrenSetter 设置子节点的函数
     * @param comparator     排序比较器（可为null）
     * @return              子节点列表
     * @param <T>             节点类型
     * @param <ID>           ID类型
     */
    private static <T, ID> List<T> treeBin(ID parentId, Map<ID, List<T>> groupMap, Function<T, ID> idGetter, BiConsumer<T, List<T>> childrenSetter, Comparator<T> comparator) {
        List<T> children = groupMap.getOrDefault(parentId, Collections.emptyList());
        // 如果提供了排序器，则对子节点排序
        if (comparator != null && !children.isEmpty()) {
            children = new ArrayList<>(children);
            children.sort(comparator);
        }
        children.forEach(child -> {
            List<T> subChildren = treeBin(idGetter.apply(child), groupMap, idGetter, childrenSetter, comparator);
            childrenSetter.accept(child, subChildren);
        });
        return children;
    }

}
