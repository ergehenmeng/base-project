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
public class BlackRoster extends BaseEntity {

    /**
     * 访问ip<br>
     * 表 : black_roster<br>
     * 对应字段 : ip<br>
     */
    private Long ip;

    /**
     * 是否删除 0:未删除 1:已删除<br>
     * 表 : black_roster<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 黑名单截止时间<br>
     * 表 : black_roster<br>
     * 对应字段 : end_time<br>
     */
    private Date endTime;

    /**
     * 添加时间<br>
     * 表 : black_roster<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : black_roster<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}