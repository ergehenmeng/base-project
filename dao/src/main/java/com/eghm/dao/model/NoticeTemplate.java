package com.eghm.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class NoticeTemplate implements Serializable {
    /**
     * 主键<br>
     * 表 : notice_template<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 消息模板code<br>
     * 表 : notice_template<br>
     * 对应字段 : code<br>
     */
    private String code;

    /**
     * 标题<br>
     * 表 : notice_template<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 通知内容<br>
     * 表 : notice_template<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 更新时间<br>
     * 表 : notice_template<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}