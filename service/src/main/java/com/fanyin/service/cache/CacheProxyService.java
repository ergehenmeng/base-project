package com.fanyin.service.cache;


/**
 * @author 二哥很猛
 * @date 2018/10/11 13:47
 */
public interface CacheProxyService {


    /**
     * 根据nid与隐藏值获取显示信息 数据字典格式化数据
     * @param nid nid
     * @param hiddenValue 隐藏值
     * @return 显示值
     */
    String getDictValue(String nid, Byte hiddenValue);

}

