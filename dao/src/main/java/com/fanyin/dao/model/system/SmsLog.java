package com.fanyin.dao.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信发送记录表
 * @author 二哥很猛
 */
@Data
public class SmsLog implements Serializable {
    /**
     * 主键<br>
     * 表 : sms_log<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 短信分类<br>
     * 表 : sms_log<br>
     * 对应字段 : classify<br>
     */
    private String classify;

    /**
     * 手机号<br>
     * 表 : sms_log<br>
     * 对应字段 : mobile<br>
     */
    private String mobile;

    /**
     * 短信内容<br>
     * 表 : sms_log<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 发送时间<br>
     * 表 : sms_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 发送状态 0:失败 1:已发送<br>
     * 表 : sms_log<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    private static final long serialVersionUID = 1L;


}