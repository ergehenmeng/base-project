package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 系统公告
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysBulletin extends BaseEntity {

    private static final long serialVersionUID = -3328700458453468310L;

    /**
     * 未发布
     */
    public static final byte STATE_0 = 0;

    /**
     * 已发布
     */
    public static final byte STATE_1 = 1;

    /**
     * 公告标题<br>
     * 表 : sys_bulletin<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 公告类型(数据字典表sys_bulletin_type)<br>
     * 表 : sys_bulletin<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 删除状态 0:正常 1:删除<br>
     * 表 : sys_bulletin<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 添加时间<br>
     * 表 : sys_bulletin<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 修改时间<br>
     * 表 : sys_bulletin<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 公告内容(富文本)<br>
     * 表 : sys_bulletin<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 是否发布 0:未发布 1:已发布<br>
     * 表 : sys_bulletin<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 数据字典解析字段
     */
    private String classifyName;
}