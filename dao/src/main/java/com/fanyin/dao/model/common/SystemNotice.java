package com.fanyin.dao.model.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统公告
 * @author 二哥很猛
 */
@Data
public class SystemNotice implements Serializable {
    private static final long serialVersionUID = -3328700458453468310L;
    /**
     * 主键<br>
     * 表 : system_notice<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 公告标题<br>
     * 表 : system_notice<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 公告类型(数据字典表system_notice_type)<br>
     * 表 : system_notice<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 删除状态 0:正常 1:删除<br>
     * 表 : system_notice<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 添加时间<br>
     * 表 : system_notice<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 修改时间<br>
     * 表 : system_notice<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 公告内容(富文本)<br>
     * 表 : system_notice<br>
     * 对应字段 : content<br>
     */
    private String content;


}