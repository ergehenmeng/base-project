package com.fanyin.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/21 17:20
 */
@Data
public class Async implements Serializable {

    private static final long serialVersionUID = -7850349675556972902L;

    /**
     * 异步结果唯一key
     */
    private String key;

    /**
     * 成功或失败code
     */
    private int code;

    /**
     * 成功或失败的信息
     */
    private String msg;

}
