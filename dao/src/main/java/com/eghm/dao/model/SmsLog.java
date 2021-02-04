package com.eghm.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信发送记录表
 * @author 二哥很猛
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsLog implements Serializable {
    /**
     * 主键<br>
     * 表 : sms_log<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 短信分类<br>
     * 表 : sms_log<br>
     * 对应字段 : sms_type<br>
     */
    private String smsType;

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