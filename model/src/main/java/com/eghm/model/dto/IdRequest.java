package com.eghm.model.dto;

import lombok.Data;

/**
 * 用于单一id对象映射
 * 禁止在该类中额外添加其他参数,除非是全局公用字段
 * @author 殿小二
 * @date 2020/8/29
 */
@Data
public class IdRequest {

    /**
     * id
     */
    private Integer id;
}
