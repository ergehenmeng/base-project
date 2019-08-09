package com.fanyin.dto.system.dict;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:50
 */
@Data
public class DictEditRequest implements Serializable {

    private static final long serialVersionUID = 2506674696822623145L;

    /**
     * 字典名称
     */
    private String title;

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

    /**
     * 主键
     */
    private Integer id;

    /**
     * nid
     */
    private String nid;

    /**
     * 备注信息
     */
    private String remark;
}
