package com.eghm.model.dto.sms;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/11/25 10:21
 */
@Data
public class SmsTemplateEditRequest implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 备注信息
     */
    private String remark;
}
