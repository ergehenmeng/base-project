package com.fanyin.dto.system.dict;

import com.fanyin.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = -5550485924850455994L;

    /**
     * 是否锁定
     */
    private Boolean locked;
}
