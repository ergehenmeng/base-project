package com.eghm.dto.ext;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2018/11/21 17:34
 */
@Getter
@Setter
public abstract class AsyncKey {

    /**
     * 异步结果唯一key
     */
    private String key;
}
