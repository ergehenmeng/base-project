package com.eghm.model.dto.config;


import com.eghm.model.ext.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/1/18 16:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigQueryRequest extends PagingQuery implements Serializable {

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
