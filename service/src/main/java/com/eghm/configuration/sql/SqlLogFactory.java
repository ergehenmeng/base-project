package com.eghm.configuration.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 二哥很猛
 * @date 2019/10/16 17:59
 */
public class SqlLogFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger("mybaits-sql");

    public static Logger getLogger() {
        return LOGGER;
    }

}
