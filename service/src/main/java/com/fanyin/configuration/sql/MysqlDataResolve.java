package com.fanyin.configuration.sql;

import com.fanyin.common.utils.DateUtil;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/10/16 19:48
 */
public class MysqlDataResolve implements DataResolve {

    @Override
    public String resolve(Object param) {
        if (param == null) {
            return "Null";
        }
        if (param instanceof String) {
            return "'" + param.toString() + "'";
        }
        if (param instanceof Date) {
            return "'" + DateUtil.format(param) + "'";
        }
        return "'" + param.toString() + "'";
    }
}
