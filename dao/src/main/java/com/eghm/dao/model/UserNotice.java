package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户站内信
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class UserNotice implements Serializable {

    /**
     * 用户id<br>
     * 表 : user_notice<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

    /**
     * 消息标题<br>
     * 表 : user_notice<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 站内信内容<br>
     * 表 : user_notice<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 站内信分类<br>
     * 表 : user_notice<br>
     * 对应字段 : classify<br>
     */
    private String classify;

    /**
     * 状态 0:未读 1:已读<br>
     * 表 : user_notice<br>
     * 对应字段 : read<br>
     */
    private Boolean read;

    /**
     * 删除状态 0:未删除 1:已删除<br>
     * 表 : user_notice<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 添加时间<br>
     * 表 : user_notice<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : user_notice<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}