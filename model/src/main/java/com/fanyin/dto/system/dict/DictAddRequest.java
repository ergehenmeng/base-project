package com.fanyin.dto.system.dict;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:40
 */
@Data
public class DictAddRequest implements Serializable {

    private static final long serialVersionUID = -9001797427236045372L;

    /**
     * 字典名称
     */
    private String title;

    /**
     * 字典nid
     */
    private String nid;

    /**
     * 字典隐藏值
     */
    private Integer hiddenValue;

    /**
     * 显示值
     */
    private String  showValue;

    /**
     * 锁定状态(禁止编辑):0:未锁定 1:锁定
     */
    private Boolean locked;
}
