package com.eghm.apiversion.support;

import com.eghm.apiversion.spi.ApiVersionComparator;

/**
 * 版本号对比默认实现
 */
public class NumericApiVersionComparator implements ApiVersionComparator {

    private static final int MAX_SEGMENT_LENGTH = 2;
    
    @Override
    public int compare(String left, String right) {
        return Integer.compare(parse(left), parse(right));
    }

    /**
     * 将分段版本号转换为可比较的整数。
     *
     * @param version API 版本号
     * @return 数字化后的版本号
     * @throws NumberFormatException 版本格式不受支持时抛出
     */
    int parse(String version) {
        String normalizedVersion = version.replace("v", "").replace("V", "");
        StringBuilder numericVersion = new StringBuilder();
        for (String segment : normalizedVersion.split("\\.")) {
            if (segment.length() > MAX_SEGMENT_LENGTH) {
                throw new NumberFormatException("Version segment must contain at most two digits: " + version);
            }
            numericVersion.append(String.format("%02d", Integer.parseInt(segment)));
        }
        return Integer.parseInt(numericVersion.toString());
    }
}
