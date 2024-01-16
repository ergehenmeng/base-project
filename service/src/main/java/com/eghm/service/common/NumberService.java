package com.eghm.service.common;

/**
 * @author 二哥很猛
 * @date 2018/1/18 14:16
 */
public interface NumberService {

    /**
     * 生成唯一编号
     *
     * @param prefix 前缀
     * @return NO.2021021213123123123
     */
    String getNumber(String prefix);

    /**
     * 生成唯一编号
     *
     * @return 2021021213123123123
     */
    String getNumber();
}
