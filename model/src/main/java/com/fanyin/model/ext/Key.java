package com.fanyin.model.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/21 17:34
 */
@Data
public class Key implements Serializable {

    private static final long serialVersionUID = -3523695195544460695L;

    /**
     * 异步结果唯一key
     */
    private String key;
}
