package com.fanyin.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author 二哥很猛
 * @date 上午11:11:38
 */
public class BigDecimalUtil {

    /**
     * 除法精度
     */
    private static final int DIV_SCALE = 10;
    /**
     * 乘法精度
     */
    private static final int MUL_SCALE = 4;
    /**
     * 加法精度
     */
    private static final int ADD_SCALE = 4;
    /**
     * 减法精度
     */
    private static final int SUB_SCALE = 4;

    /**
     * 四舍五入由于程序中计算均为元单位 因此默认为2
     */
    private static final int ROUND_SCALE = 2;

    /**
     * 除法 默认精确10位
     *
     * @param s1 除数
     * @param s2 被除数
     * @return double
     */
    public static double div(double s1, double s2) {
        return div(s1, s2, DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param s1    除数
     * @param s2    被除数
     * @param scale 精确位数
     * @return double
     */
    public static double div(double s1, double s2, int scale) {
        BigDecimal ds1 = BigDecimal.valueOf(s1);
        BigDecimal ds2 = BigDecimal.valueOf(s2);
        return ds1.divide(ds2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将元转为分
     *
     * @param money 金额 元
     * @return double
     */
    public static double yuanToCent(double money) {
        return mul(money, 100);
    }

    /**
     * 将元转为分
     * @param money 金额 元
     * @return double
     */
    public static BigDecimal yuanToCent(BigDecimal money){

        return money.multiply(BigDecimal.valueOf(100)).setScale(ROUND_SCALE, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 将分转为元
     *
     * @param money 金额 分
     * @return 元
     */
    public static double centToYuan(double money) {
        return div(money, 100);
    }

    /**
     * 分转元
     * @param money 金额 分
     * @return double
     */
    public static BigDecimal centToYuan(BigDecimal money){
        return BigDecimal.valueOf(centToYuan(money.doubleValue()));
    }


    /**
     * 元转分
     *
     * @param money 金额
     * @return double
     */
    public static String centToYuanFormat(BigDecimal money) {
        if (money == null) {
            return "0.00";
        }
        return centToYuanFormat(money.doubleValue());
    }

    /**
     * 分转元
     *
     * @param money 金额
     * @return double
     */
    public static String centToYuanFormat(double money) {
        double result = centToYuan(money);
        return formatMoney(result);
    }


    /**
     * 取反操作 ,正数变成负数,负数变成正数
     *
     * @param decimal 数字
     * @return BigDecimal
     */
    public static BigDecimal negation(BigDecimal decimal) {
        return BigDecimal.valueOf(-decimal.doubleValue());
    }


    /**
     * 乘法 默认精确2位
     *
     * @param s1 乘数
     * @param sn 第2~N个乘数
     * @return double
     */
    public static double mul(double s1, double... sn) {
        BigDecimal ms1 = BigDecimal.valueOf(s1);
        for (double s : sn) {
            ms1 = ms1.multiply(BigDecimal.valueOf(s)).setScale(MUL_SCALE, BigDecimal.ROUND_HALF_UP);
        }

        return ms1.doubleValue();
    }

    /**
     * 乘法 默认位数0 四舍五入
     *
     * @param s1 第一个乘数
     * @param s2 第二个乘数
     * @return double
     */
    public static double mul(double s1, double s2) {
        if (s1 == 0 || s2 == 0) {
            return 0;
        }
        return BigDecimal.valueOf(s1).multiply(BigDecimal.valueOf(s2)).setScale(ROUND_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 加法
     *
     * @param s1 第一个加数
     * @param s2 第二个加数
     * @return double
     */
    public static double add(double s1, double s2) {
        return add(s1, s2, ADD_SCALE);
    }

    /**
     * 加法
     *
     * @param s1    第一个加数
     * @param s2    第二个加数
     * @param scale 精确位数
     * @return double
     */
    public static double add(double s1, double s2, int scale) {
        BigDecimal as1 = BigDecimal.valueOf(s1);
        as1 = as1.add(BigDecimal.valueOf(s2));
        return as1.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 减法 默认精确2位
     *
     * @param s1 减数
     * @param s2 被减数
     * @return double
     */
    public static double sub(double s1, double s2) {
        return sub(s1, s2, SUB_SCALE);
    }

    /**
     * 减法 默认精确2位
     *
     * @param s1    减数
     * @param s2    被减数
     * @param scale 精度位数
     * @return double
     */
    public static double sub(double s1, double s2, int scale) {
        BigDecimal ms1 = BigDecimal.valueOf(s1);
        return ms1.subtract(BigDecimal.valueOf(s2)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 格式化金额显示 例如721,121.00
     *
     * @param money 入参
     * @return double
     */
    public static String formatMoney(double money) {
        if (money == 0) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("#,##0.00");
        return format.format(money);
    }

    /**
     * 格式化金额显示 例如721,121.00
     *
     * @param decimal 入参
     * @return double
     */
    public static String formatMoney(BigDecimal decimal){
        if(decimal == null){
            return "0.00";
        }
        return formatMoney(decimal.doubleValue());
    }


    /**
     * 将double转为string
     *
     * @param money 金额
     * @return 格式话之后的金额
     */
    public static String getMoneyStr(double money) {
        return Double.toString(money);
    }

    /**
     * 四舍五入
     *
     * @param money 金额
     * @param scale 保留小数位数
     * @return double
     */
    public static double round(double money, int scale) {
        return BigDecimal.valueOf(money).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入 默认不保留小数
     *
     * @param money 金额
     * @return double
     */
    public static double round(double money) {
        return BigDecimal.valueOf(money).setScale(ROUND_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * BigDecimal 乘以 double  结果去两位小数
     *
     * @param v1 第一个
     * @param v2 第二个
     * @return BigDecimal
     */
    public static BigDecimal multiply(BigDecimal v1, Double v2) {
        if (null == v1 || null == v2) {
            return null;
        }
        return v1.multiply(BigDecimal.valueOf(v2)).setScale(MUL_SCALE, BigDecimal.ROUND_HALF_EVEN);

    }


}
