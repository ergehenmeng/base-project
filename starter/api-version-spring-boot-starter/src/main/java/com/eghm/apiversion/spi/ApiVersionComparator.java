package com.eghm.apiversion.spi;

/**
 * Api 版本号比较器接口
 */
@FunctionalInterface
public interface ApiVersionComparator {

    /**
     * 比较两个 API 版本。
     *
     * @param left  左侧版本
     * @param right 右侧版本
     * @return 左侧版本小于、等于或大于右侧版本时，分别返回负数、零或正数
     * @throws NumberFormatException 版本格式无法解析时抛出
     */
    int compare(String left, String right);

    /**
     * 判断来源版本是否大于或等于目标版本。
     *
     * @param source 来源版本，通常为客户端请求版本
     * @param target 目标版本，通常为接口声明版本
     * @return {@code source >= target} 时返回 {@code true}
     * @throws NumberFormatException 版本格式无法解析时抛出
     */
    default boolean greaterThanOrEqual(String source, String target) {
        return compare(source, target) >= 0;
    }
}
