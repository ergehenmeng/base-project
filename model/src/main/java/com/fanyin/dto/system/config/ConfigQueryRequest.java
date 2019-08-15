package com.fanyin.dto.system.config;


import com.fanyin.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/1/18 16:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = -2384592001035426711L;

    /**
     * 参数配置类型
     */
    private Integer classify;

    /**
     * 是否锁定(禁止编辑)
     */
    private Boolean locked;

    /**
     * 备注信息
     */
    private String remark;


}
