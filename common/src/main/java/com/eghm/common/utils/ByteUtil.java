package com.eghm.common.utils;

/**
 * 字节工具类
 *
 * @author 二哥很猛
 * @date 2018/8/6 15:19
 */
public class ByteUtil {

    /**
     * 16进制数组
     */
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * byte转16进制字符串
     *
     * @param bt byte
     * @return 字符串
     */
    private static String byteToHexString(byte bt) {
        //byte转int
        int n = bt;
        if (n < 0) {
            n += 256;
        }
        int tens = n / 16;
        int unit = n % 16;
        return HEX_DIGITS[tens] + HEX_DIGITS[unit];
    }

    /**
     * 对bytes进行编码 16进制模式
     *
     * @param bytes byte数组
     * @return 字符串
     */
    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(byteToHexString(b));
        }
        return builder.toString();
    }
}
