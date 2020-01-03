package com.eghm.configuration.sql;

/**
 * @author 二哥很猛
 * @date 2019/10/16 19:45
 */
public interface DataResolve {

    /**
     * 解析入参
     *
     * @param param sql参数
     * @return 格式化后的参数
     */
    String resolve(Object param);

}
