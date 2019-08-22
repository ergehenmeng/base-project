package com.fanyin.dao.model.business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class SmsTemplate implements Serializable {
    /**
     * 主键<br>
     * 表 : sms_template<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 短信模板nid即短信类型<br>
     * 表 : sms_template<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 短信内容<br>
     * 表 : sms_template<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 更新时间<br>
     * 表 : sms_template<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 备注信息<br>
     * 表 : sms_template<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    private static final long serialVersionUID = 1L;

}