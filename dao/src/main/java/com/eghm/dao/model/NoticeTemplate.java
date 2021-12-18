package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class NoticeTemplate extends BaseEntity {

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

}