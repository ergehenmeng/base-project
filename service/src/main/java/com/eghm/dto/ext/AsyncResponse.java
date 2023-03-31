package com.eghm.dto.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * 队列异步响应信息 在缓存中拿到结果过
 * @author 二哥很猛
 * @date 2018/11/21 17:20
 */
@Data
public class AsyncResponse implements Serializable {

    private static final long serialVersionUID = -7850349675556972902L;

    /**
     * 异步结果唯一key 用于获取结果
     */
    private String key;

    /**
     * 成功或失败code
     */
    private Integer code;

    /**
     * 成功或失败的信息
     */
    private String msg;

}
