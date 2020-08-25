package com.eghm.service.key;

import com.eghm.common.enums.Workspace;

/**
 * 项目中只能指定一种生成方式,否则可能会出现id重复
 * @author 二哥很猛
 * @date 2018/9/19 11:01
 */
public interface KeyGenerator {

    /**
     * 生成唯一id,默认IP方式生成
     * @return 63bit的id
     */
    Number generateKey();

    /**
     * 生成唯一id,后期扩展使用
     * @param workSpace 指定生成方式
     * @return 63bit的id
     */
    Number generateKey(Workspace workSpace);
}

