package com.eghm.model.dto.sms;

import com.eghm.model.dto.ext.PagingQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/21 16:21
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SmsLogQueryRequest extends PagingQuery {

    private static final long serialVersionUID = -3461922717720251514L;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 短信发送状态 0:发送中 1:发送成功 2:发送失败
     */
    private Byte state;

    /**
     * 短信类型
     */
    private String smsType;
}
