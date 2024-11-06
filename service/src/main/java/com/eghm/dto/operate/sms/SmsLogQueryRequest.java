package com.eghm.dto.operate.sms;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.SmsType;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2019/8/21 16:21
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SmsLogQueryRequest extends PagingQuery {

    @ApiModelProperty("开始日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("开始日期 yyyy-MM-dd")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @ApiModelProperty("短信发送状态 0:发送中 1:发送成功 2:发送失败")
    private Integer state;

    @ApiModelProperty("短信类型")
    private SmsType smsType;
}
