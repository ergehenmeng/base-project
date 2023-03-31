package com.eghm.model.dto.sms;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ApiModelProperty("开始时间 yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty("结束时间 yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty("短信发送状态 0:发送中 1:发送成功 2:发送失败")
    private Integer state;

    @ApiModelProperty("短信类型")
    private String smsType;
}
