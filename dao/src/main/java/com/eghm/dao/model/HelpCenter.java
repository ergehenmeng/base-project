package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class HelpCenter extends BaseEntity {

    /**
     * 帮助分类取sys_dict表中help_classify字段<br>
     * 表 : help_center<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 状态 0:不显示 1:显示<br>
     * 表 : help_center<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 问<br>
     * 表 : help_center<br>
     * 对应字段 : ask<br>
     */
    private String ask;

    /**
     * 答 支持<br>
     * 表 : help_center<br>
     * 对应字段 : answer<br>
     */
    private String answer;

    /**
     * 排序(小<->大)<br>
     * 表 : help_center<br>
     * 对应字段 : sort<br>
     */
    private Byte sort;

    /**
     * 删除状态 0:不删除(正常) 1:已删除<br>
     * 表 : help_center<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 更新时间<br>
     * 表 : help_center<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 添加时间<br>
     * 表 : help_center<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    private static final long serialVersionUID = 1L;

}